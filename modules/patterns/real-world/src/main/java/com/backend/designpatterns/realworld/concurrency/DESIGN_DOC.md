# Design Doc: Concurrency & Resilience

| **Design Doc** | **Concurrency & Resilience** |
|---|---|
| **Owner** | [You] |
| **Date** | 2026-05 |
| **Status** | Draft |
| **Teams** | Platform, SRE, Infrastructure |

## Context

Order fulfillment calls 4 downstream services (inventory, fraud, payment, shipping). Sequential calls = high latency. No rate limiting, no circuit breakers, no retry budget. A single slow downstream blocks everything. No actor isolation for stateful workers.

## Problem

- **Sequential orchestration**: 4 downstream calls = 400ms+ P50 (target: parallel fan-out via STS)
- **No backpressure**: Traffic spike → downstream collapse (target: rate limit + bulkhead + circuit breaker)
- **Retry storm**: 100K QPS × 3 retries = 300K extra load on blip (target: retry budget)
- **No load shedding**: Batch jobs crowd out user traffic under overload (target: priority-based admission)
- **Shared mutable state**: Worker tasks compete for locks (target: actor model + mailbox)

## Alternatives Considered

| Alternative | Why Rejected |
|---|---|
| **1. Just add threads** | Unbounded threads overwhelm downstream; no isolation |
| **2. Rate limit only** | Doesn't prevent cascading failure or retry storms |
| **3. Loom VTs only** | VTs don't solve sharing or isolation — actors needed for state |
| **4. STS + ResiliencePipeline + RetryBudget + ActorModel** | ✅ Chosen |

## Design

4 orthogonal concerns, each a pattern composition:

### 1. Fulfillment Orchestration (Loom Trifecta)

```
Request → StructuredTaskScope (parallel fan-out)
            ├── inventory.check()
            ├── fraud.score()
            ├── payment.charge()  ── sequential
            └── shipping.schedule()
         → CompletableFuture (fire & forget: notify, audit)
```

- **StructuredTaskScope** (ShutdownOnFailure) — parallel phase 1: fail-fast on any error
- **VirtualThread** — 1 task = 1 VT, no pool math
- **ScopedValue** — propagates request context through STS forks
- **CompletableFuture** — async post-processing (does NOT propagate ScopedValue)

### 2. Backpressure (actor/ + backpressure/)

```
                    ┌─────────────────┐
  Request ─────────▶│ TokenBucket     │── Strategy: rate limit algorithm
                    └────────┬────────┘
                             ▼
                    ┌─────────────────┐
                    │ CircuitBreaker  │── State: CLOSED/OPEN/HALF_OPEN
                    └────────┬────────┘
                             ▼
                    ┌─────────────────┐
                    │ Bulkhead        │── Strategy: per-downstream semaphore
                    └────────┬────────┘
                             ▼
                    Downstream.call()
```

### 3. RPC Pipeline (rpc/)

```
Client side:              Server side:
Admit → CheckDeadline →   Admit → CheckDeadline →
Execute → RetryIfBudget   Execute → ShedIfOverloaded
```

### 4. Actor Model (actor/)

```
                         ┌──────────────┐
  Message ──────────────▶│   Mailbox    │── Chain: ordered delivery
                         └──────┬───────┘
                                ▼
                         ┌──────────────┐
                         │   Actor      │── State (Idle/Running/Suspended/Stopped)
                         └──────┬───────┘
                                ▼
                         ┌──────────────┐
                         │  Supervisor  │── Observer: decide RESTART/STOP/ESCALATE
                         └──────────────┘
```

### Pattern composition per sub-system

| Sub-system | Patterns | What it solves |
|---|---|---|
| **Fulfillment** | STS + VT + ScopedValue + CF | Parallel fan-out, context propagation, async logging |
| **Backpressure** | Strategy (TokenBucket) + State (CircuitBreaker) + Strategy (Bulkhead) + Chain (Pipeline) | Rate × health × isolation, composed linearly |
| **RPC** | ValueObject (Deadline) + Strategy (RetryBudget) + Strategy (LoadShedder) + Chain (Pipeline) | Timeout + retry storm prevention + admission control |
| **Actor** | State + Command + Chain (Mailbox) + Observer (Supervisor) + Factory (ActorSystem) | Shared state isolation, failure escalation hierarchy |

### Key interfaces

- `ResiliencePipeline` — rate → health → capacity → call → report
- `RpcPipeline` — admit → deadline → execute → retry
- `ActorSystem` — creates/caches actors, central lifecycle
- `Supervisor` — failure policy per level (task/machine/cluster)

## Reading Order

Read in this sequence — top-level first, then each sub-area bottom-up:

| Step | File | Why read this next |
|------|------|--------------------|
| 1 | `DESIGN_DOC.md` (this file) | Understand problem, design, trade-offs |
| 2 | `ConcurrencyDemo.java` | See all compositions running, then dive deep |
| 3 | **Fulfillment**: `FulfillmentOrchestrator.java` | Core Loom: STS + VTs + ScopedValue + CF |
| 4 | **Fulfillment**: `DownstreamService.java` + `RequestContext.java` | ScopedValue propagation — STS vs CF distinction |
| 5 | **Fulfillment**: `BackpressureLimiter.java` | Why VTs alone don't fix backpressure |
| 6 | **Actor**: `Actor.java` + `ActorState.java` + `ActorMessage.java` | Core actor: State + Command + Mailbox |
| 7 | **Actor**: `Mailbox.java` | Chain — ordered message delivery, why no locks |
| 8 | **Actor**: `ActorSystem.java` | Factory + Registry — creates and caches actors |
| 9 | **Actor**: `Supervisor.java` | Observer + Strategy + Chain — failure escalation |
| 10 | **Actor**: `ActorDemo.java` then `SupervisionTreeDemo.java` | Demos — basic actor then hierarchical supervision |
| 11 | **Backpressure**: `TokenBucket.java` | Strategy — rate limiting algorithm |
| 12 | **Backpressure**: `CircuitBreaker.java` | State — CLOSED/OPEN/HALF_OPEN lifecycle |
| 13 | **Backpressure**: `Bulkhead.java` | Strategy — per-downstream resource isolation |
| 14 | **Backpressure**: `ResiliencePipeline.java` | Chain — composes all three in fixed order |
| 15 | **Backpressure**: `BackpressureDemo.java` | Demo — see pipeline in action |
| 16 | **RPC**: `Deadline.java` | Value Object — absolute timeout, survives serialization |
| 17 | **RPC**: `RetryBudget.java` | Strategy — sliding-window token budget prevents storms |
| 18 | **RPC**: `LoadShedder.java` | Chain + Strategy — priority-based admission |
| 19 | **RPC**: `RpcPipeline.java` | Chain — composes admit → deadline → execute → retry |
| 20 | **RPC**: `RpcDemo.java` | Demo — see pipeline in action |

## Migration Plan

| Phase | Time | What | Risk |
|-------|------|------|------|
| 1 | Month 1 | Add Loom VTs + STS for parallel fulfillment | Low — drop-in replacement for sequential |
| 2 | Month 2 | Add `ResiliencePipeline` (rate limit only) | Low — no behavior change until threshold hit |
| 3 | Month 3 | Add CircuitBreaker + Bulkhead | Medium — misconfigured thresholds cause false positives |
| 4 | Month 4 | Add RPC pipeline: Deadline + RetryBudget | Medium — retry budget too tight drops legitimate retries |
| 5 | Month 5 | Add LoadShedder (priority-based admission) | Medium — misclassified traffic gets wrongly shed |
| 6 | Month 6+ | Add Actor model for stateful workers | High — supervision tree gaps can drop tasks silently |

## Rollback / Safety

- Each gate in ResiliencePipeline individually toggleable
- RetryBudget: start at 10x headroom, tighten over weeks
- CircuitBreaker: HALF_OPEN probe interval starts at 30s
- Deadline: enforce on server side even if client omits it
- Supervision tree: maxRestarts with exponential backoff, not fixed count

## Metrics

| Metric | Current | Target | How |
|--------|---------|--------|-----|
| Fulfillment P50 | 400ms+ (sequential) | < 120ms (parallel) | JFR |
| Downstream error rate | — | < 0.1% after CB opens | CircuitBreaker metrics |
| Retry amplification | 3× (no budget) | < 0.1× (budget) | RetryBudget gauges |
| Load shedding accuracy | — | 100% BATCH shed before CRITICAL | Admission counters |
| Actor restart time | — | < 100ms per level | Supervisor timers |

## Risks

- **Retry budget starvation**: One aggressive client exhausts shared budget. **Mitigation**: per-downstream budget, not global.
- **Deadline not propagated**: CF fork loses ScopedValue deadline. **Mitigation**: capture deadline in local var before CF.
- **Supervision cascade**: Failure at leaf escalates to root too fast. **Mitigation**: each level has separate maxRestarts + backoff.
