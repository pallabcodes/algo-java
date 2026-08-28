# Design Doc: Multi-Layer Cache

| **Design Doc** | **Multi-Layer Cache** |
|---|---|
| **Owner** | [You] |
| **Date** | 2026-05 |
| **Status** | Draft |
| **Teams** | Platform, Performance, SRE |

## Context

App uses single in-memory cache. Cache miss hits DB directly. No load-through, no eviction policy control, no layering (L1 local, L2 Redis, L3 CDN). Adding new cache layer requires changing every caller.

## Problem

- **Cache miss penalty**: DB hit on every miss — no load-through (target: Proxy fetches on miss transparently)
- **Layer coupling**: All cache logic in one class — can't add L2/L3 without rewriting caller (target: compose layers via Decorator)
- **Eviction rigidity**: Single FIFO eviction — can't swap LRU/LFU/TTL per layer (target: pluggable Strategy)
- **Topology wiring**: Callers construct multi-layer chain manually (target: Factory builds topology)

## Alternatives Considered

| Alternative | Why Rejected |
|---|---|
| **1. Keep single cache, add more logic** | Caller still knows about layers |
| **2. Just use Redis, skip local cache** | L1 latency < 1μs vs L2 at 1ms — L1 essential for hot keys |
| **3. Proxy + Decorator + Strategy + Factory** | ✅ Chosen |

## Design

4 patterns compose:

```
  Caller ──▶ CacheProxy ──▶ LayeredCacheDecorator ──▶ L1 (LocalCache)
                                │                        │
                                │                   EvictionStrategy (LRU/LFU/TTL)
                                ▼
                             L2 (RemoteCache) ──▶ L3 (CDN)
                                │
                           EvictionStrategy (LRU/LFU/TTL)
```

### Pattern composition

| Pattern | File(s) | What it does |
|---------|---------|--------------|
| **Proxy** | `CacheProxy` | Load-through — fetches from source DB on complete miss. Caller unaware. |
| **Decorator** | `LayeredCacheDecorator` | Composes L1/L2/L3 into single `Cacheable`. Hit promotes to upper layers. |
| **Strategy** | `EvictionStrategy` + `LRU`/`LFU`/`TTL` | Pluggable eviction per layer. Adding new policy = new record. |
| **Factory** | `CacheFactory` | Creates 1/2/3-layer topologies. `singleLayer()`, `twoLayer()`, `threeLayer()`. |

### Key interfaces

- `Cacheable<K, V>` — uniform cache contract (get/put/evict/clear)
- `EvictionStrategy` — `onAccess`/`onPut` hooks per policy

## Reading Order

| Step | File | Why read this next |
|------|------|--------------------|
| 1 | `DESIGN_DOC.md` (this file) | Understand problem, design, trade-offs |
| 2 | `Cacheable.java` | Core interface — contract all layers and proxy implement |
| 3 | `LocalCache.java` | Simplest leaf — L1 in-memory, understand base behavior |
| 4 | `RemoteCache.java` | L2/L3 node — adds Strategy composability |
| 5 | `EvictionStrategy.java` | Strategy — pluggable eviction (LRU/LFU/TTL) |
| 6 | `LayeredCacheDecorator.java` | Decorator — wraps multiple layers with promotion |
| 7 | `CacheProxy.java` | Proxy — load-through from source on complete miss |
| 8 | `CacheFactory.java` | Factory — wires topology without caller knowing |
| 9 | `CacheDemo.java` | See all 4 patterns compose in 4 scenarios |

## Migration Plan

| Phase | Time | What | Risk |
|-------|------|------|------|
| 1 | Week 1 | Extract `Cacheable` interface. Move existing cache behind it. | Low |
| 2 | Week 2 | Add `CacheProxy` — load-through from DB. | Low |
| 3 | Week 3 | Add `LayeredCacheDecorator`. Add L2 (Redis). | Medium — L2 latency may slow hot path |
| 4 | Week 4 | Add `EvictionStrategy`. Migrate L1 to LRU. | Low |
| 5 | Week 5 | Add `CacheFactory`. Remove manual topology code. | Low |

## Rollback / Safety

- Feature flag per cache layer
- CacheProxy has circuit breaker: if source DB slow, degrade to stale cache
- Promotion from L2→L1 is async to avoid blocking gets

## Metrics

| Metric | Current | Target | How |
|--------|---------|--------|-----|
| P50 get latency | 5ms (DB hit) | < 1ms (L1 hit) | Micrometer |
| Cache hit ratio | 60% | > 90% | Hit/miss counters |
| Time to add layer | 2 weeks | < 1 day | Dev hours |

## Risks

- **Stale data on promotion**: L1 evicts stale keys; L2 may serve stale. **Mitigation**: TTL-based invalidation per layer.
- **Eviction thrashing**: If working set > L1 capacity, frequent evictions. **Mitigation**: monitor eviction rate; alert at 80% capacity.
