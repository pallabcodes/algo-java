package com.backend.designpatterns.realworld.featureflags;

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
