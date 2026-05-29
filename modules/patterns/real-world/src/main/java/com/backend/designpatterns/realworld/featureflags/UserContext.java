package com.backend.designpatterns.realworld.featureflags;

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
