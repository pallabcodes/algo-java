package com.backend.designpatterns.realworld.workflows;

public class WorkflowDemo {

    public static void main(String[] args) {
        System.out.println("""
            === L6 PATTERN COMPOSITION: Transactional Workflow ===

            Patterns combined:  Command + Memento + Observer

            Each step is a Command (execute + undo). Memento tracks history for rollback.
            Observer emits events at each step. This is how every transactional workflow
            works at scale (booking, order fulfillment, multi-step provisioning).
            """);

        var eventBus = new WorkflowObserver.EventBus()
            .subscribe(WorkflowObserver.logger())
            .subscribe(WorkflowObserver.metrics())
            .subscribe(WorkflowObserver.alertOnFailure());

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO A: Happy path — all steps succeed");
        System.out.println("=".repeat(70));

        var bookingFlow = new WorkflowOrchestrator("booking-001", eventBus)
            .then(new WorkflowCommand.BookHotel("BK-001", "Grand Plaza", "Jun 15-17"))
            .then(new WorkflowCommand.ReserveCar("RC-001", "Tesla Model 3", "Jun 15"))
            .then(new WorkflowCommand.ChargeCard("CH-001", 85000, "USD"))
            .then(new WorkflowCommand.SendConfirmation("user@email.com", "Trip confirmed!"));
        bookingFlow.execute();

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO B: Failure mid-workflow — full rollback via Memento");
        System.out.println("=".repeat(70));

        var failingFlow = new WorkflowOrchestrator("booking-002", eventBus)
            .then(new WorkflowCommand.BookHotel("BK-002", "Ritz Carlton", "Jul 10-14"))
            .then(new WorkflowCommand.ReserveCar("RC-002", "Mercedes S-Class", "Jul 10"))
            .then(new WorkflowCommand.ChargeCard("CH-002", 250_000, "USD"))
            .then(new WorkflowCommand.SendConfirmation("user@email.com", "Luxury trip!"));
        boolean result = failingFlow.execute();
        System.out.println("[Demo] Workflow result: " + (result ? "SUCCESS" : "ROLLED BACK"));

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO C: Atomic multi-step with undo-able side effects");
        System.out.println("=".repeat(70));

        var atomicFlow = new WorkflowOrchestrator("booking-003", eventBus)
            .then(new WorkflowCommand.BookHotel("BK-003", "Airbnb Downtown", "Aug 1-3"))
            .then(new WorkflowCommand.ChargeCard("CH-003", 30000, "USD"));
        atomicFlow.execute();

        System.out.println("\n" + "=".repeat(70));
        System.out.println("L6 TAKEAWAY: 3 patterns, atomic transactional workflows.");
        System.out.println("  Command  → each step is an action with undo capability");
        System.out.println("  Memento  → maintains execution history for full rollback");
        System.out.println("  Observer → logs/metrics/alerts at each step without coupling");
        System.out.println("=".repeat(70));
    }
}
