package com.backend.designpatterns.realworld.coordination;

import com.backend.designpatterns.realworld.coordination.ServiceComponent.*;

/**
 * [2/4] Mediator pattern — encapsulates the choreography between OrderService,
 * PaymentService, InventoryService, and ShippingService. Services never
 * reference each other directly. The Mediator owns the coordination logic.
 *
 * Without Mediator, each service would need to know about the others, creating
 * a web of dependencies. Alternative rejected: services calling each other
 * directly (tight coupling, can't test services in isolation). With Mediator,
 * adding a new participant or changing the flow = editing one class.
 * Composes with OrderEventBus (Observer) for decoupled monitoring.
 */
public class OrderMediator {
    private final OrderService orderSvc;
    private final PaymentService paymentSvc;
    private final InventoryService inventorySvc;
    private final ShippingService shippingSvc;
    private final OrderEventBus eventBus;

    public OrderMediator(OrderService o, PaymentService p, InventoryService i, ShippingService s, OrderEventBus bus) {
        this.orderSvc = o; this.paymentSvc = p; this.inventorySvc = i; this.shippingSvc = s; this.eventBus = bus;
    }

    public boolean placeOrder(String orderId, String userId, long amount, String sku, int qty, String address) {
        System.out.println("\n[Mediator] coordinating order " + orderId);

        if (!orderSvc.createOrder(orderId, userId, amount)) {
            eventBus.publish(orderId, "OrderService", false, "create failed");
            return false;
        }
        eventBus.publish(orderId, "OrderService", true, "created");

        if (!inventorySvc.reserve(orderId, sku, qty)) {
            eventBus.publish(orderId, "InventoryService", false, "insufficient stock");
            return false;
        }
        eventBus.publish(orderId, "InventoryService", true, "reserved");

        if (!paymentSvc.processPayment(orderId, amount, "credit_card")) {
            eventBus.publish(orderId, "PaymentService", false, "payment declined");
            return false;
        }
        eventBus.publish(orderId, "PaymentService", true, "charged");

        if (!shippingSvc.schedule(orderId, address, "standard")) {
            eventBus.publish(orderId, "ShippingService", false, "shipping unavailable");
            return false;
        }
        eventBus.publish(orderId, "ShippingService", true, "scheduled");

        System.out.println("[Mediator] order " + orderId + " COMPLETE");
        return true;
    }

    public static OrderMediator createDefault(OrderEventBus bus) {
        return new OrderMediator(
            new DefaultOrderService(), new DefaultPaymentService(),
            new DefaultInventoryService(), new DefaultShippingService(), bus
        );
    }
}
