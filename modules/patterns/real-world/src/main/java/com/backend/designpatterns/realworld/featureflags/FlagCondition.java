package com.backend.designpatterns.realworld.featureflags;

/**
 * Composite pattern — flag conditions form AND/OR/NOT trees for evaluation.
 * A condition can be a leaf (RegionCondition, CohortCondition, RolloutCondition)
 * or a composite (AllOf=AND, AnyOf=OR, Not). Trees can nest arbitrarily:
 *   "(US OR internal) AND rollout > 50%"
 *
 * Without this, complex conditions are if/else chains. Adding a new condition
 * type requires editing the evaluator. With Composite, conditions are uniform
 * and composable at runtime. Interpreter pattern (via evaluate()) walks the tree.
 */
public sealed interface FlagCondition
    permits FlagCondition.RegionCondition, FlagCondition.CohortCondition,
            FlagCondition.RolloutCondition, FlagCondition.AllOf,
            FlagCondition.AnyOf, FlagCondition.Not {

    boolean evaluate(UserContext user);

    record RegionCondition(String region) implements FlagCondition {
        public boolean evaluate(UserContext user) { return user.region().equals(region); }
    }

    record CohortCondition(String cohort) implements FlagCondition {
        public boolean evaluate(UserContext user) { return user.cohort().equals(cohort); }
    }

    record RolloutCondition(int percentage) implements FlagCondition {
        public boolean evaluate(UserContext user) { return user.rolloutBasis() < percentage; }
    }

    record AllOf(FlagCondition... conditions) implements FlagCondition {
        public boolean evaluate(UserContext user) {
            for (var c : conditions) if (!c.evaluate(user)) return false;
            return true;
        }
    }

    record AnyOf(FlagCondition... conditions) implements FlagCondition {
        public boolean evaluate(UserContext user) {
            for (var c : conditions) if (c.evaluate(user)) return true;
            return false;
        }
    }

    record Not(FlagCondition inner) implements FlagCondition {
        public boolean evaluate(UserContext user) { return !inner.evaluate(user); }
    }
}
