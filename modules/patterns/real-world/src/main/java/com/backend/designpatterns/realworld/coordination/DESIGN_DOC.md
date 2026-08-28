# Design Doc: Order Service Coordination

| **Design Doc** | **Order Service Coordination** |
|---|---|
| **Owner** | [You] |
| **Date** | 2026-05 |
| **Status** | Draft |
| **Teams** | Checkout, Fulfillment, Platform |

## Context

Order placement touches 4 services: order, payment, inventory, shipping. Currently each service calls others directly — circular dependencies, hard to test, changing flow requires changing every service.

## Problem

- **Circular coupling**: OrderService → PaymentService → InventoryService → OrderService (target: no service references another)
- **Change ripple**: Adding a 5th service or reordering steps changes N classes (target: 1 class change)
- **No observability**: No central place to log/monitor the choreography (target: Observer hooks at each step)
- **Testing**: Integration test requires all 4 services running (target: unit-test each service + mediator in isolation)

## Alternatives Considered

| Alternative | Why Rejected |
|---|---|
| **1. Keep direct calls** | Circular deps, N classes change per flow update |
| **2. Orchestrator service** | Extra network hop, serializes through one process |
| **3. Event-driven choreography** | Hard to reason about flow; eventual consistency gaps |
| **4. Mediator + Observer + Factory** | ✅ Chosen |

## Design

3 patterns compose:

```
  Caller ──▶ OrderMediator ──▶ OrderService
                  │           ├── InventoryService
                  │           ├── PaymentService
                  │           └── ShippingService
                  │
                  ▼
           OrderEventBus ──▶ Logger (Observer)
                        └── Metrics (Observer)
```

### Pattern composition

| Pattern | File(s) | What it does |
|---------|---------|--------------|
| **Mediator** | `OrderMediator` | Encapsulates choreography. Services never reference each other. One class owns the flow. |
| **Observer** | `OrderEventBus` + logger/metrics | Decoupled monitoring. Adding subscriber = one line. |
| **Factory** | `OrderMediator.createDefault()` | Wires mediator + services + event bus. |

### Key interfaces

- `ServiceComponent` — service contract (order/payment/inventory/shipping)
- `OrderEventBus` — `publish()` + `compose()` (functional composition of observers)

## Reading Order

| Step | File | Why read this next |
|------|------|--------------------|
| 1 | `DESIGN_DOC.md` (this file) | Understand problem, design, trade-offs |
| 2 | `ServiceComponent.java` | Service contract — what each participant looks like |
| 3 | `OrderMediator.java` | Mediator — choreography logic, services never reference each other |
| 4 | `OrderEventBus.java` | Observer — decoupled monitoring hooks |
| 5 | `MediatorDemo.java` | See all 3 patterns compose |

## Migration Plan

| Phase | Time | What | Risk |
|-------|------|------|------|
| 1 | Week 1 | Extract `ServiceComponent` interface per service | Low |
| 2 | Week 2 | Create `OrderMediator`, move orchestration logic in | Medium — verify behavior matches existing flow |
| 3 | Week 3 | Add `OrderEventBus` + 2 observers (log, metrics) | Low |
| 4 | Week 4 | Unit-test mediator with mock services | Low |

## Rollback / Safety

- Mediator is in-process — no distributed transaction risk
- EventBus exceptions caught per observer — one observer failure doesn't break flow
- Idempotent service calls — safe to retry on failure

## Metrics

| Metric | Current | Target | How |
|--------|---------|--------|-----|
| Flow change impact | N classes | 1 class | Code review |
| Service testability | Integration only | Unit tests | Test count |
| Observability | Manual log spelunking | Step-level events | EventBus metrics |

## Risks

- **Mediator becomes god object**: All flow logic in one class grows unbounded. **Mitigation**: split into sub-mediators per domain (OrderMediator, PaymentMediator).
- **Observer blocks main flow**: Sync observer (DB write) delays response. **Mitigation**: async EventBus with bounded queue for non-critical observers.
