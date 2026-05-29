package com.backend.designpatterns.realworld.payments.sdk;

import java.util.Map;

public record CanonicalPaymentResponse(
    String transactionId,
    Status status,
    String providerRef,
    String providerName,
    long amountCents,
    String currency,
    Map<String, String> providerRawData,
    String errorMessage
) {
    public enum Status { SUCCESS, DECLINED, FAILED, PENDING }

    public static CanonicalPaymentResponse success(String txnId, String ref, String provider, long amount, String currency) {
        return new CanonicalPaymentResponse(txnId, Status.SUCCESS, ref, provider, amount, currency, Map.of(), null);
    }

    public static CanonicalPaymentResponse failure(String txnId, String msg, String provider) {
        return new CanonicalPaymentResponse(txnId, Status.FAILED, null, provider, 0, "USD", Map.of(), msg);
    }
}
