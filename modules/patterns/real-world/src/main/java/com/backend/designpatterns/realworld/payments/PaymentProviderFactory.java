package com.backend.designpatterns.realworld.payments;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * [4/10] Factory pattern — creates and caches PaymentProvider instances.
 * Decouples provider creation from business logic. Without this, every caller
 * would need to know which implementation to instantiate.
 *
 * Alternative rejected: static if/else creation in each controller (duplicates
 * logic across the codebase). Composes with PaymentRouter (Strategy) which
 * selects which provider this factory should serve.
 */
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
