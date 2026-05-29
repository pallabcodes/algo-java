package com.backend.designpatterns.realworld.payments.sdk;

sealed interface ProviderSdkRequest
    permits StripeSdkRequest, PayPalSdkRequest, GPaySdkRequest {}

record StripeSdkRequest(
    long amountCents,
    String currency,
    String sourceToken,
    String description,
    String idempotencyKey
) implements ProviderSdkRequest {}

record PayPalSdkRequest(
    String amount,
    String currencyCode,
    String payerId,
    String intent,
    String returnUrl
) implements ProviderSdkRequest {}

record GPaySdkRequest(
    long amountCents,
    String currencyCode,
    String encryptedToken,
    String merchantId
) implements ProviderSdkRequest {}
