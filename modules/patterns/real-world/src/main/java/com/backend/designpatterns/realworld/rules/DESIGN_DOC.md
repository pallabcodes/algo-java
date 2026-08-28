# Design Doc: Rule Engine

| **Design Doc** | **Rule Engine** |
|---|---|
| **Owner** | [You] |
| **Date** | 2026-05 |
| **Status** | Draft |
| **Teams** | Pricing, Fraud, Platform |

## Context

Business rules (discounts, fraud checks, shipping offers) scattered across controllers as if/else. Each rule hardcoded — adding a new rule requires code change + deploy. No way for product team to author rules dynamically.

## Problem

- **Hardcoded rules**: `if (amount > 100 && region == "US")` in 10 places (target: declarative rule expressions)
- **Add/deploy cycle**: Every rule change = PR + deploy (target: rules from config, no deploy)
- **Condition logic duplication**: Same `amount > X && region == Y` repeated (target: composable expression trees)
- **Action coupling**: Condition + action in same code block (target: separate RuleExpr from RuleAction)

## Alternatives Considered

| Alternative | Why Rejected |
|---|---|
| **1. Keep if/else per rule** | Hardcoded, no dynamic composition |
| **2. Drools / rule engine library** | Heavy dependency; steep learning curve |
| **3. YAML config with if/else parser** | Just moves if/else to parser |
| **4. Composite + Interpreter + Strategy** | ✅ Chosen |

## Design

3 patterns compose:

```
  RuleEngine ──▶ RuleSet (Strategy)
                    │
                    ├── Rule 1: RuleExpr ──▶ RuleAction
                    │              │
                    │         Composite tree
                    │         ├── And ── FieldEquals(region, US)
                    │         │       └── FieldGreaterThan(amount, 10000)
                    │         └── Or  ── ...
                    │
                    ├── Rule 2: RuleExpr ──▶ RuleAction
                    └── Rule 3: ...
```

### Pattern composition

| Pattern | File(s) | What it does |
|---------|---------|--------------|
| **Composite** | `RuleExpr` sealed interface + `And`/`Or`/`Not`/`FieldEquals`/`FieldGreaterThan` | Expression trees — arbitrarily nested AND/OR/NOT with leaf conditions |
| **Interpreter** | `RuleExpr.evaluate(Context)` | Recursive tree walk evaluates expression to boolean |
| **Strategy** | `RuleEngine.DiscountEngine` + rule sets | Swappable rule sets per context (region, customer tier, season) |
| **Command** (variant) | `RuleAction` + `ApplyDiscount`/`FlagForReview`/`FreeShipping` | Encapsulates action as object; separated from condition evaluation |

### Key interfaces

- `RuleExpr` — sealed: `And | Or | Not | FieldEquals | FieldGreaterThan`
- `RuleAction` — `execute(OrderContext)`
- `RuleEngine` — evaluates all rules in set, returns matching actions

## Reading Order

| Step | File | Why read this next |
|------|------|--------------------|
| 1 | `DESIGN_DOC.md` (this file) | Understand problem, design, trade-offs |
| 2 | `RuleExpr.java` | Composite + Interpreter — expression tree nodes |
| 3 | `RuleAction.java` | Command variant — actions separated from conditions |
| 4 | `RuleEngine.java` | Strategy — rule sets pluggable per context |
| 5 | `RuleEngineDemo.java` | See all 3 patterns compose |

## Migration Plan

| Phase | Time | What | Risk |
|-------|------|------|------|
| 1 | Week 1 | Extract `RuleExpr` sealed interface + basic leaves (FieldEquals) | Low |
| 2 | Week 2 | Add Composite operators (And, Or, Not) | Low |
| 3 | Week 3 | Add `RuleEngine` + `RuleAction` | Low |
| 4 | Week 4 | Migrate first rule batch from if/else to RuleExpr | Medium — verify behavior match |
| 5 | Week 5 | Add rule set configuration (YAML/JSON → RuleExpr) | Medium — deserialization edge cases |

## Rollback / Safety

- Unknown RuleExpr type → rule returns false (fail closed)
- RuleAction failures caught per action — one action failure doesn't abort remaining
- Rule set config validated at load time (not evaluation time)

## Metrics

| Metric | Current | Target | How |
|--------|---------|--------|-----|
| Time to add rule | 2 days (code+deploy) | < 1 hour (config change) | Config deploy time |
| Rule condition reuse | 0% (repeated if/else) | > 50% (shared expressions) | Expression DAG analysis |
| Rule expression test coverage | — | per-node unit tests | Expression coverage |

## Risks

- **Expression tree performance**: Deeply nested AND/OR trees on hot path. **Mitigation**: max depth limit (20); short-circuit evaluation.
- **Config injection risk**: Deserializing RuleExpr from config = code injection surface. **Mitigation**: whitelist of allowed leaf types; no arbitrary code execution.
- **Action side effects**: RuleAction may fail halfway (discount applied, notification not sent). **Mitigation**: actions should be idempotent; orchestrate via Command with undo.
