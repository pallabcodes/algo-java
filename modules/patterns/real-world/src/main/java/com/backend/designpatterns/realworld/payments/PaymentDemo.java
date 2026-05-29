package com.backend.designpatterns.realworld.payments;

import java.util.List;
import java.util.UUID;

public class PaymentDemo {

    public static void main(String[] args) {
        System.out.println("""
            === L6 PATTERN COMPOSITION: Payment Gateway ===

            Patterns combined:  Adapter + Factory + Strategy + Chain of Responsibility + State + Observer

            At Google scale, a single checkout touches 6+ cross-cutting concerns.
            Each concern maps to a pattern. The patterns COMPOSE — they don't compete.
            """);

        // 1. BOOTSTRAP: Factory creates providers (Adapter wrappers)
        PaymentProviderFactory providerFactory = PaymentProviderFactory.defaultProviders();
        List<PaymentProvider> allProviders = List.of(
            providerFactory.named("Stripe"),
            providerFactory.named("PayPal"),
            providerFactory.named("GPay")
        );

        // 2. STRATEGY: Pick routing algorithm at runtime
        PaymentRouter router = new PaymentRouter.ByRegion();

        // 3. CHAIN OF RESPONSIBILITY: Processing pipeline
        ProcessingPipeline pipeline = ProcessingPipeline.defaultPipeline();

        // 4. OBSERVER: Event bus for downstream consumers
        PaymentEventBus eventBus = PaymentEventBus.defaultBus();

        // 5. STATE: Transaction lifecycle managed via sealed interface

        // === SCENARIO A: US Customer paying $49.99 via credit card ===
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO A: US Customer — $49.99 via Credit Card");
        System.out.println("=".repeat(70));

        PaymentRequest reqA = new PaymentRequest(
            "ORD-001", "user_us_1", "USD", 4999, "credit_card", "US", UUID.randomUUID().toString()
        );

        PaymentProvider providerA = router.route(reqA, allProviders);
        System.out.println("[Strategy] routed to: " + providerA.name());

        TransactionState stateA = new TransactionState.Initiated();
        Transaction txA = new Transaction(UUID.randomUUID().toString(), reqA.orderId(),
            reqA.userId(), reqA.amountCents(), reqA.currency(), providerA.name(), stateA);

        eventBus.publish(new PaymentEvent(PaymentEvent.EventType.TRANSACTION_STARTED,
            txA.id(), txA.orderId(), txA.userId(), txA.amountCents(), txA.provider(), "started"));

        PaymentResult resultA = pipeline.execute(reqA, providerA, txA);
        System.out.println("Result: " + resultA);

        boolean aSuccess = resultA.status() == PaymentResult.Status.SUCCESS;
        txA = txA.withState(txA.state().next(aSuccess));
        if (aSuccess) txA = txA.withState(txA.state().next(true));
        System.out.println("State: " + stateA + " -> " + txA.state());

        eventBus.publish(new PaymentEvent(PaymentEvent.EventType.TRANSACTION_CAPTURED,
            txA.id(), txA.orderId(), txA.userId(), txA.amountCents(), txA.provider(), "captured"));

        // === SCENARIO B: Fraud-flagged high-value transaction ===
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO B: High-Value US Txn ($15,000) — FRAUD FLAGGED");
        System.out.println("=".repeat(70));

        PaymentRequest reqB = new PaymentRequest(
            "ORD-002", "user_suspicious", "USD", 15_000_00, "credit_card", "US", UUID.randomUUID().toString()
        );

        PaymentProvider providerB = router.route(reqB, allProviders);
        System.out.println("[Strategy] routed to: " + providerB.name());

        Transaction txB = new Transaction(UUID.randomUUID().toString(), reqB.orderId(),
            reqB.userId(), reqB.amountCents(), reqB.currency(), providerB.name(), new TransactionState.Initiated());

        eventBus.publish(new PaymentEvent(PaymentEvent.EventType.TRANSACTION_STARTED,
            txB.id(), txB.orderId(), txB.userId(), txB.amountCents(), txB.provider(), "started"));

        PaymentResult resultB = pipeline.execute(reqB, providerB, txB);
        System.out.println("Result: " + resultB);

        eventBus.publish(new PaymentEvent(PaymentEvent.EventType.FRAUD_FLAGGED,
            txB.id(), txB.orderId(), txB.userId(), txB.amountCents(), txB.provider(), "high value review"));

        txB = txB.withState(txB.state().next(resultB.status() == PaymentResult.Status.SUCCESS));
        System.out.println("State: " + txB.state());

        // === SCENARIO C: Declined card ===
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO C: UK Customer — Card Declined by Issuer");
        System.out.println("=".repeat(70));

        PaymentRequest reqC = new PaymentRequest(
            "ORD-003", "user_uk_1", "GBP", 2999, "card_declined", "UK", UUID.randomUUID().toString()
        );

        PaymentProvider providerC = router.route(reqC, allProviders);
        System.out.println("[Strategy] routed to: " + providerC.name());

        Transaction txC = new Transaction(UUID.randomUUID().toString(), reqC.orderId(),
            reqC.userId(), reqC.amountCents(), reqC.currency(), providerC.name(), new TransactionState.Initiated());

        PaymentResult resultC = pipeline.execute(reqC, providerC, txC);
        System.out.println("Result: " + resultC);

        boolean cSuccess = resultC.status() == PaymentResult.Status.SUCCESS;
        txC = txC.withState(txC.state().next(cSuccess));
        System.out.println("State: " + txC.state());

        eventBus.publish(new PaymentEvent(PaymentEvent.EventType.TRANSACTION_FAILED,
            txC.id(), txC.orderId(), txC.userId(), txC.amountCents(), txC.provider(), resultC.message()));

        // === SCENARIO D: Dynamic Strategy Swap at Runtime ===
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO D: Strategy swap at runtime — User preference overrides region");
        System.out.println("=".repeat(70));

        PaymentRequest reqD = new PaymentRequest(
            "ORD-004", "user_paypal_fan", "JPY", 120000, "wallet", "JP", UUID.randomUUID().toString()
        );

        // Swap strategy at runtime — this is the power of Strategy pattern
        router = new PaymentRouter.ByUserPreference("PayPal");
        PaymentProvider providerD = router.route(reqD, allProviders);
        System.out.println("[Strategy:UserPreference] routed to: " + providerD.name());
        System.out.println("  (GPay also supports JP but user prefers PayPal)");

        // Swap pipeline too — express path skips fraud check
        ProcessingPipeline expressPipeline = new ProcessingPipeline()
            .add(PipelineHandler.validate())
            .add(PipelineHandler.charge())
            .add(PipelineHandler.postProcess());
        System.out.println("  (Express pipeline: fraud check skipped)");

        Transaction txD = new Transaction(UUID.randomUUID().toString(), reqD.orderId(),
            reqD.userId(), reqD.amountCents(), reqD.currency(), providerD.name(), new TransactionState.Initiated());

        PaymentResult resultD = expressPipeline.execute(reqD, providerD, txD);
        System.out.println("Result: " + resultD);

        boolean dSuccess = resultD.status() == PaymentResult.Status.SUCCESS;
        txD = txD.withState(txD.state().next(dSuccess));
        if (dSuccess) txD = txD.withState(txD.state().next(true));
        System.out.println("State: " + txD.state());

        System.out.println("\n" + "=".repeat(70));
        System.out.println("L6 TAKEAWAY: 6 patterns, 1 coherent system.");
        System.out.println("  Adapter    → normalizes disparate payment APIs");
        System.out.println("  Factory    → creates the right adapter per context");
        System.out.println("  Strategy   → selects routing algorithm at runtime");
        System.out.println("  Chain      → orchestrates cross-cutting pipeline stages");
        System.out.println("  State      → manages lifecycle without if/else hell");
        System.out.println("  Observer   → decouples downstream consumers");
        System.out.println("=".repeat(70));
    }
}
