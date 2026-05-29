package com.backend.designpatterns.realworld.payments.sdk;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ResponseNormalizerFactory {
    private final Map<String, ResponseNormalizer> normalizers = new ConcurrentHashMap<>();

    public ResponseNormalizerFactory register(String provider, ResponseNormalizer normalizer) {
        normalizers.put(provider, normalizer);
        return this;
    }

    public ResponseNormalizer forProvider(String provider) {
        return normalizers.getOrDefault(provider,
            (resp, req, name) -> CanonicalPaymentResponse.failure(null, "no normalizer for: " + provider, provider));
    }

    public static ResponseNormalizerFactory defaultNormalizers() {
        return new ResponseNormalizerFactory()
            .register("Stripe", new ResponseNormalizer.StripeNormalizer())
            .register("PayPal", new ResponseNormalizer.PayPalNormalizer())
            .register("GPay", new ResponseNormalizer.GPayNormalizer());
    }
}
