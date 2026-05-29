package com.backend.designpatterns.realworld.payments;

import java.util.List;
import java.util.random.RandomGenerator;

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
