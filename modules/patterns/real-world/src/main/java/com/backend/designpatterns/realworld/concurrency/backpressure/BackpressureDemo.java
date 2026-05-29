package com.backend.designpatterns.realworld.concurrency.backpressure;

import java.util.concurrent.*;
import java.util.random.RandomGenerator;

public class BackpressureDemo {

    public static void main(String[] args) throws Exception {
        System.out.println("""
            === L6 DISTRIBUTED BACKPRESSURE: Rate Limiter + Circuit Breaker + Bulkhead ===

            Patterns composed:
              TokenBucket     → rate limiting (permits/sec + burst)
              CircuitBreaker  → State pattern (CLOSED→OPEN→HALF_OPEN)
              Bulkhead        → Strategy pattern (isolated capacity per downstream)

            At Google scale, every gRPC client has ALL THREE per downstream.
            They compose: rate limit first, check health, allocate capacity, then call.
            """);

        // Pipeline for a simulated downstream service
        var pipeline = new ResiliencePipeline(
            new TokenBucket(5, 10),            // 5 req/sec, burst up to 10
            new CircuitBreaker(3, 2000, 2),    // 3 failures → open 2s → 2 half-open probes
            new Bulkhead("inventory-svc", 3)   // max 3 concurrent calls
        );

        var rng = RandomGenerator.getDefault();
        var vt = Executors.newVirtualThreadPerTaskExecutor();

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO A: Normal traffic — rate limited to 5/sec");
        System.out.println("=".repeat(70));

        for (int i = 0; i < 8; i++) {
            final int reqId = i;
            var result = pipeline.execute(() -> {
                Thread.sleep(50);
                return "ok-" + reqId;
            });
            System.out.println("  Request " + i + ": " + (result.success() ? "✅ " + result.value()
                : result.rejected() ? "⏸ " + result.error() : "❌ " + result.error()));
            Thread.sleep(50);
        }

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO B: Downstream failures → Circuit Breaker opens");
        System.out.println("=".repeat(70));

        // Simulate a failing downstream
        for (int i = 0; i < 6; i++) {
            var result = pipeline.execute(() -> {
                Thread.sleep(30);
                throw new RuntimeException("downstream timeout");
            });
            System.out.println("  Request " + i + ": " + (result.success() ? "✅ " + result.value()
                : result.rejected() ? "⏸ " + result.error() : "❌ " + result.error()));
        }

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO C: Circuit Half-Open — probe recovers");
        System.out.println("=".repeat(70));

        Thread.sleep(2500); // wait for circuit to transition to HALF_OPEN

        var result = pipeline.execute(() -> {
            Thread.sleep(30);
            return "recovered";
        });
        System.out.println("  Probe: " + (result.success() ? "✅ " + result.value()
            : result.rejected() ? "⏸ " + result.error() : "❌ " + result.error()));

        // Circuit should be CLOSED now — another call confirms
        var result2 = pipeline.execute(() -> {
            Thread.sleep(30);
            return "confirmed";
        });
        System.out.println("  Confirm: " + (result2.success() ? "✅ " + result2.value()
            : result2.rejected() ? "⏸ " + result2.error() : "❌ " + result2.error()));

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO D: Bulkhead isolation — one downstream doesn't starve another");
        System.out.println("=".repeat(70));

        var slowPipeline = new ResiliencePipeline(
            new TokenBucket(100, 100),
            new CircuitBreaker(10, 5000, 2),
            new Bulkhead("slow-svc", 2)  // only 2 concurrent calls
        );

        var futures = new java.util.ArrayList<CompletableFuture<String>>();
        for (int i = 0; i < 5; i++) {
            final int id = i;
            futures.add(CompletableFuture.supplyAsync(() -> {
                var r = slowPipeline.execute(() -> {
                    Thread.sleep(200);
                    return "slow-" + id;
                });
                return "  Request " + id + ": " + (r.success() ? "✅ " + r.value()
                    : r.rejected() ? "⏸ " + r.error() : "❌ " + r.error());
            }, vt));
        }
        for (var f : futures) System.out.println(f.get());

        System.out.println("\n" + "=".repeat(70));
        System.out.println("L6 TAKEAWAY: 3 patterns, 1 resilience pipeline.");
        System.out.println("  TokenBucket   → rate: prevents traffic spikes");
        System.out.println("  CircuitBreaker → health: fail fast when downstream is down");
        System.out.println("  Bulkhead      → isolation: one slow caller doesn't block others");
        System.out.println("  They compose: rate → health → capacity → call → report");
        System.out.println("  Each solves a DIFFERENT concern. Removing any one creates risk.");
        System.out.println("=".repeat(70));
    }
}
