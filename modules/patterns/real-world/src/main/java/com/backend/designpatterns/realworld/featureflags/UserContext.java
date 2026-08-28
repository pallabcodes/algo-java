package com.backend.designpatterns.realworld.featureflags;

/**
 * [1/6] Value Object — user context for feature flag evaluation.
 * Carries region, cohort, and rollout basis (userId/random/group).
 * Passed through the FlagCondition Composite tree and EvaluationStrategy.
 * Without a standardized context: every flag evaluation would need ad-hoc
 * parameter passing, leading to inconsistent flag behavior.
 */
public record UserContext(
    String userId,
    String region,
    String cohort,
    double rolloutBasis
) {
    public static UserContext of(String userId, String region) {
        return new UserContext(userId, region, "control", Math.abs(userId.hashCode() % 100));
    }

    public UserContext withCohort(String cohort) {
        return new UserContext(userId, region, cohort, rolloutBasis);
    }
}
