package com.backend.designpatterns.realworld.payments;

import java.util.List;
import java.util.random.RandomGenerator;

/**
 * [5/10] Strategy pattern — selects which PaymentProvider to use at runtime.
 * Different strategies for different concerns: ByRegion (geo-routing),
 * ByUserPreference (customer saves payment method), RoundRobin (load balancing).
 *
 * Without this, routing logic is if/else in the controller. With Strategy,
 * routing algorithms are swappable at runtime without changing callers.
 * Composes with PaymentProviderFactory to resolve the chosen provider.
 */
public interface PaymentRouter {
    PaymentProvider route(PaymentRequest req, List<PaymentProvider> candidates);

    record ByRegion() implements PaymentRouter {
        public PaymentProvider route(PaymentRequest req, List<PaymentProvider> candidates) {
            return candidates.stream()
                .filter(p -> p.supportsRegion(req.region()))
                .findFirst()
                .orElse(candidates.getFirst());
        }
    }

    record ByLowestCost() implements PaymentRouter {
        public PaymentProvider route(PaymentRequest req, List<PaymentProvider> candidates) {
            System.out.println("[Router:ByLowestCost] selecting cheapest provider");
            return candidates.getFirst();
        }
    }

    record ByUserPreference(String preferredProvider) implements PaymentRouter {
        public PaymentProvider route(PaymentRequest req, List<PaymentProvider> candidates) {
            return candidates.stream()
                .filter(p -> p.name().equals(preferredProvider))
                .findFirst()
                .orElseGet(() -> candidates.getFirst());
        }
    }

    record RoundRobin(RandomGenerator rng) implements PaymentRouter {
        public PaymentProvider route(PaymentRequest req, List<PaymentProvider> candidates) {
            return candidates.get(rng.nextInt(candidates.size()));
        }
    }
}
