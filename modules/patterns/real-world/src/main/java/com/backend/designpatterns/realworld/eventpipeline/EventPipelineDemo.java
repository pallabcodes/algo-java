package com.backend.designpatterns.realworld.eventpipeline;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class EventPipelineDemo {

    public static void main(String[] args) {
        System.out.println("""
            === L6 PATTERN COMPOSITION: Event Processing Pipeline ===

            Patterns combined:  Adapter + Chain of Responsibility + Factory + Strategy + Observer

            At Google, every service emits events. The pipeline that processes them
            must be: source-agnostic, stage-isolated, handler-routed by type,
            routed by strategy, and monitored end-to-end.
            """);

        // 1. ADAPTERS: Different event sources behind a unified interface
        var sources = List.of(
            new EventSource.PubSub("gcp-prod-1"),
            new EventSource.Kafka("events-cluster"),
            new EventSource.CloudTasks("checkout-queue")
        );

        // 2. FACTORY: Creates type-specific pipelines
        HandlerFactory handlerFactory = HandlerFactory.defaultHandlers();

        // 3. STRATEGY: Pluggable routing strategies
        EventRouterStrategy fanout = new EventRouterStrategy.FanOut();
        EventRouterStrategy sharded = new EventRouterStrategy.Sharded(4);
        List<String> subscribers = List.of("inventory-svc", "billing-svc", "analytics-svc", "search-svc");

        // 4. OBSERVER: Monitoring hooks
        List<EventMonitor> monitors = List.of(
            EventMonitor.logAll(),
            EventMonitor.sliTracker(),
            EventMonitor.counter()
        );

        // === SCENARIO A: Critical event (OrderPlaced) through full pipeline ===
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO A: Critical Event — OrderPlaced (full pipeline + audit)");
        System.out.println("=".repeat(70));

        Event orderEvent = new Event(UUID.randomUUID().toString(), "OrderPlaced", "checkout-svc",
            "{\"orderId\":\"ORD-001\",\"total\":4999}".getBytes(), Instant.now(), "user_123");

        EventPipeline orderPipeline = handlerFactory.forType("OrderPlaced");
        orderPipeline.execute(orderEvent);
        fanout.route(orderEvent, subscribers);

        monitors.forEach(m -> m.observe(orderEvent, "complete", 120));

        // === SCENARIO B: Lightweight event (Heartbeat) — no transform/validate ===
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO B: Lightweight Event — Heartbeat (routed immediately)");
        System.out.println("=".repeat(70));

        Event heartbeat = new Event(UUID.randomUUID().toString(), "Heartbeat", "health-checker",
            new byte[]{1}, Instant.now(), "node-42");

        handlerFactory.forType("Heartbeat").execute(heartbeat);
        sharded.route(heartbeat, subscribers);

        // === SCENARIO C: Invalid/malformed event — dropped at validation ===
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO C: Malformed Event — empty payload, dropped at validation");
        System.out.println("=".repeat(70));

        Event badEvent = new Event(UUID.randomUUID().toString(), "UserSignedUp", "auth-svc",
            new byte[0], Instant.now(), "user_999");

        handlerFactory.forType("UserSignedUp").execute(badEvent);

        // === SCENARIO D: Dynamic strategy swap ===
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO D: Strategy swap — Priority routing for order events");
        System.out.println("=".repeat(70));

        var priorityRouter = new EventRouterStrategy.PriorityFirst(List.of("billing-svc", "inventory-svc"));
        priorityRouter.route(orderEvent, subscribers);

        System.out.println("\n" + "=".repeat(70));
        System.out.println("L6 TAKEAWAY: 5 patterns, 1 event pipeline.");
        System.out.println("  Adapter  → unifies PubSub/Kafka/CloudTasks behind one interface");
        System.out.println("  Chain    → stages are isolated, short-circuitable, reorderable");
        System.out.println("  Factory  → different event types get different pipelines");
        System.out.println("  Strategy → routing topology changes without code changes");
        System.out.println("  Observer → monitoring is cross-cutting, not baked in");
        System.out.println("=".repeat(70));
    }
}
