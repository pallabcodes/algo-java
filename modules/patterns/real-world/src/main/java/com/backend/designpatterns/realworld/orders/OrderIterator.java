package com.backend.designpatterns.realworld.orders;

import java.util.*;

/**
 * [2/4] Iterator pattern — BFS traversal of OrderNode Composite tree.
 * Each Visitor (TotalCalculator, InvoiceGenerator) can reuse this iterator
 * instead of implementing its own traversal logic.
 *
 * Without Iterator: each operation would reimplement tree walk,
 * duplicating traversal code across every Visitor.
 */
public class OrderIterator implements Iterator<OrderNode> {
    private final Queue<OrderNode> queue = new LinkedList<>();

    public OrderIterator(OrderNode root) {
        queue.add(root);
    }

    public boolean hasNext() { return !queue.isEmpty(); }

    public OrderNode next() {
        OrderNode current = queue.poll();
        if (current instanceof OrderNode.Order o) {
            o.items().forEach(queue::add);
            queue.add(o.shipping());
            o.discounts().forEach(queue::add);
        }
        return current;
    }

    public static void printAll(OrderNode root) {
        System.out.println("[Iterator] traversing order tree:");
        Iterator<OrderNode> it = new OrderIterator(root);
        while (it.hasNext()) {
            OrderNode node = it.next();
            System.out.println("  " + node.label());
        }
    }
}
