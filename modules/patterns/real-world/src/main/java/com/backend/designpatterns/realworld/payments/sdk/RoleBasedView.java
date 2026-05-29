package com.backend.designpatterns.realworld.payments.sdk;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Proxy pattern — shapes the CanonicalPaymentResponse per caller role.
 * Customers see {status, amount, currency}. Support agents also see
 * {transactionId, providerRef, errorMessage}. Admins see everything
 * including raw provider data.
 *
 * This is one implementation of the broader "Response Mapper" concept.
 * A Response Mapper = logic that transforms canonical → client-specific shape.
 * Version-based mappers (v1 vs v2) are another implementation.
 * Both are Strategy pattern over the mapping logic.
 */
public interface RoleBasedView {
    Map<String, Object> apply(CanonicalPaymentResponse response);

    record CustomerView() implements RoleBasedView {
        public Map<String, Object> apply(CanonicalPaymentResponse r) {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("status", r.status().name());
            m.put("amount", r.amountCents());
            m.put("currency", r.currency());
            m.put("provider", r.providerName());
            return m;
        }
    }

    record SupportAgentView() implements RoleBasedView {
        public Map<String, Object> apply(CanonicalPaymentResponse r) {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("transactionId", r.transactionId());
            m.put("status", r.status().name());
            m.put("amount", r.amountCents());
            m.put("currency", r.currency());
            m.put("provider", r.providerName());
            m.put("providerRef", r.providerRef());
            if (r.errorMessage() != null) m.put("errorMessage", r.errorMessage());
            return m;
        }
    }

    record AdminView() implements RoleBasedView {
        public Map<String, Object> apply(CanonicalPaymentResponse r) {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("transactionId", r.transactionId());
            m.put("status", r.status().name());
            m.put("amount", r.amountCents());
            m.put("currency", r.currency());
            m.put("provider", r.providerName());
            m.put("providerRef", r.providerRef());
            if (r.errorMessage() != null) m.put("errorMessage", r.errorMessage());
            m.put("providerRawData", r.providerRawData().isEmpty() ? "{}" : r.providerRawData().toString());
            return m;
        }
    }

    static RoleBasedView forRole(String role) {
        return switch (role) {
            case "admin" -> new AdminView();
            case "support" -> new SupportAgentView();
            default -> new CustomerView();
        };
    }
}
