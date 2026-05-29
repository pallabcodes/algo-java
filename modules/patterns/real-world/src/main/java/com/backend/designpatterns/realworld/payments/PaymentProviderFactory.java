package com.backend.designpatterns.realworld.payments;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PaymentProviderFactory {
    private final Map<String, PaymentProvider> registry = new ConcurrentHashMap<>();

    public PaymentProviderFactory register(String key, PaymentProvider provider) {
        registry.put(key, provider);
        return this;
    }

    public PaymentProvider forRegion(String region) {
        return registry.values().stream()
            .filter(p -> p.supportsRegion(region))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("no provider for region: " + region));
    }

    public PaymentProvider named(String name) {
        PaymentProvider p = registry.get(name);
        if (p == null) throw new IllegalArgumentException("unknown provider: " + name);
        return p;
    }

    public static PaymentProviderFactory defaultProviders() {
        return new PaymentProviderFactory()
            .register("Stripe", new StripeAdapter())
            .register("PayPal", new PayPalAdapter())
            .register("GPay", new GPayAdapter());
    }
}
