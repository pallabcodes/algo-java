package com.backend.designpatterns.realworld.payments;

public record Transaction(
    String id,
    String orderId,
    String userId,
    long amountCents,
    String currency,
    String provider,
    TransactionState state
) {
    public Transaction withState(TransactionState s) {
        return new Transaction(id, orderId, userId, amountCents, currency, provider, s);
    }
}
