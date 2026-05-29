package com.backend.designpatterns.realworld.payments.sdk;

import java.util.Map;

public interface ApiRequestAdapter {
    CanonicalPaymentRequest toCanonical(String orderId, String userId, Map<String, Object> raw, ApiVersion version);

    record V1Adapter() implements ApiRequestAdapter {
        public CanonicalPaymentRequest toCanonical(String orderId, String userId, Map<String, Object> raw, ApiVersion v) {
            long amount = ((Number) raw.getOrDefault("total", 0)).longValue();
            return new CanonicalPaymentRequest(orderId, userId, amount, "USD",
                raw.containsKey("cc_last4") ? "credit_card" : "unknown",
                Map.of("api_version", "v1", "legacy_fields", raw.keySet().toString()));
        }
    }

    record V2Adapter() implements ApiRequestAdapter {
        @SuppressWarnings("unchecked")
        public CanonicalPaymentRequest toCanonical(String orderId, String userId, Map<String, Object> raw, ApiVersion v) {
            long amount = ((Number) raw.getOrDefault("amount_cents", 0)).longValue();
            String currency = (String) raw.getOrDefault("currency", "USD");
            Map<String, String> metadata = (Map<String, String>) (Map) raw.getOrDefault("metadata", Map.of());
            return new CanonicalPaymentRequest(orderId, userId, amount, currency,
                (String) raw.getOrDefault("payment_method", "credit_card"), metadata);
        }
    }
}
