package com.backend.designpatterns.realworld.orders;

import java.util.List;

public class OrderDemo {

    public static void main(String[] args) {
        System.out.println("""
            === L6 PATTERN COMPOSITION: Order Processing ===

            Patterns combined:  Visitor + Composite + Iterator

            An order is a tree (Composite). Operations on it (total, invoice, reserve)
            are Visitors. BFS/DFS traversal uses Iterator.

            Adding a new operation = new Visitor. No OrderNode classes change.
            """);

        var order = new OrderNode.Order("ORD-001", "user_42", List.of(
            new OrderNode.LineItem("SKU-001", "Wireless Mouse", 2, 2500, 5000),
            new OrderNode.LineItem("SKU-002", "USB-C Hub", 1, 3500, 3500),
            new OrderNode.LineItem("SKU-003", "Monitor Arm", 1, 8500, 8500)
        ), List.of(
            new OrderNode.Discount("WELCOME10", "percentage", 1700),
            new OrderNode.Discount("FREESHIP", "flat", 1200)
        ), new OrderNode.Shipping("Standard", 1200, "US"), 17000);

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO A: Iterator — traverse the order tree");
        System.out.println("=".repeat(70));

        OrderIterator.printAll(order);

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO B: Visitor — calculate total (Composite walk)");
        System.out.println("=".repeat(70));

        var calc = new OrderVisitor.TotalCalculator();
        order.accept(calc);

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO C: Visitor — generate invoice");
        System.out.println("=".repeat(70));

        var invoice = new OrderVisitor.InvoiceGenerator();
        order.accept(invoice);

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO D: Visitor — reserve inventory (new op, no changes to OrderNode)");
        System.out.println("=".repeat(70));

        var reserver = new OrderVisitor.InventoryReserver();
        order.accept(reserver);

        System.out.println("\n" + "=".repeat(70));
        System.out.println("L6 TAKEAWAY: 3 patterns, extensible order processing.");
        System.out.println("  Composite → tree structure (Order → Items × Discounts × Shipping)");
        System.out.println("  Visitor   → new operations without modifying node classes");
        System.out.println("  Iterator  → flexible traversal (change order without changing logic)");
        System.out.println("=".repeat(70));
    }
}
