package com.backend.designpatterns.realworld.payments;

import java.util.Set;
import java.util.UUID;

public class PayPalAdapter implements PaymentProvider {
    private final Set<String> regions = Set.of("US", "UK", "EU", "IN", "SG");

    public String name() { return "PayPal"; }

    public PaymentResult charge(PaymentRequest req) {
        System.out.println("[PayPalAdapter] processing " + req.currency() + " " + req.amountCents()
            + " via PayPal SDK for " + req.orderId());
        return new PaymentResult(UUID.randomUUID().toString(), PaymentResult.Status.SUCCESS,
            "PAYID-" + req.idempotencyKey().substring(0, 10), "completed");
    }

    public PaymentResult refund(String txnId, long amountCents) {
        return new PaymentResult(txnId, PaymentResult.Status.SUCCESS, "RF-" + txnId, "refunded");
    }

    public boolean supportsRegion(String region) { return regions.contains(region); }
}
