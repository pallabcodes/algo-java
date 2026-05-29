package com.backend.designpatterns.realworld.payments.sdk;

public interface VersionRouter {
    ApiVersion resolve(String versionHeader);

    record HeaderBased() implements VersionRouter {
        public ApiVersion resolve(String header) {
            if (header == null || header.isBlank()) return ApiVersion.V1;
            if (header.contains("v2")) return ApiVersion.V2;
            if (header.contains("v1")) return ApiVersion.V1;
            return ApiVersion.V1;
        }
    }

    record GradualMigration(double v2Percentage) implements VersionRouter {
        public ApiVersion resolve(String header) {
            if (header != null && header.contains("v2")) return ApiVersion.V2;
            if (header != null && header.contains("v1")) return ApiVersion.V1;
            return Math.random() * 100 < v2Percentage ? ApiVersion.V2 : ApiVersion.V1;
        }
    }
}
