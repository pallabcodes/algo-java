# Design Doc: Feature Flag System

| **Design Doc** | **Feature Flag System** |
|---|---|
| **Owner** | [You] |
| **Date** | 2026-05 |
| **Status** | Draft |
| **Teams** | Platform, Product, SRE |

## Context

Features ship directly — no toggle, no gradual rollout, no kill switch. One bad deploy affects all users. Experiments require separate code paths. Rollback = redeploy.

## Problem

- **No gradual rollout**: Features go 0→100% instantly (target: staged rollout: 1% → 10% → 50% → 100%)
- **No kill switch**: Rollback = redeploy, takes 30+ min (target: toggle off in seconds)
- **No experiment support**: A/B tests require separate code branches (target: flag-based cohort assignment)
- **Condition explosion**: `if (region == US && percent < 50)` scattered across codebase (target: composable condition trees)

## Alternatives Considered

| Alternative | Why Rejected |
|---|---|
| **1. Keep if/else per feature** | Scattered logic, no central visibility |
| **2. LaunchDarkly/FF4J** | Dependency + cost; can't customize evaluation strategy |
| **3. Environment variables** | Restart required to change; no per-user targeting |
| **4. Composite + Strategy + Proxy + Factory** | ✅ Chosen |

## Design

4 patterns compose:

```
  Caller ──▶ FeatureProxy ──▶ FlagRegistry
                                  │
                                  ▼
                           FlagCondition (Composite tree)
                                  │
                                  ├── AllOf (AND) ── region=US
                                  │                └── percent > 50
                                  ├── AnyOf (OR)
                                  └── Not
                                  │
                                  ▼
                           EvaluationStrategy (Strategy)
                                  ├── SimpleFlag (on/off)
                                  ├── ExperimentRollout (cohort-based)
                                  └── StagedRollout (alpha/beta/stable)
```

### Pattern composition

| Pattern | File(s) | What it does |
|---------|---------|--------------|
| **Composite** | `FlagCondition` + `AllOf`/`AnyOf`/`Not` | Build arbitrary AND/OR/NOT trees. Recursive `evaluate()`. |
| **Strategy** | `EvaluationStrategy` + `SimpleFlag`/`ExperimentRollout`/`StagedRollout` | Pluggable rollout mechanism per flag |
| **Proxy** | `FeatureProxy` | Transparent wrapper — caller never sees flag check. `execute()` returns result regardless of state. |
| **Interpreter** | `FlagCondition.evaluate()` | Recursively walks condition tree |
| **Factory** | `FlagRegistry.productionFlags()` | Creates flag config from definitions |

### Key interfaces

- `FlagCondition` — sealed interface for condition tree nodes
- `EvaluationStrategy` — `isEnabled(UserContext)` per rollout mechanism
- `FeatureProxy` — wraps any feature behind flag check

## Reading Order

| Step | File | Why read this next |
|------|------|--------------------|
| 1 | `DESIGN_DOC.md` (this file) | Understand problem, design, trade-offs |
| 2 | `UserContext.java` | Value Object — context passed through all flag evaluation |
| 3 | `FlagCondition.java` | Composite + Interpreter — AND/OR/NOT condition trees |
| 4 | `EvaluationStrategy.java` | Strategy — pluggable rollout (Simple/Experiment/Staged) |
| 5 | `FlagRegistry.java` | Registry + Factory — central flag store |
| 6 | `FeatureProxy.java` | Proxy — transparent wrapper, caller never sees flag check |
| 7 | `FeatureFlagDemo.java` | See all 4 patterns compose |

## Migration Plan

| Phase | Time | What | Risk |
|-------|------|------|------|
| 1 | Week 1 | Add `FlagRegistry` + `SimpleFlag` strategy | Low — on/off toggle only |
| 2 | Week 2 | Add `FlagCondition` Composite tree | Low |
| 3 | Week 3 | Add `StagedRollout` (alpha/beta/stable) | Medium — rollout % math must be exact |
| 4 | Week 4 | Add `ExperimentRollout` (cohort hashing) | Medium — consistent hashing across sessions |
| 5 | Week 5 | Add `FeatureProxy` — wrap first feature | Low |
| 6 | Week 6 | Migrate remaining features | Low |

## Rollback / Safety

- Default (flag registry unavailable) = feature disabled (fail closed)
- Staged rollout: ramp % changes take effect in < 1s
- Proxy logs all flag evaluation results for audit
- ExperimentRollout uses sticky cohort — user sees same variant across sessions

## Metrics

| Metric | Current | Target | How |
|--------|---------|--------|-----|
| Rollout time | 30+ min (redeploy) | < 1s (toggle) | Flag toggle latency |
| Kill switch time | 30+ min | < 1s | Time to disable |
| Experiment coverage | 0 | > 50% of features | Experiment flags count |
| Condition duplication | N/A | 0 (centralized) | FlagRegistry usage |

## Risks

- **Proxy hides failure**: Flag check throws → feature silently disabled. **Mitigation**: Proxy catches exception, logs, fails closed.
- **Condition tree complexity**: Deeply nested AND/OR/NOT unreadable. **Mitigation**: max depth check in CI; flatten via named sub-conditions.
- **Stale flags**: Dead flags accumulate. **Mitigation**: flag expiry date + automated cleanup after N days at 100%.
