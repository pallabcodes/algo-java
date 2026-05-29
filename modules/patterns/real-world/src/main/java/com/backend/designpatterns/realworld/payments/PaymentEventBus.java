package com.backend.designpatterns.realworld.payments;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Observer pattern — decouples payment processing from downstream consumers
 * (accounting, inventory, analytics, fraud alerts). Each PaymentObserver is
 * notified on every PaymentEvent without the charge pipeline knowing about them.
 *
 * Without this, every new consumer requires modifying the charge() method.
 * Alternative rejected: direct calls to each downstream service (tight coupling,
 * blocks the payment thread). With EventBus, adding a subscriber = one line.
 * Composes with PaymentObserver static factories for subscriber definitions.
 */
public class PaymentEventBus {
    private final List<PaymentObserver> observers = new CopyOnWriteArrayList<>();

    public PaymentEventBus subscribe(PaymentObserver observer) {
        observers.add(observer);
        return this;
    }

    public void publish(PaymentEvent event) {
        System.out.println("[EventBus] publishing " + event.type() + " to " + observers.size() + " subscribers");
        observers.forEach(o -> o.onEvent(event));
    }

    public static PaymentEventBus defaultBus() {
        return new PaymentEventBus()
            .subscribe(PaymentObserver.accountingLogger())
            .subscribe(PaymentObserver.inventoryRelease())
            .subscribe(PaymentObserver.analyticsSink())
            .subscribe(PaymentObserver.fraudTeamAlert());
    }
}
