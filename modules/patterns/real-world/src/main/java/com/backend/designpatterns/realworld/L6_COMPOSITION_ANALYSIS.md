# L6 Pattern Composition Analysis

## Why the code examples alone aren't enough for L6

The demos show **what** the patterns do together. L6 requires understanding
**when they hurt**, **how they evolve**, **which 7 actually matter**, and
**what happens at Google/Netflix scale**.

---

## 0. Full Index of Real-World Examples

```
real-world/src/main/java/com/backend/designpatterns/realworld/
├── L6_COMPOSITION_ANALYSIS.md                   ◀ this file

├── payments/                                     # 6 patterns
│   └── Adapter + Factory + Strategy + Chain + State + Observer

├── payments/sdk/                                 # 5 patterns (canonical DTO)
│   └── Builder + Strategy + Adapter + Factory + Proxy (role-based view)
│       └── VersionRouter (strangler fig API migration)

├── eventpipeline/                                # 5 patterns
│   └── Adapter + Chain + Factory + Strategy + Observer

├── caching/                                      # 4 patterns
│   └── Proxy + Decorator + Strategy + Factory

├── featureflags/                                 # 4 patterns
│   └── Composite + Strategy + Factory + Proxy

├── orders/                                       # 3 patterns
│   └── Visitor + Composite + Iterator

├── workflows/                                    # 3 patterns
│   └── Command + Memento + Observer

├── coordination/                                 # 3 patterns
│   └── Mediator + Factory + Observer

├── rules/                                        # 3 patterns
│   └── Interpreter + Composite + Strategy

├── notifications/                                # 3 patterns
│   └── Bridge + Factory + Strategy

└── concurrency/                                  # 9 concurrency patterns
    ├── FulfillmentOrchestrator.java  Loom Trifecta + CF + BackpressureLimiter
    ├── actor/                      State + Command + Mailbox + Observer + Factory
    │   └── SupervisionTree         + Chain (failure escalation hierarchy)
    ├── backpressure/               TokenBucket + CircuitBreaker + Bulkhead
    └── rpc/                        Deadline + RetryBudget + LoadShedder
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
| 4 | **Chain of Responsibility** | Passes request through a pipeline of handlers | Middleware, interceptors, processing stages, validation pipelines, supervision trees. Every request goes through stages, and each stage can short-circuit. | Decorator = Chain with single wrapper layer |
| 5 | **Observer** | One-to-many notification on state change | Event-driven architecture, metrics, logging, pub/sub, supervisor health monitoring. The foundation of any async/decentralized system. | Mediator = Observer with routing table |
| 6 | **Composite** | Tree structure of part-whole hierarchies | Config trees, policy conditions, discount rules, menu structures. "Treat individual objects and compositions uniformly." | Interpreter = Composite + Strategy |
| 7 | **State** | Manages lifecycle via state transitions | Order lifecycle, workflow status, connection states, job scheduling, actor lifecycle. "Replace if/else state checks with state objects." | Memento = State snapshot; Command = State action |

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
| Actor model | **State** (lifecycle) + **Command** (message) + **Mailbox** (ordered Chain) + **Observer** (supervisor) + **Factory** (system) |
| Supervision tree | **Chain** of **Observer**s — each supervisor tries, then escalates to parent |
| Resilience pipeline | **Strategy** (rate limit token bucket) + **State** (circuit breaker states) + **Bulkhead** (isolation via Strategy) |
| RPC call pipeline | **Chain** (admit → check time → execute → retry) + **Strategy** (load shedding by priority) + **Value Object** (deadline) |

All 12 real-world examples in this module were built using only these 7
patterns (plus specialized concurrency primitives). Not a single file needed
Prototype, Flyweight, Visitor, or Memento as a primary pattern.

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

### Borg Supervision Tree
- **Patterns**: Chain + Observer + State
- **Reality**: Each Borg task has a borglet supervisor. If the task fails,
  borglet restarts it. If borglet itself fails (OOM, hardware error), the
  machine-level supervisor reschedules. If the whole machine is failing, the
  cluster-level master evacuates all tasks.
- **Why it had to be this way**: A flat supervision model would either restart
  too aggressively (thrashing on hardware failures) or too conservatively
  (task stays dead on transient blips). Hierarchical = different policies at
  each level.
- **What broke**: An L5 engineer set maxRestarts too high on the cluster
  supervisor. A bad binary got restarted 50 times across 1000 machines before
  anyone noticed. Fix: exponential backoff on restarts, not just a count.

### Google Frontend (GFE) Load Shedding
- **Patterns**: Strategy + Chain
- **Reality**: GFE classifies every request as CRITICAL (search, auth),
  INTERACTIVE (recommendations), or BATCH (analytics). When CPU exceeds 80%,
  BATCH requests are shed first. At 90%, INTERACTIVE too. CRITICAL is never
  shed — instead, admission control limits total concurrency.
- **Why it had to be this way**: Without priority-based shedding, a batch
  analytics job can crowd out user-facing search traffic during a traffic spike.
  Random shedding (drop 10% of all traffic) drops critical requests.
- **What broke**: A misconfigured client marked all requests as CRITICAL.
  Load shedding became useless. Fix: server-side reclassification based on
  latency SLO, not client trust.

### gRPC Retry Storm (Outage Postmortem)
- **Patterns**: Strategy (retry budget)
- **Reality**: A 5-second backend blip at 100K QPS with "retry 3 times" policy
  generates 300K extra requests. Those retries cause more load, which causes
  more failures, which causes more retries — positive feedback loop.
- **Why it had to be this way**: Simple retry (maxAttempts = 3) is the default
  in every RPC framework. The budget adds coordination across retries.
  Without it, retry storms amplify outages.
- **What broke**: A single flaky machine caused cascading retry storm across
  the fleet. Fix: retry budget shared per downstream (not per call). 10
  tokens/sec, max 100. Once budget is dry, retries are denied.

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
| **Time-to-recover from failure** | How long between task crash and restart (supervision tree) | < 100ms per level |
| **Retry amplification factor** | Extra load generated by retries during outage | < 0.1× (10% overhead) |
| **Overload shedding accuracy** | % of BATCH requests shed before CRITICAL | 100% (critical never shed) |

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

The same principle applies to concurrency:
- **Don't add actors** until you have shared mutable state problems
- **Don't add circuit breakers** until you see cascading failures
- **Don't add retry budgets** until you see retry storms in postmortems
- **Don't add load shedding** until you have priority SLAs
- **Phase them in**: start with rate limiting, add circuit breaker when you
  see repeated failures, add bulkhead when one slow downstream starts
  affecting others.

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

### Concurrency Anti-Patterns

**Actor Model Anti-Pattern:**
```java
// ❌ L5: Every actor goes through the supervision tree
Actor a = system.actor("worker", handler);
a.send(task);

// ✅ L6: Stateless tasks don't need actors — use plain VTs
if (task.isStateless()) {
    return executor.submit(() -> process(task));
}
return actorSystem.send("worker", task);
```

**Resilience Pipeline Anti-Pattern:**
```java
// ❌ L5: Apply all 3 gates to every call, regardless of downstream
return pipeline.execute(downstreamCall);

// ✅ L6: Skip gates for trusted/co-located downstreams
if (downstream.isLocalhost()) {
    return downstreamCall.call(); // no rate limit, no circuit breaker
}
return pipeline.execute(downstreamCall);
```

**Retry Anti-Pattern:**
```java
// ❌ L5: Retry until success, no budget
for (int i = 0; i < 5; i++) {
    try { return call(); } catch (Exception e) { /* retry */ }
}

// ✅ L6: Budget-aware retry + fail fast for non-retriable errors
if (error.isPermanent()) throw error; // no retry
if (!retryBudget.tryConsume()) throw new BudgetExhaustedException();
return call();
```

### Supervision Tree Anti-Pattern
```java
// ❌ L5: Flat supervision — one supervisor for all actors
var system = new ActorSystem("workers", 10, 60_000);

// ✅ L6: Hierarchical — different policies per level
var perTask = new Supervisor("task", 3, 10_000);     // restart 3x fast
var perMachine = new Supervisor("machine", 10, 300_000, perTask);  // escalate slower
// Different concerns: task transient vs machine hardware
```

---

## 7. How Concurrency Patterns Compose at Google Scale

### Actor Model (State + Command + Mailbox + Observer + Factory)

The actor model is not a new pattern — it's 5 GoF patterns composed:
- **State**: `ActorState` sealed interface (Idle → Running → Suspended → Stopped).
  Each state determines message acceptance. Sealed = no invalid state combinations.
- **Command**: `ActorMessage` envelope with type, payload, and reply `CompletableFuture`.
  Request-response without coupling caller to actor's address.
- **Mailbox**: `LinkedBlockingQueue` + VirtualThread executor. Messages are
  delivered one-at-a-time per actor (Chain variant — ordered, sequential).
- **Observer**: `Supervisor` monitors failures via callback and decides recovery
  (RESTART / STOP / ESCALATE).
- **Factory**: `ActorSystem` creates, caches, and wires actors. Registry for
  named actor lookup.

At Google scale: every Borg task is an actor (borglet = supervisor, machine =
parent supervisor, cluster = root). Every shard in a stateful service is an
actor. Every gRPC stream handler can be an actor.

**Key L6 insight**: Without actors, you need locks for shared mutable state.
With actors, each actor owns its state and communicates via messages. No locks
needed within an actor. But actors add overhead (mailbox, deserialization).
Use them only when you have state contention.

### Supervision Tree (Chain of Observers)

Supervisors form a tree. When a supervisor exceeds its restart budget:
- If it has a parent → ESCALATE (parent decides)
- If it's root → STOP (permanent) or ESCALATE (if many pending messages)

This is Chain of Responsibility applied to failure handling. Each level has
different policy:
- Borglet: restart 3x quickly (handles transient OOM)
- Machine: restart 2x, then escalate (node might be failing)
- Cluster: restart 1x, then stop (cluster-wide issue, human intervention)

### Distributed Backpressure (Strategy + State + Bulkhead)

Three patterns, one resilience pipeline:

| Component | Pattern | Solves | At Google scale |
|-----------|---------|--------|-----------------|
| **TokenBucket** | Strategy (rate limiting algorithm) | Traffic spikes overwhelm downstream | Every service has a rate limit policy |
| **CircuitBreaker** | State (CLOSED/OPEN/HALF_OPEN) | Cascading failures — one blip amplifies | Every gRPC call has a circuit breaker |
| **Bulkhead** | Strategy (resource isolation) | One slow downstream starves others | Each downstream gets its own semaphore pool |

**Key L6 insight**: These three solve different concerns (rate × health ×
isolation). Removing any one creates risk. But adding all three to every call
adds latency. Optimize: skip gates for co-located or trusted downstreams.

### gRPC Pipeline (Value Object + Strategy + Chain)

Three patterns, one RPC call path:

| Component | Pattern | Solves |
|-----------|---------|--------|
| **Deadline** | Value Object (absolute Instant) | Stops wasted work on timed-out calls |
| **RetryBudget** | Strategy (sliding-window token refill) | Caps retry volume, prevents storms |
| **LoadShedder** | Chain + Strategy (priority-based admission) | Drops low-priority under overload |

**Key L6 insight**: Deadline is the most important and most neglected. Without
deadlines, a slow backend holds resources for minutes. With deadlines, work
stops as soon as the client gives up. Deadline propagation through ScopedValue
means child forks inherit the parent's deadline.

### Loom Trifecta + CompletableFuture

| Component | ScopedValue propagation | Use case |
|-----------|------------------------|----------|
| **StructuredTaskScope** | ✅ Propagates to child forks | Parallel fan-out with fail-fast |
| **CompletableFuture** | ❌ Does NOT propagate | Fire-and-forget (logging, analytics) |
| **VirtualThread** | N/A (execution context) | 1 task = 1 VT, no pool math |

**Key L6 insight**: Use STS for parallelism (results matter), CF for async
(post-processing that must not block the response). The ScopedValue distinction
is a common L6 interview trap: "Your deadline propagates through STS but not
through CF. How do you handle this?" Answer: capture the deadline in a local
variable before the CF fork.

---

## 8. How This Maps to L6 Expectations

| L6 Criterion | This material provides | Still missing |
|-------------|----------------------|---------------|
| **Technical depth** | Pattern composition mechanics, 7-pattern distillation, concurrency patterns at scale | Performance modeling (e.g., "adding Chain adds 2ms P99, is that acceptable?") |
| **Technical leadership** | Migration paths, trade-offs, anti-patterns, war stories | Stakeholder management, convincing other teams to adopt |
| **Strategic thinking** | When to compose vs not, 7-pattern shortlist, concurrency phase-in | 3-year roadmap for the pattern's evolution |
| **Ambiguity** | "It depends" analysis, evolution paths, hierarchical supervision | Handling incompletely specified requirements across orgs |
| **Mentorship** | Code as teaching tool, 12 runnable examples, anti-pattern warnings | Design doc reviews, pattern RFCs, team standards |

To close the gap: take any demo and write a **1-page design doc** for it,
covering alternatives considered, risks, and migration plan. That's the L6
muscle.
