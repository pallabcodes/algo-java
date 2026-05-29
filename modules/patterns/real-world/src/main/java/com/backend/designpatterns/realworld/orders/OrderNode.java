package com.backend.designpatterns.realworld.orders;

import java.util.List;

/**
 * Composite pattern — an order is a tree of nodes (Order → LineItems + Discounts + Shipping).
 * Each node accepts a Visitor, enabling tree operations without modifying node classes.
 *
 * Alternative to Composite: flat DTOs with primitive fields (loses tree structure,
 * makes it hard to add nested items/discounts dynamically). With Composite,
 * the order structure can grow without changing traversal code.
 */
public sealed interface OrderNode {

    void accept(OrderVisitor visitor);
    String label();

    record Order(String id, String userId, List<OrderNode> items, List<OrderNode> discounts, OrderNode shipping, long subtotalCents) implements OrderNode {
        public void accept(OrderVisitor v) { v.visit(this); }
        public String label() { return "Order[" + id + "]"; }
    }

    record LineItem(String sku, String name, int qty, long unitPriceCents, long totalCents) implements OrderNode {
        public void accept(OrderVisitor v) { v.visit(this); }
        public String label() { return "LineItem[" + sku + "]"; }
    }

    record Discount(String name, String type, long amountCents) implements OrderNode {
        public void accept(OrderVisitor v) { v.visit(this); }
        public String label() { return "Discount[" + name + "]"; }
    }

    record Shipping(String method, long costCents, String region) implements OrderNode {
        public void accept(OrderVisitor v) { v.visit(this); }
        public String label() { return "Shipping[" + method + "]"; }
    }
}
