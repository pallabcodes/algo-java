package com.backend.designpatterns.realworld.featureflags;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FlagRegistry {
    private final Map<String, EvaluationStrategy> flags = new ConcurrentHashMap<>();

    public FlagRegistry register(String name, EvaluationStrategy strategy) {
        flags.put(name, strategy);
        return this;
    }

    public boolean isEnabled(String name, UserContext user) {
        EvaluationStrategy strategy = flags.get(name);
        if (strategy == null) {
            System.out.println("[FlagRegistry] " + name + " NOT FOUND → default false");
            return false;
        }
        return strategy.isEnabled(name, user);
    }

    public static FlagRegistry productionFlags() {
        FlagCondition usOnly = new FlagCondition.RegionCondition("US");
        FlagCondition rollout50pct = new FlagCondition.RolloutCondition(50);
        FlagCondition internalCohort = new FlagCondition.CohortCondition("internal");
        FlagCondition betaCohort = new FlagCondition.CohortCondition("beta");

        return new FlagRegistry()
            .register("new-search-engine",
                new EvaluationStrategy.StagedRollout(10, 20, 100))
            .register("dark-mode",
                new EvaluationStrategy.SimpleFlag(
                    new FlagCondition.AnyOf(usOnly, internalCohort)))
            .register("checkout-v2",
                new EvaluationStrategy.ExperimentRollout("exp_checkout_v2_0426",
                    rollout50pct,
                    new FlagCondition.CohortCondition("holdout")))
            .register("internal-tools",
                new EvaluationStrategy.SimpleFlag(
                    new FlagCondition.AllOf(internalCohort, new FlagCondition.RegionCondition("US"))));
    }
}
