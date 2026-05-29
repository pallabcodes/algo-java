package com.backend.designpatterns.realworld.payments;

/**
 * Adapter pattern — normalizes disparate payment provider SDKs (Stripe, PayPal, GPay)
 * into a single interface. Without this, every provider call would need if/else
 * branches in the controller. Adding a new provider = implementing this interface.
 *
 * Alternative rejected: having the controller call each SDK directly (creates
 * coupling, violates OCP). This composes with PaymentProviderFactory (Factory)
 * for creation and PaymentRouter (Strategy) for selection at runtime.
 */
public interface PaymentProvider {
    String name();
    PaymentResult charge(PaymentRequest request);
    PaymentResult refund(String transactionId, long amountCents);
    boolean supportsRegion(String region);
}
