package com.backend.designpatterns.realworld.payments.sdk;

import java.util.Map;

/**
 * Strategy pattern — maps CanonicalPaymentResponse to client-facing shapes
 * based on API version (v1 legacy vs v2 modern) AND role (customer/admin).
 *
 * v1 returns legacy field names (charge_result, transaction_id).
 * v2 returns modern names (status, transactionId) via RoleBasedView.
 * Admin always gets more fields than customer regardless of version.
 *
 * "Response Mapper" is the broader concept: it decides HOW to transform
 * a canonical response into a client-specific DTO. RoleBasedView is one
 * implementation (by role). This is another (by version × role).
 * Both are Strategy over the mapping.
 */
public interface ApiResponseMapper {
    Map<String, Object> toResponse(CanonicalPaymentResponse canonical, ApiVersion version, String role);

    record V1ResponseMapper() implements ApiResponseMapper {
        public Map<String, Object> toResponse(CanonicalPaymentResponse r, ApiVersion v, String role) {
            if ("admin".equals(role) || "support".equals(role)) {
                return Map.of(
                    "transaction_id", r.transactionId(),
                    "charge_result", r.status().name().toLowerCase(),
                    "provider_ref", r.providerRef(),
                    "error", r.errorMessage() != null ? r.errorMessage() : "none"
                );
            }
            return Map.of(
                "charge_result", r.status().name().toLowerCase(),
                "error", r.errorMessage() != null ? r.errorMessage() : "none"
            );
        }
    }

    record V2ResponseMapper() implements ApiResponseMapper {
        public Map<String, Object> toResponse(CanonicalPaymentResponse r, ApiVersion v, String role) {
            return RoleBasedView.forRole(role).apply(r);
        }
    }

    static ApiResponseMapper forVersion(ApiVersion version) {
        return switch (version.version()) {
            case "v1" -> new V1ResponseMapper();
            case "v2" -> new V2ResponseMapper();
            default -> new V2ResponseMapper();
        };
    }
}
