package com.backend.designpatterns.realworld.coordination;

public class MediatorDemo {

    public static void main(String[] args) {
        System.out.println("""
            === L6 PATTERN COMPOSITION: Service Coordination ===

            Patterns combined:  Mediator + Factory + Observer

            The Mediator encapsulates how a set of services interact.
            Factory creates the mediator with the right service instances.
            Observer emits events for observability.

            Services NEVER reference each other directly. The Mediator owns the choreography.
            """);

        var eventBus = OrderEventBus.compose(OrderEventBus.logger(), OrderEventBus.metricsSink());
        var mediator = OrderMediator.createDefault(eventBus);

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO A: Happy path — all services succeed");
        System.out.println("=".repeat(70));
        mediator.placeOrder("ORD-001", "user_42", 4999, "SKU-WM", 2, "123 Main St, NY");

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO B: Payment fails — mediator stops and reports");
        System.out.println("=".repeat(70));
        mediator.placeOrder("ORD-002", "user_broke", 250_000, "SKU-LAPTOP", 1, "456 Oak Ave, CA");

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO C: Factory creates alternative mediator for VIP");
        System.out.println("=".repeat(70));

        var vipMediator = new OrderMediator(
            new ServiceComponent.DefaultOrderService(),
            (id, amt, method) -> { System.out.println("[VIP PaymentSvc] no limit check"); return true; },
            new ServiceComponent.DefaultInventoryService(),
            new ServiceComponent.DefaultShippingService(),
            eventBus
        );
        vipMediator.placeOrder("ORD-003", "vip_user", 999_999, "SKU-SERVER", 10, "789 VIP Ln, CA");

        System.out.println("\n" + "=".repeat(70));
        System.out.println("L6 TAKEAWAY: 3 patterns, decoupled service choreography.");
        System.out.println("  Mediator → orchestrates services without direct coupling");
        System.out.println("  Factory  → creates mediators with different service wiring");
        System.out.println("  Observer → observability without services knowing");
        System.out.println("=".repeat(70));
    }
}
