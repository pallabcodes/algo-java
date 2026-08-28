# Design Doc: Multi-Provider Payment Gateway

| **Design Doc** | **Multi-Provider Payment Gateway** |
|---|---|
| **Owner** | [You] |
| **Date** | 2026-05 |
| **Status** | Draft |
| **Teams** | Checkout, Payments, Fraud, Accounting |

## Context

3 payment providers (Stripe, PayPal, GPay). Different SDKs, auth, response shapes. Routing logic in controller if/else. Adding provider = 3-4 weeks, high regression risk.

## Problem

- **Time-to-add-provider**: 3-4 weeks (target: < 1 week)
- **Bug rate**: 40% of payment P0s from incorrect routing
- **Test coverage**: 6 providers × 4 states × 3 error paths = 72 scenarios, only ~20 covered
- **Team coupling**: Checkout can't ship without Payments review on every route change

## Alternatives Considered

| Alternative | Why Rejected |
|---|---|
| **1. Keep if/else, add tests** | Tests don't fix coupling or lead time |
| **2. Adapter-only, no Factory** | Creation logic stays in controllers |
| **3. Third-party aggregator** | Doesn't support all providers; adds latency + cost |
| **4. Adapter + Factory + Strategy + Chain + State + Observer** | ✅ Chosen |

## Design

6 patterns compose at different layers:

```
                    ┌──────────────┐
  Req ─────────────▶│   Router     │── Strategy selects provider
                    └──────┬───────┘
                           ▼
                    ┌──────────────┐
                    │   Factory    │── Creates adapter instance
                    └──────┬───────┘
                           ▼
                    ┌──────────────┐
                    │   Pipeline   │── Chain: Validate → Fraud → Charge → Notify
                    └──────┬───────┘
                           ▼
                    ┌──────────────┐
                    │   Adapter    │── Normalizes SDK to canonical DTO
                    └──────┬───────┘
                           ▼
                    ┌──────────────┐
                    │   EventBus   │── Observer: Accounting, Inventory, Analytics, Fraud
                    └──────────────┘

States: Initiated → Authorizing → Captured → Settled
                              ↘ Failed
                   Captured → Refunded
```

### Pattern composition

| Pattern | File(s) | What it does |
|---------|---------|--------------|
| **Adapter** | `PaymentProvider` + `StripeAdapter`/`PayPalAdapter`/`GPayAdapter` | Normalizes 3 different SDKs into single interface |
| **Factory** | `PaymentProviderFactory` | Creates/caches adapters. `defaultProviders()` registers all 3. No if/else creation. |
| **Strategy** | `PaymentRouter` + `ByRegion`/`ByUserPreference`/`RoundRobin` | Swaps routing algorithm at runtime. Demo swaps region routing to user preference mid-flow. |
| **Chain** | `ProcessingPipeline` + `PipelineHandler` | Stages: validate → fraudCheck → charge → postProcess. Each handler can short-circuit (return non-null). |
| **State** | `TransactionState` (sealed interface) | 6 states via records. `next()` encodes valid transitions. `isTerminal()` guards terminal states. |
| **Observer** | `PaymentEventBus` + `PaymentObserver` | 4 subscribers: accounting, inventory, analytics, fraud. Decoupled from charge pipeline. |

## Reading Order

Files to study in sequence, from interface to demo:

| Step | File | Why read this next |
|------|------|--------------------|
| 1 | `DESIGN_DOC.md` (this file) | Understand problem, design, trade-offs |
| 2 | `PaymentRequest.java` + `PaymentResult.java` | DTOs — data flowing through system |
| 3 | `PaymentProvider.java` | Adapter interface — core abstraction all providers implement |
| 4 | `StripeAdapter.java` | First concrete Adapter — see how SDK normalizes |
| 5 | `PaymentProviderFactory.java` | Factory — how adapters get created without if/else |
| 6 | `PaymentRouter.java` | Strategy — how provider is selected at runtime |
| 7 | `PipelineHandler.java` + `ProcessingPipeline.java` | Chain — stages are independent, short-circuitable |
| 8 | `TransactionState.java` | State — lifecycle via sealed interface |
| 9 | `PaymentEventBus.java` + `PaymentObserver.java` + `PaymentEvent.java` | Observer — decoupled downstream consumers |
| 10 | `Transaction.java` | State holder — ties TransactionState to data |
| 11 | `PaymentDemo.java` | See all 6 patterns compose in 4 scenarios |

### Canonical DTO (see `sdk/`)

- `CanonicalPaymentRequest` → `RequestBuilderStrategy` → Provider SDK
- Provider SDK response → `ResponseNormalizer` → `CanonicalPaymentResponse`
- `RoleBasedView` (Proxy) shapes response per caller role
- `VersionRouter` (Strangler Fig) routes old vs new API shape

## Migration Plan

Phase in patterns, don't ship all at once:

| Phase | Time | What | Risk |
|-------|------|------|------|
| 1 | Month 1 | Extract `PaymentProvider` interface. Move Stripe behind it. | Low — behavior unchanged |
| 2 | Month 2 | Add `PaymentProviderFactory`. Migrate PayPal, GPay. | Medium — regression per provider |
| 3 | Month 3-4 | Add `ProcessingPipeline` (validate, fraud). | Medium — fraud adds ~5ms |
| 4 | Month 5-6 | Add `TransactionState` sealed interface. | High — missing transitions cause data loss |
| 5 | Month 7-8 | Add `PaymentEventBus`. Migrate 4 subscribers. | Low — add after stable pipeline |
| 6 | Ongoing | Short-circuit in hot path (skip chain for small txns). | Low |

## Rollback / Safety

- Feature flag per provider migration
- Pipeline runs in shadow mode (log, don't act) for 2 weeks
- State machine: `Failed` and `Refunded` are terminal — no illegal transitions
- EventBus: synchronous in demo; production should async with bounded queue (1K)

## Metrics

| Metric | Current | Target | How |
|--------|---------|--------|-----|
| Time-to-add-provider | 3-4 weeks | < 1 week | Ticket tracking |
| Pipeline P50 overhead | — | < 10ms | JFR/Micrometer |
| Test coverage | 20/72 | 65/72 | Coverage report |
| Debug time (P0) | 2+ hr | < 30 min | Post-mortem |
| Onboarding | 3 weeks | < 2 weeks | New hire survey |

## Risks

- **Over-engineering**: 6 patterns for 3 providers is heavy. **Mitigation**: revisit at month 6. If still 3 providers, simplify.
- **State machine gaps**: Can't refund a `Failed` txn? **Mitigation**: exhaustive transition matrix in CI.
- **EventBus backpressure**: Sync observers block charge. **Mitigation**: async + bounded queue + fallback to sync.
