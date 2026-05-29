package com.backend.designpatterns.realworld.payments;

import java.util.Set;
import java.util.UUID;

public class GPayAdapter implements PaymentProvider {
    private final Set<String> regions = Set.of("US", "IN", "UK", "SG", "AU", "JP");

    public String name() { return "GPay"; }

    public PaymentResult charge(PaymentRequest req) {
        System.out.println("[GPayAdapter] processing Google Pay token for " + req.orderId());
        return new PaymentResult(UUID.randomUUID().toString(), PaymentResult.Status.SUCCESS,
            "gpay_" + req.idempotencyKey().substring(0, 8), "captured");
    }

    public PaymentResult refund(String txnId, long amountCents) {
        return new PaymentResult(txnId, PaymentResult.Status.SUCCESS, "gpay_rf_" + txnId, "refunded");
    }

    public boolean supportsRegion(String region) { return regions.contains(region); }
}
