package com.backend.designpatterns.realworld.payments.sdk;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RequestBuilderFactory {
    private final Map<String, RequestBuilderStrategy> builders = new ConcurrentHashMap<>();

    public RequestBuilderFactory register(String provider, RequestBuilderStrategy builder) {
        builders.put(provider, builder);
        return this;
    }

    public RequestBuilderStrategy forProvider(String provider) {
        return builders.get(provider);
    }

    public static RequestBuilderFactory defaultBuilders() {
        return new RequestBuilderFactory()
            .register("Stripe", new RequestBuilderStrategy.StripeBuilder())
            .register("PayPal", new RequestBuilderStrategy.PayPalBuilder())
            .register("GPay", new RequestBuilderStrategy.GPayBuilder());
    }
}
