package com.backend.designpatterns.realworld.payments.sdk;

sealed interface ProviderSdkResponse
    permits StripeSdkResponse, PayPalSdkResponse, GPaySdkResponse {}

record StripeSdkResponse(
    String id,
    String status,
    String failureCode,
    String failureMessage,
    String balanceTransaction
) implements ProviderSdkResponse {}

record PayPalSdkResponse(
    String id,
    String status,
    String debugId,
    String reason
) implements ProviderSdkResponse {}

record GPaySdkResponse(
    String id,
    String status,
    String errorReason
) implements ProviderSdkResponse {}
