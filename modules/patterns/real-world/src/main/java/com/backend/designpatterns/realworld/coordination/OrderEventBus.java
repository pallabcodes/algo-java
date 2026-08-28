package com.backend.designpatterns.realworld.coordination;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * [3/4] Observer pattern — decoupled event channel for order lifecycle events.
 * Uses functional Composition (compose()) to merge multiple observers.
 * Without this: every observer would need to be explicitly called in OrderMediator,
 * adding one more line per subscriber (tight coupling, violates OCP).
 */
@FunctionalInterface
public interface OrderEventBus {
    void publish(String orderId, String service, boolean success, String detail);

    static OrderEventBus compose(OrderEventBus... buses) {
        return (id, svc, ok, detail) -> {
            for (var b : buses) b.publish(id, svc, ok, detail);
        };
    }

    static OrderEventBus logger() {
        return (id, svc, ok, d) ->
            System.out.println("[Mediator:Log] " + id + " | " + svc + " | " + (ok ? "OK" : "FAIL") + " | " + d);
    }

    static OrderEventBus metricsSink() {
        return (id, svc, ok, d) ->
            System.out.println("[Mediator:Metrics] order." + svc + " " + (ok ? "success" : "failure"));
    }
}
