package com.backend.designpatterns.realworld.payments;

public record PaymentRequest(
    String orderId,
    String userId,
    String currency,
    long amountCents,
    String paymentMethod,
    String region,
    String idempotencyKey
) {}
