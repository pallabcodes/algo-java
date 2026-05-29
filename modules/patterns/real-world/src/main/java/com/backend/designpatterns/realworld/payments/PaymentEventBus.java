package com.backend.designpatterns.realworld.payments;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
