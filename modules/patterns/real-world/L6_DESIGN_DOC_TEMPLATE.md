# L6 Design Doc Template

> Use this template to write a 1-page design doc for any pattern composition.
> Google's L6 promo packet needs the *story* — not just the code.
>
> Below is a **filled example** (Payment Gateway + SDK Integration), followed by a
> **blank template**.

---

## FILLED EXAMPLE: Multi-Provider Payment Gateway

| **Design Doc** | **Multi-Provider Payment Gateway** |
|---|---|
| **Owner** | You |
| **Date** | 2026-05 |
| **Status** | Draft → Review → Approved |
| **Teams** | Checkout, Payments, Fraud, Accounting |

### Context

We support 6 payment providers (Stripe, PayPal, GPay, Adyen, Braintree, Square)
across 12 regions. Each provider has a different SDK, auth mechanism, and
response format. Currently, routing logic is 350 lines of `if/else` in
`CheckoutController.java`. Adding a new provider takes 3-4 weeks and
frequently breaks existing flows.

### Problem

- **Time-to-add-provider**: 3-4 weeks (target: < 1 week)
- **Bug rate**: 40% of payment-related P0s involve incorrect provider routing
- **Testing surface**: 6 providers × 4 states × 3 error paths = 72 scenarios,
  but only 20 are covered
- **Team coupling**: Checkout team can't ship without Payments team reviewing
  every routing change

### Alternatives Considered

| Alternative | Why Rejected |
|---|---|
| **1. Keep if/else, add tests** | Testing doesn't fix the coupling or the 3-week lead time |
| **2. Extract Adapter only, skip Factory** | Without Factory, creation logic still lives in controllers; no config-driven routing |
| **3. Use a third-party payment gateway aggregator** | Aggregator doesn't support 2 of our 6 providers; adds latency and cost |
| **4. Adapter + Factory + Chain + State + Observer** | ✅ Chosen |

### Design

```
                    ┌─────────────┐
  Request ─────────▶│   Router    │─── Strategy selects provider
                    └──────┬──────┘
                           ▼
                    ┌─────────────┐
                    │   Factory   │─── Creates Adapter for selected provider
                    └──────┬──────┘
                           ▼
                    ┌─────────────┐
                    │   Pipeline  │─── Chain: Validate → Fraud → Charge → Notify
                    └──────┬──────┘
                           ▼
                    ┌─────────────┐
                    │   Adapter   │─── Normalizes SDK response to canonical DTO
                    └──────┬──────┘
                           ▼
                    ┌─────────────┐
                    │   EventBus  │─── Observer: Accounting, Inventory, Analytics
                    └─────────────┘

States: Initiated → Authorizing → Captured → Settled
                              ↘ Failed
                   Captured → Refunded
```

**Canonical DTO pattern** (see `payments/sdk/`):
- `CanonicalPaymentRequest` → `RequestBuilderStrategy` → Provider SDK
- Provider SDK response → `ResponseNormalizer` → `CanonicalPaymentResponse`
- `RoleBasedView` (Proxy) shapes response per caller role

### Migration Plan

| Phase | Time | What | Risk |
|-------|------|------|------|
| 1 | Month 1 | Extract Adapter interface. Move Stripe behind it. | Low — no behavior change |
| 2 | Month 2 | Add Factory. Migrate PayPal, GPay. | Medium — need regression tests per provider |
| 3 | Month 3-4 | Add Chain (validate, fraud). Ship as middleware. | Medium — fraud check adds 5ms latency |
| 4 | Month 5-6 | Add State machine for transaction lifecycle. | High — state transition gaps can cause data loss |
| 5 | Month 7-8 | Add Observer (EventBus). Migrate 3 subscribers. | Low — add after stable |
| 6 | Never | Don't add patterns that aren't justified by data. | — |

### Rollback / Safety

- Each provider change is toggled by a feature flag
- New pipeline runs in shadow mode for 2 weeks (log results, don't act)
- State machine has a dead-letter queue for invalid transitions
- EventBus is async with bounded queue (1K) — backpressure drops events, not payments

### Metrics

| Metric | Current | Target | How measured |
|--------|---------|--------|-------------|
| Time-to-add-provider | 3-4 weeks | < 1 week | Ticket tracking |
| Pipeline P50 latency | — | < 10ms overhead | JFR / Micrometer |
| Test scenarios covered | 20 / 72 | 65 / 72 | Coverage report |
| Debugging time (P0) | 2+ hours | < 30 min | Incident post-mortem |
| Team onboarding | 3 weeks | < 2 weeks | New hire survey |

### Risks

- **Pattern over-engineering**: If we stay at 1-2 providers for >12 months,
  this is too much abstraction. Mitigation: revisit at 6-month checkpoint.
- **EventBus backpressure**: Synchronous observers block the charge path.
  Mitigation: async EventBus with timeout + fallback to sync.
- **State machine gaps**: Missed transitions (e.g., "can we refund a FAILED txn?").
  Mitigation: exhaustive transition matrix as a single file, tested in CI.

---

## BLANK TEMPLATE

Copy this for your own design doc.

---

| **Design Doc** | **[Title — What are you building?]** |
|---|---|
| **Owner** | [Your Name] |
| **Date** | [YYYY-MM] |
| **Status** | Draft |
| **Teams** | [Affected Teams] |

### Context

[2-3 sentences. What's the current state? What's the business/organizational
pressure? What systems are involved?]

### Problem

[Bullet list. What's broken? Make it measurable.]

- **Problem 1**: [Current metric] (target: [target metric])
- **Problem 2**: [Current behavior] (target: [desired behavior])
- **Problem 3**: [Org pain] (target: [org goal])

### Alternatives Considered

| Alternative | Why Rejected |
|---|---|
| **1. Do nothing** | [Reason] |
| **2. [Alternative]** | [Why not chosen] |
| **3. [Alternative]** | [Why not chosen] |
| **4. [Chosen]** | ✅ |

### Design

[2-3 sentences + architecture diagram (ASCII).]

```
[ASCII architecture diagram showing:
 - Entry point
 - Which patterns compose at which layer
 - Data flow arrows
 - State transitions if applicable
]
```

**Pattern composition**:
- **[Pattern 1]**: [What it does in this system]
- **[Pattern 2]**: [What it does in this system]
- **...**

**Key interfaces**:
- `[Interface/Class]` — [Responsibility]
- `[Interface/Class]` — [Responsibility]

### Migration Plan

| Phase | Time | What | Risk |
|-------|------|------|------|
| 1 | [Timeline] | [First step — smallest possible change] | [Risk level + why] |
| 2 | [Timeline] | [Second step] | [Risk level + why] |
| 3 | [Timeline] | [Third step] | [Risk level + why] |
| ... | ... | ... | ... |

### Rollback / Safety

- [Feature flag? Shadow mode?]
- [Data integrity guarantees]
- [Error handling strategy]

### Metrics

| Metric | Current | Target | How measured |
|--------|---------|--------|-------------|
| [Metric 1] | [Current value] | [Target value] | [Measurement tool] |
| [Metric 2] | [Current value] | [Target value] | [Measurement tool] |
| [Metric 3] | [Current value] | [Target value] | [Measurement tool] |

### Risks

- **[Risk 1]**: [Description]. **Mitigation**: [Plan].
- **[Risk 2]**: [Description]. **Mitigation**: [Plan].

---

## How to Use This for L6 Promo

Your promo packet needs 3-5 of these design docs covering different
pattern compositions you shipped. Each doc should show:

1. **Before** — concrete pain (broken metrics, slow teams)
2. **Alternatives** — you considered other approaches and can explain why
3. **Migration** — you didn't ship it all at once; you phased it
4. **Impact** — metrics moved in the right direction

The **code** in `real-world/` shows what the final state looks like.
The **design doc** shows how you got there without breaking things.
The L6 committee needs both.
