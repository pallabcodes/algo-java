package com.backend.designpatterns.realworld.payments;

@FunctionalInterface
public interface PaymentObserver {
    void onEvent(PaymentEvent event);

    static PaymentObserver accountingLogger() {
        return event -> System.out.println("[Observer:Accounting] journal entry for " + event.type()
            + " | txn=" + event.transactionId());
    }

    static PaymentObserver inventoryRelease() {
        return event -> {
            if (event.type() == PaymentEvent.EventType.TRANSACTION_FAILED
                || event.type() == PaymentEvent.EventType.TRANSACTION_REFUNDED) {
                System.out.println("[Observer:Inventory] releasing hold on order " + event.orderId());
            }
        };
    }

    static PaymentObserver analyticsSink() {
        return event -> System.out.println("[Observer:Analytics] emitting metric: "
            + event.type() + " amount=" + event.amountCents());
    }

    static PaymentObserver fraudTeamAlert() {
        return event -> {
            if (event.type() == PaymentEvent.EventType.FRAUD_FLAGGED) {
                System.out.println("[Observer:FraudTeam] PAGING fraud team for txn " + event.transactionId());
            }
        };
    }
}
