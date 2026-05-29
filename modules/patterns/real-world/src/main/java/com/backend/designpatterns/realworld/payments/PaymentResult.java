package com.backend.designpatterns.realworld.payments;

public record PaymentResult(
    String transactionId,
    Status status,
    String providerRef,
    String message
) {
    public enum Status { SUCCESS, DECLINED, FAILED, PENDING }
}
