package com.backend.designpatterns.realworld.concurrency;

import java.util.concurrent.*;
import java.util.ArrayList;

public class ConcurrencyDemo {

    public static void main(String[] args) throws Exception {
        System.out.println("""
            === L6 CONCURRENCY: Loom Trifecta + CompletableFuture + Backpressure ===

            Loom Trifecta:  VirtualThread + StructuredTaskScope + ScopedValue
            Async:          CompletableFuture composition (allOf for fire-and-forget)
            Safety:         BackpressureLimiter (Semaphore), ShutdownOnFailure

            REALITY: At Google scale, every request fans out to 3-8 downstream services.
            Without structured concurrency: orphan threads on failure, context leak,
            unmanaged parallelism that overwhelms downstreams.

            KEY DISTINCTION:
              StructuredTaskScope → ScopedValue propagates (correct for parallelism)
              CompletableFuture  → ScopedValue does NOT propagate (use closures)
              Both are Loom patterns. Know when each applies.
            """);

        var orchestrator = new FulfillmentOrchestrator();

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO A: Happy path — parallel checks → sequential steps → async post");
        System.out.println("=".repeat(70));

        var resultA = orchestrator.fulfill("ORD-001", "user_42", "SKU-LAPTOP", 1, 150_000, "visa", "US");
        System.out.println("\n[Result] " + (resultA.success() ? "✅ FULFILLED" : "❌ FAILED")
            + " | latency=" + resultA.totalLatencyMs() + "ms");

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO B: Fraud block — ShutdownOnFailure cancels inventory check");
        System.out.println("=".repeat(70));

        try {
            orchestrator.fulfill("ORD-002", "user_fraud", "SKU-PHONE", 2, 200_000, "visa", "US");
        } catch (SecurityException e) {
            System.out.println("\n[Result] ❌ CANCELLED: " + e.getMessage());
        }

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO C: Payment declined — phase 2 failure, no rollback needed");
        System.out.println("=".repeat(70));

        try {
            orchestrator.fulfill("ORD-003", "user_bad_card", "SKU-TABLET", 1, 300_000, "bad_card", "EU");
        } catch (Exception e) {
            System.out.println("\n[Result] ❌ FAILED: " + e.getMessage());
        }

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO D: Backpressure — 8 concurrent requests, limit of 5");
        System.out.println("=".repeat(70));

        var executor = Executors.newVirtualThreadPerTaskExecutor();
        var futures = new ArrayList<CompletableFuture<String>>();

        for (int i = 0; i < 8; i++) {
            final String orderId = "ORD-BP-" + i;
            futures.add(CompletableFuture.supplyAsync(() -> {
                try {
                    orchestrator.fulfill(orderId, "user_bp", "SKU-BP", 1, 50_000, "visa", "US");
                    return orderId + ": ✅";
                } catch (RejectedExecutionException e) {
                    return orderId + ": ⏸ BACKPRESSURE";
                } catch (Exception e) {
                    return orderId + ": ❌ " + e.getClass().getSimpleName();
                }
            }, executor));
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        System.out.println("\n[Backpressure Results]");
        futures.forEach(f -> System.out.println("  " + f.getNow(null)));

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("L6 TAKEAWAY: 4 concurrency patterns, 1 orchestrator.");
        System.out.println("  ScopedValue         → context propagates through STS forks");
        System.out.println("  StructuredTaskScope  → fan-out with fail-fast lifecycle");
        System.out.println("  VirtualThread        → 1 task = 1 VT, no pool math needed");
        System.out.println("  CompletableFuture    → fire-and-forget (allOf) for post-processing");
        System.out.println("  BackpressureLimiter  → Semaphore prevents downstream overload");
        System.out.println("  CRITICAL: STS inherits ScopedValue, CF does NOT.");
        System.out.println("=".repeat(70));
    }
}
