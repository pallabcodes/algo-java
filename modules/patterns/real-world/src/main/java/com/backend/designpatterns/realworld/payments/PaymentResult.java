package com.backend.designpatterns.realworld.payments;

/**
 * [1/10] Payment result value object capturing the outcome of a transaction.
 */
public record PaymentResult(
    String transactionId,
    Status status,
    String providerRef,
    String message
) {
    public enum Status { SUCCESS, DECLINED, FAILED, PENDING }
}
