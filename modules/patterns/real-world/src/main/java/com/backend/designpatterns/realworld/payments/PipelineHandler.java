package com.backend.designpatterns.realworld.payments;

@FunctionalInterface
public interface PipelineHandler {
    PaymentResult handle(PaymentRequest request, PaymentProvider provider, Transaction tx);

    static PipelineHandler validate() {
        return (req, provider, tx) -> {
            System.out.println("[Pipeline:Validate] checking request integrity");
            if (req.amountCents() <= 0) {
                return new PaymentResult(tx.id(), PaymentResult.Status.FAILED, null, "invalid amount");
            }
            if (req.userId() == null || req.userId().isBlank()) {
                return new PaymentResult(tx.id(), PaymentResult.Status.FAILED, null, "missing userId");
            }
            return null;
        };
    }

    static PipelineHandler fraudCheck() {
        return (req, provider, tx) -> {
            System.out.println("[Pipeline:FraudCheck] scoring transaction risk");
            if (req.amountCents() > 10_000_00 && "US".equals(req.region())) {
                System.out.println("[Pipeline:FraudCheck] HIGH VALUE TXN, flagging for review");
            }
            return null;
        };
    }

    static PipelineHandler charge() {
        return (req, provider, tx) -> {
            System.out.println("[Pipeline:Charge] executing with " + provider.name());
            return provider.charge(req);
        };
    }

    static PipelineHandler postProcess() {
        return (req, provider, tx) -> {
            System.out.println("[Pipeline:PostProcess] recording audit log");
            return null;
        };
    }
}
