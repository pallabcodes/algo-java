package com.backend.designpatterns.realworld.payments.sdk;

import java.util.UUID;

public interface ProviderSdkClient {
    String name();
    ProviderSdkResponse charge(ProviderSdkRequest req);

    record StripeClient() implements ProviderSdkClient {
        public String name() { return "Stripe"; }
        public ProviderSdkResponse charge(ProviderSdkRequest raw) {
            StripeSdkRequest req = (StripeSdkRequest) raw;
            System.out.println("[StripeSDK] charging source=" + req.sourceToken() + " " + req.currency() + " " + req.amountCents());
            if (req.sourceToken().contains("decl")) {
                return new StripeSdkResponse(null, "failed", "card_declined", "insufficient funds", null);
            }
            return new StripeSdkResponse("ch_" + UUID.randomUUID().toString().substring(0, 8), "succeeded", null, null, "txn_abc");
        }
    }

    record PayPalClient() implements ProviderSdkClient {
        public String name() { return "PayPal"; }
        public ProviderSdkResponse charge(ProviderSdkRequest raw) {
            PayPalSdkRequest req = (PayPalSdkRequest) raw;
            System.out.println("[PayPalSDK] executing " + req.intent() + " for payer=" + req.payerId() + " " + req.currencyCode() + " " + req.amount());
            return new PayPalSdkResponse("PAYID-" + UUID.randomUUID().toString().substring(0, 12), "completed", null, null);
        }
    }

    record GPayClient() implements ProviderSdkClient {
        public String name() { return "GPay"; }
        public ProviderSdkResponse charge(ProviderSdkRequest raw) {
            GPaySdkRequest req = (GPaySdkRequest) raw;
            System.out.println("[GPaySDK] decrypting token for merchant=" + req.merchantId() + " " + req.currencyCode() + " " + req.amountCents());
            return new GPaySdkResponse("gpay_" + UUID.randomUUID().toString().substring(0, 8), "captured", null);
        }
    }
}
