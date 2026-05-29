package com.backend.designpatterns.realworld.payments;

import java.util.Set;
import java.util.UUID;

public class StripeAdapter implements PaymentProvider {
    private final Set<String> regions = Set.of("US", "CA", "UK", "EU", "AU");

    public String name() { return "Stripe"; }

    public PaymentResult charge(PaymentRequest req) {
        System.out.println("[StripeAdapter] charging " + req.currency() + " " + req.amountCents()
            + " for order " + req.orderId());
        if ("card_declined".equals(req.paymentMethod())) {
            return new PaymentResult(null, PaymentResult.Status.DECLINED, "stripe_decline", "card declined by issuer");
        }
        return new PaymentResult(UUID.randomUUID().toString(), PaymentResult.Status.SUCCESS,
            "ch_" + req.idempotencyKey().substring(0, 8), "captured");
    }

    public PaymentResult refund(String txnId, long amountCents) {
        return new PaymentResult(txnId, PaymentResult.Status.SUCCESS, "rf_" + txnId.substring(0, 6), "refunded");
    }

    public boolean supportsRegion(String region) { return regions.contains(region); }
}
