# Design Doc: Event Processing Pipeline

| **Design Doc** | **Event Processing Pipeline** |
|---|---|
| **Owner** | [You] |
| **Date** | 2026-05 |
| **Status** | Draft |
| **Teams** | Platform, Data, Infrastructure |

## Context

Events arrive from 3 sources (PubSub, Kafka, CloudTasks). Each source has different API, deserialization, routing. Currently each source has its own processing code — duplicate validation, transform, routing logic. Adding a new source copies N stages.

## Problem

- **Source coupling**: Pipeline hardcoded per source — 3 sources = 3 pipelines (target: 1 pipeline, source-agnostic)
- **Stage duplication**: Validate/transform/route logic repeated per source (target: shared stages via Chain)
- **Routing rigidity**: Event routing (fan-out, sharded, priority) cannot change without code change (target: pluggable Strategy)
- **No monitoring**: No cross-cutting observability per stage (target: Observer hooks)

## Alternatives Considered

| Alternative | Why Rejected |
|---|---|
| **1. Keep per-source pipelines** | 3× code, N× for each new source |
| **2. Common pipeline, hardcoded stages** | Can't reorder or skip per event type |
| **3. Adapter + Chain + Factory + Strategy + Observer** | ✅ Chosen |

## Design

5 patterns compose:

```
  PubSub ──▶ EventSource (Adapter) ──▶ EventPipeline (Chain)
  Kafka  ──▶ EventSource (Adapter) ──▶   ├── deserialize
  Tasks  ──▶ EventSource (Adapter) ──▶   ├── validate
                                          ├── transform
                                          └── route (Strategy)
                                               │
                                               ▼
                                          HandlerFactory ──▶ type-specific pipeline
                                               │
                                               ▼
                                          EventMonitor (Observer)
```

### Pattern composition

| Pattern | File(s) | What it does |
|---------|---------|--------------|
| **Adapter** | `EventSource` + `PubSubSource`/`KafkaSource`/`CloudTasksSource` | Unifies 3 source APIs behind single interface |
| **Chain** | `EventHandler` + `EventPipeline` | Stages: deserialize → validate → transform → route. Each can short-circuit. |
| **Factory** | `HandlerFactory` | Creates different pipelines per event type (critical vs lightweight) |
| **Strategy** | `EventRouterStrategy` + `FanOut`/`Sharded`/`PriorityFirst` | Routing topology swappable at runtime |
| **Observer** | `EventMonitor` + logAll/sliTracker/counter | Cross-cutting monitoring; decoupled from stages |

### Key interfaces

- `EventSource` — `poll()` unifying PubSub/Kafka/CloudTasks
- `EventHandler` — `handle()` functional interface; each stage = one handler
- `EventRouterStrategy` — routes event to downstream handler(s)

## Reading Order

| Step | File | Why read this next |
|------|------|--------------------|
| 1 | `DESIGN_DOC.md` (this file) | Understand problem, design, trade-offs |
| 2 | `Event.java` | Canonical event record — data flowing through pipeline |
| 3 | `EventSource.java` | Adapter — unifies PubSub/Kafka/CloudTasks behind one interface |
| 4 | `EventHandler.java` | Chain stage — functional interface, each stage is one handler |
| 5 | `EventPipeline.java` | Chain — how stages compose and short-circuit |
| 6 | `EventRouterStrategy.java` | Strategy — pluggable routing (FanOut/Sharded/Priority) |
| 7 | `HandlerFactory.java` | Factory — per-type pipeline creation |
| 8 | `EventMonitor.java` | Observer — cross-cutting monitoring hooks |
| 9 | `EventPipelineDemo.java` | See all 5 patterns compose |

## Migration Plan

| Phase | Time | What | Risk |
|-------|------|------|------|
| 1 | Week 1 | Extract `EventSource` adapter for 1 source (PubSub) | Low |
| 2 | Week 2 | Add `EventPipeline` Chain — deserialize + validate | Low |
| 3 | Week 3 | Add remaining EventSources (Kafka, CloudTasks) | Medium — behavioral diff between source SDKs |
| 4 | Week 4 | Add `HandlerFactory` — different pipelines per type | Medium — type classification may misroute |
| 5 | Week 5 | Add `EventRouterStrategy` — FanOut, Sharded, Priority | Low |
| 6 | Week 6 | Add `EventMonitor` observers | Low |

## Rollback / Safety

- Each Chain stage logs + skips on error (malformed events dropped at validate)
- HandlerFactory returns fallback (basic pipeline) for unknown event types
- EventRouterStrategy defaults to FanOut if routing key missing

## Metrics

| Metric | Current | Target | How |
|--------|---------|--------|-----|
| Time to add source | 2 weeks | < 2 days | Dev hours |
| Stage reuse across sources | 0% | 100% | Shared handler count |
| Undropped events | — | > 99.9% | Dead-letter queue |

## Risks

- **Short-circuit hides errors**: Validate drops malformed events silently. **Mitigation**: dead-letter topic + alert.
- **Strategy misrouting**: PriorityFirst starves low-priority events permanently. **Mitigation**: starvation counter + fairness window.
- **Adapter leak**: Source-specific behavior (Kafka exactly-once vs PubSub at-least-once) leaks through. **Mitigation**: canonical event envelope + source metadata field.
