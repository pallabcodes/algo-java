package com.backend.designpatterns.realworld.payments.sdk;

import java.util.Map;

/**
 * Adapter + Strategy pattern — normalizes role-specific request shapes
 * (customer, support, admin) into the canonical CanonicalPaymentRequest.
 * Each role sends different fields: customers send {amount,currency},
 * support agents send {amount,currency,override_limit}, admins send
 * {amount,currency,metadata:{...}}.
 *
 * Without this, the controller parses each role's payload differently using
 * if/else on the role header. Adding a new role means editing the controller.
 * Alternative rejected: one massive request DTO with optional fields (leads
 * to confusion about which fields are valid for which role).
 * Composes with RoleBasedView for bidirectional role-based shaping.
 */
public interface RoleBasedRequestAdapter {
    CanonicalPaymentRequest adapt(String orderId, String userId, Map<String, Object> rawRequest);

    record CustomerRequestAdapter() implements RoleBasedRequestAdapter {
        public CanonicalPaymentRequest adapt(String orderId, String userId, Map<String, Object> raw) {
            return new CanonicalPaymentRequest(
                orderId, userId,
                ((Number) raw.getOrDefault("amount", 0)).longValue(),
                (String) raw.getOrDefault("currency", "USD"),
                (String) raw.getOrDefault("method", "credit_card"),
                Map.of()
            );
        }
    }

    record SupportRequestAdapter() implements RoleBasedRequestAdapter {
        public CanonicalPaymentRequest adapt(String orderId, String userId, Map<String, Object> raw) {
            long amount = ((Number) raw.getOrDefault("amount", 0)).longValue();
            String currency = (String) raw.getOrDefault("currency", "USD");
            return new CanonicalPaymentRequest(
                orderId, userId, amount, currency,
                (String) raw.getOrDefault("method", "credit_card"),
                Map.of("override_limit", raw.getOrDefault("override_limit", "false").toString(),
                       "force_capture", raw.getOrDefault("force_capture", "false").toString())
            );
        }
    }

    record AdminRequestAdapter() implements RoleBasedRequestAdapter {
        public CanonicalPaymentRequest adapt(String orderId, String userId, Map<String, Object> raw) {
            long amount = ((Number) raw.getOrDefault("amount", 0)).longValue();
            String currency = (String) raw.getOrDefault("currency", "USD");
            String method = (String) raw.getOrDefault("method", "credit_card");
            @SuppressWarnings("unchecked")
            var metadata = (Map<String, String>) (Map) raw.getOrDefault("metadata", Map.of());
            return new CanonicalPaymentRequest(orderId, userId, amount, currency, method, metadata);
        }
    }

    static RoleBasedRequestAdapter forRole(String role) {
        return switch (role) {
            case "admin" -> new AdminRequestAdapter();
            case "support" -> new SupportRequestAdapter();
            default -> new CustomerRequestAdapter();
        };
    }
}
