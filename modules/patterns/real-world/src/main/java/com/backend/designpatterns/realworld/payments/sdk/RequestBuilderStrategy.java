package com.backend.designpatterns.realworld.payments.sdk;

import java.util.UUID;

public interface RequestBuilderStrategy {
    ProviderSdkRequest build(CanonicalPaymentRequest req, String idempotencyKey);

    record StripeBuilder() implements RequestBuilderStrategy {
        public ProviderSdkRequest build(CanonicalPaymentRequest req, String key) {
            String source = switch (req.paymentMethodType()) {
                case "credit_card" -> "tok_visa";
                case "debit_card"  -> "tok_debit";
                default            -> "tok_" + req.paymentMethodType().substring(0, 4);
            };
            return new StripeSdkRequest(req.amountCents(), req.currency().toLowerCase(),
                source, req.orderId(), key);
        }
    }

    record PayPalBuilder() implements RequestBuilderStrategy {
        public ProviderSdkRequest build(CanonicalPaymentRequest req, String key) {
            return new PayPalSdkRequest(
                String.valueOf(req.amountCents()),
                req.currency(),
                "payer_xyz",
                "sale",
                "https://return." + req.orderId()
            );
        }
    }

    record GPayBuilder() implements RequestBuilderStrategy {
        public ProviderSdkRequest build(CanonicalPaymentRequest req, String key) {
            return new GPaySdkRequest(req.amountCents(), req.currency(),
                "encrypted:" + UUID.randomUUID(), "merchant_backend");
        }
    }
}
