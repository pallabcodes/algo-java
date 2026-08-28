package com.backend.designpatterns.realworld.payments;

/**
 * [8/10] Event record representing a payment lifecycle event.
 */
public record PaymentEvent(
    EventType type,
    String transactionId,
    String orderId,
    String userId,
    long amountCents,
    String provider,
    String message
) {
    public enum EventType {
        TRANSACTION_STARTED,
        TRANSACTION_CAPTURED,
        TRANSACTION_SETTLED,
        TRANSACTION_FAILED,
        TRANSACTION_REFUNDED,
        FRAUD_FLAGGED
    }
}
