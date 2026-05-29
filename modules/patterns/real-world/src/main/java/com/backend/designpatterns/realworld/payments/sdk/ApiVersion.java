package com.backend.designpatterns.realworld.payments.sdk;

public record ApiVersion(String version) {
    public static final ApiVersion V1 = new ApiVersion("v1");
    public static final ApiVersion V2 = new ApiVersion("v2");
}
