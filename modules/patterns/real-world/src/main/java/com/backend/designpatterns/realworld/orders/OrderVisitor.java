package com.backend.designpatterns.realworld.orders;

import java.util.concurrent.atomic.LongAdder;

/**
 * Visitor pattern — separates operations on the OrderNode tree from the tree
 * structure itself. Adding a new operation (total calculation, invoice generation,
 * inventory reservation) = new Visitor. No OrderNode classes change.
 *
 * Alternative without Visitor: adding a method to OrderNode for each operation
 * (violates OCP — every new operation requires editing every OrderNode subclass).
 * Alternative rejected: pattern matching in the caller (works for simple cases
 * but doesn't scale to complex nested traversals).
 */
public interface OrderVisitor {
    void visit(OrderNode.Order order);
    void visit(OrderNode.LineItem item);
    void visit(OrderNode.Discount discount);
    void visit(OrderNode.Shipping shipping);

    final class TotalCalculator implements OrderVisitor {
        private final LongAdder total = new LongAdder();

        public void visit(OrderNode.Order o) {
            System.out.println("[Visitor] calculating total for " + o.id());
            o.items().forEach(i -> i.accept(this));
            o.shipping().accept(this);
            for (var d : o.discounts()) d.accept(this);
            System.out.println("[Visitor] GRAND TOTAL: $" + (total.sum() / 100.0));
        }
        public void visit(OrderNode.LineItem i) { total.add(i.totalCents()); }
        public void visit(OrderNode.Discount d) { total.add(-d.amountCents()); if (total.sum() < 0) total.reset(); }
        public void visit(OrderNode.Shipping s) { total.add(s.costCents()); }
    }

    final class InvoiceGenerator implements OrderVisitor {
        private final StringBuilder sb = new StringBuilder();

        public void visit(OrderNode.Order o) {
            System.out.println("[Visitor] generating invoice for " + o.id());
            sb.append("INVOICE: ").append(o.id()).append("\n");
            o.items().forEach(i -> i.accept(this));
            o.shipping().accept(this);
            for (var d : o.discounts()) d.accept(this);
            sb.append("---\n");
            System.out.print(sb);
        }
        public void visit(OrderNode.LineItem i) {
            sb.append("  ").append(i.qty()).append("x ").append(i.name())
              .append(" @ $").append(i.unitPriceCents() / 100.0)
              .append(" = $").append(i.totalCents() / 100.0).append("\n");
        }
        public void visit(OrderNode.Discount d) {
            sb.append("  Discount: ").append(d.name()).append(" -$").append(d.amountCents() / 100.0).append("\n");
        }
        public void visit(OrderNode.Shipping s) {
            sb.append("  Shipping: ").append(s.method()).append(" $").append(s.costCents() / 100.0).append("\n");
        }
    }

    record InventoryReserver() implements OrderVisitor {
        public void visit(OrderNode.Order o) { o.items().forEach(i -> i.accept(this)); }
        public void visit(OrderNode.LineItem i) {
            System.out.println("[Visitor] reserving " + i.qty() + "x " + i.sku());
        }
        public void visit(OrderNode.Discount d) {}
        public void visit(OrderNode.Shipping s) {}
    }
}
