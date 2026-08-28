package com.backend.designpatterns.realworld.coordination;

/**
 * [1/4] Service record + service interfaces for Mediator pattern.
 * Nested interfaces (OrderService, PaymentService, InventoryService, ShippingService)
 * define the contract. Default implementations provided as inner records.
 * Without a uniform interface: Mediator would need to know concrete service types,
 * defeating the purpose of decoupling.
 */
public record ServiceComponent(String name, boolean available, long responseTimeMs) {

    public interface OrderService {
        boolean createOrder(String orderId, String userId, long amount);
    }

    public interface PaymentService {
        boolean processPayment(String orderId, long amount, String method);
    }

    public interface InventoryService {
        boolean reserve(String orderId, String sku, int qty);
    }

    public interface ShippingService {
        boolean schedule(String orderId, String address, String method);
    }

    record DefaultOrderService() implements OrderService {
        public boolean createOrder(String id, String uid, long amt) {
            System.out.println("[OrderSvc] creating order " + id + " for " + uid);
            return true;
        }
    }

    record DefaultPaymentService() implements PaymentService {
        public boolean processPayment(String id, long amt, String method) {
            System.out.println("[PaymentSvc] charging " + amt + " via " + method);
            if (amt > 200_000) { System.out.println("[PaymentSvc] DECLINED: limit"); return false; }
            return true;
        }
    }

    record DefaultInventoryService() implements InventoryService {
        public boolean reserve(String id, String sku, int qty) {
            System.out.println("[InventorySvc] reserving " + qty + "x " + sku);
            return true;
        }
    }

    record DefaultShippingService() implements ShippingService {
        public boolean schedule(String id, String addr, String method) {
            System.out.println("[ShippingSvc] scheduling " + method + " to " + addr);
            return true;
        }
    }
}
