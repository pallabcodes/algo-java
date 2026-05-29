package com.backend.designpatterns.realworld.payments;

public interface PaymentProvider {
    String name();
    PaymentResult charge(PaymentRequest request);
    PaymentResult refund(String transactionId, long amountCents);
    boolean supportsRegion(String region);
}
