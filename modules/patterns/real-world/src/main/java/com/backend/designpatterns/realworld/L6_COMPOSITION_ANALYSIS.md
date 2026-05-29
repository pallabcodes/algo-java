# L6 Pattern Composition Analysis

## Why the code examples alone aren't enough for L6

The demos show **what** the patterns do together. L6 requires understanding
**when they hurt**, **how they evolve**, **which 7 actually matter**, and
**what happens at Google/Netflix scale**.

---

## 0. Full Index of Real-World Examples

```
real-world/src/main/java/com/backend/designpatterns/realworld/
├── L6_COMPOSITION_ANALYSIS.md       ◀ this file

├── payments/                         # 6 patterns
│   └── Adapter + Factory + Strategy + Chain + State + Observer

├── payments/sdk/                     # 5 patterns (canonical DTO pattern)
│   └── Builder + Strategy + Adapter + Factory + Proxy (role-based view)

├── eventpipeline/                    # 5 patterns
│   └── Adapter + Chain + Factory + Strategy + Observer

├── caching/                          # 4 patterns
│   └── Proxy + Decorator + Strategy + Factory

├── featureflags/                     # 4 patterns
│   └── Composite + Strategy + Factory + Proxy

├── orders/                           # 3 patterns
│   └── Visitor + Composite + Iterator

├── workflows/                        # 3 patterns
│   └── Command + Memento + Observer

├── coordination/                     # 3 patterns
│   └── Mediator + Factory + Observer

├── rules/                            # 3 patterns
│   └── Interpreter + Composite + Strategy

└── notifications/                    # 3 patterns
    └── Bridge + Factory + Strategy
```

---

## 1. The 7 Patterns That Matter (The L6 Shortlist)

There are 23+ GoF patterns. In production at scale, only 7 do 90% of the work.
The rest are special cases, language features, or combinations of these 7.

### The Core 7

| # | Pattern | What it does | Why it survives | Collapses into this |
|---|---------|-------------|-----------------|---------------------|
| 1 | **Factory** | Creates objects without specifying concrete classes | Every non-trivial system has config-driven creation — regions, providers, feature flags. Without Factory, you have `if/else` creation everywhere. | Abstract Factory = Factory of Factories |
| 2 | **Adapter** | Translates one interface to another | Every system talks to external APIs/SDKs with incompatible shapes. Multi-provider strategy requires normalization. | Bridge = Strategy + Adapter |
| 3 | **Strategy** | Selects algorithm at runtime | A/B tests, multi-region routing, discount calculation, payment method selection. "Swap the algorithm without changing the caller." | Template Method = Strategy with inheritance (worse) |
| 4 | **Chain of Responsibility** | Passes request through a pipeline of handlers | Middleware, interceptors, processing stages, validation pipelines. Every request goes through stages, and each stage can short-circuit. | Decorator = Chain with single wrapper layer |
| 5 | **Observer** | One-to-many notification on state change | Event-driven architecture, metrics, logging, pub/sub. The foundation of any async/decentralized system. | Mediator = Observer with routing table |
| 6 | **Composite** | Tree structure of part-whole hierarchies | Config trees, policy conditions, discount rules, menu structures. "Treat individual objects and compositions uniformly." | Interpreter = Composite + Strategy |
| 7 | **State** | Manages lifecycle via state transitions | Order lifecycle, workflow status, connection states, job scheduling. "Replace if/else state checks with state objects." | Memento = State snapshot; Command = State action |

### The Other 13 (And Why They're Secondary)

| Pattern | Why it's not in the core 7 |
|---------|---------------------------|
| **Singleton** | DI concern, not a pattern. Guice/Spring manages scope. |
| **Builder** | Language feature now (records + builders). Lombok, Kotlin data classes. |
| **Prototype** | Cloning. Rarely needed outside of object pooling. |
| **Facade** | Just a wrapper class. Every service layer is a facade. |
| **Flyweight** | Memory optimization. Domain-specific (e.g., glyph rendering). |
| **Proxy** | AOP handled by frameworks (Spring AOP, AspectJ). |
| **Bridge** | Strategy selects, Adapter wraps = Bridge. It's two patterns combined. |
| **Decorator** | Chain of Responsibility with a single wrapping handler. |
| **Template Method** | Strategy but with inheritance. Composition > Inheritance. |
| **Command** | State + Memento. An action with undo = state transition + snapshot. |
| **Interpreter** | Composite + Strategy. Expression tree evaluated via strategy. |
| **Mediator** | Observer with routing. A specialized event bus. |
| **Visitor** | Pattern matching in Java 21+ replaces most Visitor use cases. |
| **Memento** | State snapshot. Part of State pattern, not standalone. |
| **Iterator** | Built into every collection in every language. |

### The Test: Can you build everything with 7?

| Real-world need | Core 7 composition |
|----------------|-------------------|
| Multi-provider payment integration | **Factory** → creates **Adapter**s; **Strategy** selects route; **Chain** validates/frauds/charges; **State** tracks lifecycle; **Observer** emits events |
| Middleware pipeline | **Chain** of **Strategy**-selected handlers; **Factory** creates pipeline per route |
| Feature flags | **Composite** builds condition trees; **Strategy** evaluates; **Factory** creates from config |
| Transactional workflow | **State** machine; **Observer** emits events; **Factory** creates steps |
| Rule engine | **Composite** expression tree; **Strategy** selects rules; **Interpreter** evaluates |
| Service orchestration | **Mediator** (**Observer** with routing); **Factory** wires services |
| Multi-layer caching | **Chain** of caches; **Strategy** for eviction; **Factory** creates topology |

All 8 real-world examples in this module were built using only these 7
patterns (plus specialized variants). Not a single file needed Prototype,
Flyweight, Visitor, or Memento as a primary pattern.

---

## 2. Payment Gateway — When This Combo FAILS

### The Trade-offs (L6 must articulate these)

| Concern | Cost | Mitigation |
|---------|------|------------|
| **Chain latency** | Each handler adds 0.5-5ms. At 100K QPS, fraud check alone costs 500 CPU-seconds. | Branch prediction: skip fraud for <$5 txns (Strategy selects pipeline variant). |
| **Adapter explosion** | 20 providers × 3 methods = 60 implementations. Each is a maintenance burden. | Use a meta-adapter: map provider API to canonical model via config, not code. |
| **EventBus backpressure** | Synchronous observers block the payment thread. One slow observer (200ms DB write) holds up the whole checkout. | Make EventBus async with bounded queue + dead-letter topic. |
| **State machine complexity** | 6 states × 4 transitions × 3 error paths = lots of testing. Easy to miss a transition (e.g., "can we refund a FAILED transaction?"). | State transition matrix as a single source of truth. |

### When NOT to use this composition

- **Single provider, no fraud needs**: If you only use Stripe and trust it,
  6 patterns are over-engineering. A simple `PaymentService.charge()` is better.
- **Low QPS (< 10/sec)**: The indirection adds complexity without benefit.
  Pattern composition shines at scale, not in startups.
- **Prototyping / MVP**: Start with if/else. Extract patterns when you hit pain.
  Premature pattern composition is the #1 L5 mistake.

### Evolution Path (how it really grows at scale)

```
Phase 1:   charge() { stripe.charge() }                     — Monolith
Phase 2:   charge() { if(region==US) stripe else paypal }   — if/else routing
Phase 3:   PaymentProvider interface + ProviderFactory       — Adapter + Factory
Phase 4:   + PaymentRouter (Strategy)                       — Pluggable routing
Phase 5:   + FraudCheck, Validate (Chain)                   — Cross-cutting stages
Phase 6:   + TransactionState, EventBus                     — Lifecycle + events
```

Each phase takes 6-18 months. L6 designs for Phase 6 but deploys Phase 2.

---

## 3. War Stories

### YouTube Video Processing Pipeline
- **Patterns**: Chain + Strategy + Observer + Factory
- **Reality**: A raw video goes through 12+ stages (codec detect → transcode →
  thumbnail → audio → captions → manifest). Each stage is a Chain handler.
  Strategy picks the codec per format/region. Observer emits progress to
  pub/sub for the UX team. Factory creates pipeline variants for short/long
  videos.
- **Why it had to be this way**: Different teams own different stages (codecs
  vs audio vs ML thumbnails). Chain allows each team to deploy independently.
- **What broke**: When one stage 500'd, the entire pipeline failed. Fix: each
  stage has a timeout + fallback strategy (skip thumbnail if unreachable).

### Google Ads Bidding (Real-Time)
- **Patterns**: Strategy + Adapter + Chain + Proxy
- **Reality**: Each ad auction runs 50+ bidding strategies (by CPC, by CPA,
  by ROAS). Each DSP (Google, Meta, Amazon) has a different API (Adapter).
  The bid goes through validation → budget check → auction → logging (Chain).
  Proxy wraps the entire flow with experiment flags.
- **Why it had to be this way**: 200ms budget for the entire auction. No time
  for abstraction overhead. The patterns are optimized via JIT inlining.
- **What broke**: Adapter abstraction leaked (Meta's API has fundamentally
  different budget semantics). Fix: canonical model with provider-specific
  extensions (Composite pattern inside the Adapter).

### Borg Resource Scheduling
- **Patterns**: Chain + State + Strategy
- **Reality**: A job goes through: queued → scheduled → running → evicted →
  rescheduled → done. Each transition is a Chain handler (feasibility check →
  resource accounting → bin-packing → launch → health check). State machine
  tracks every job across 10K+ machines.
- **Why it had to be this way**: Chain allows injecting preemption logic,
  gang scheduling, GPU affinity at different stages without core changes.
- **What broke**: The state machine had 200+ states for some job types.
  Fix: split into hierarchical state machines (job state vs task state).

---

## 4. Measuring Pattern Effectiveness (L6 Metrics)

Patterns are not "correct" or "incorrect" — they have measurable costs:

| Metric | What it measures | L6 target |
|--------|------------------|-----------|
| **Time-to-add-provider** | How long to add a new payment adapter | < 1 week |
| **Pipeline P50 latency** | Cost of Chain indirection | < 5ms overhead |
| **Debugging difficulty** | Time to trace a production issue through patterns | < 30min |
| **Test surface area** | How many tests needed per pattern node | Linear, not exponential |
| **Team onboarding** | Time for new engineer to understand the composition | < 2 weeks |

If a pattern composition fails any of these, simplify.

---

## 5. The Real L6 Question

The demos ask "how do I compose patterns?"

The L6 interview asks: **"Your team's checkout system has 6 providers and
growing. It's becoming unmanageable. Design the path from chaos to clarity
without stopping shipping. You have 6 months and 4 teams to coordinate."**

The correct answer is not "use Adapter + Factory + Chain + State + Observer."
The correct answer is:
1. **Phase 1 (months 1-2)**: Extract the Adapter interface. Single provider
   behind it. No other changes. Ship fast.
2. **Phase 2 (months 2-4)**: Add Factory. Migrate 1 provider per week.
3. **Phase 3 (months 4-5)**: Identify the cross-cutting concern (fraud).
   Add it as a Chain handler, not a method call.
4. **Phase 4 (months 5-6)**: Inject Strategy for routing. Only if the data
   shows it's needed.
5. **Never**: Add EventBus unless you have 3+ consumers.

This is the L6 difference: **patterns as a migration plan, not a blueprint.**

---

## 6. Each Demo's Anti-Pattern (What would get a -1 from an L6 reviewer)

### Payment Gateway Anti-Pattern
```java
// ❌ L5: Every transaction goes through all 6 patterns
PaymentProvider p = factory.create("Stripe");
router.route(req, List.of(p));
pipeline.execute(req, p, tx);
eventBus.publish(...);

// ✅ L6: Short-circuit for simple cases
if (req.amountCents() < 500 && "US".equals(req.region())) {
    return simpleCharge(req); // skip factory, router, pipeline
}
return fullComposition(req);
```

### Event Pipeline Anti-Pattern
```java
// ❌ L5: Every event type goes through the same pipeline
handlerFactory.forType(event.type()).execute(event);

// ✅ L6: Hot path bypasses the chain entirely
if ("Heartbeat".equals(event.type())) {
    routingStrategy.route(event); // skip deserialize/validate/transform
    return;
}
```

### Cache Anti-Pattern
```java
// ❌ L5: Every get() traverses all layers
return layeredCache.get(key);

// ✅ L6: L1 has bloom filter, skips L2/L3 for definitely-absent keys
if (!bloomFilter.mightContain(key)) return null;
return layeredCache.get(key);
```

---

## 7. How This Maps to L6 Expectations

| L6 Criterion | This material provides | Still missing |
|-------------|----------------------|---------------|
| **Technical depth** | Pattern composition mechanics, 7-pattern distillation | Performance modeling (e.g., "adding Chain adds 2ms P99, is that acceptable?") |
| **Technical leadership** | Migration paths, trade-offs, anti-patterns | Stakeholder management, convincing other teams to adopt |
| **Strategic thinking** | When to compose vs not, 7-pattern shortlist | 3-year roadmap for the pattern's evolution |
| **Ambiguity** | "It depends" analysis, evolution paths | Handling incompletely specified requirements |
| **Mentorship** | Code as teaching tool, 8 runnable examples | Design doc reviews, pattern RFCs |

To close the gap: take any demo and write a **1-page design doc** for it,
covering alternatives considered, risks, and migration plan. That's the L6
muscle.
