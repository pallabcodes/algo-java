package com.backend.designpatterns.realworld.payments;

/**
 * [1/10] Payment request value object carrying all fields needed for a transaction.
 */
public record PaymentRequest(
    String orderId,
    String userId,
    String currency,
    long amountCents,
    String paymentMethod,
    String region,
    String idempotencyKey
) {}
