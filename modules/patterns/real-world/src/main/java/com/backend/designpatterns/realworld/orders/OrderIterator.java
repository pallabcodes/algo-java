package com.backend.designpatterns.realworld.orders;

import java.util.*;

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
