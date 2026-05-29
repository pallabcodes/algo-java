package com.backend.designpatterns.realworld.payments.sdk;

import java.util.Map;

public record CanonicalPaymentRequest(
    String orderId,
    String userId,
    long amountCents,
    String currency,
    String paymentMethodType,
    Map<String, String> metadata
) {
    public static CanonicalPaymentRequest of(String orderId, String userId, long amountCents, String currency, String method) {
        return new CanonicalPaymentRequest(orderId, userId, amountCents, currency, method, Map.of());
    }
}
