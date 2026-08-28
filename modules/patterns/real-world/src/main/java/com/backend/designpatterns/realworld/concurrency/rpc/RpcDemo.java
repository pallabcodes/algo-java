package com.backend.designpatterns.realworld.concurrency.rpc;

import java.time.Duration;
import java.util.concurrent.*;

/**
 * [19/19] Demonstrates ValueObject + Strategy + Chain for RPC pipeline.
 * Shows: Deadline propagation, RetryBudget with token refill,
 * LoadShedder priority-based admission (CRITICAL/INTERACTIVE/BATCH).
 *
 * Without these: no timeout (wasted work), retry storms (3× load),
 * batch jobs crowd out search traffic under overload.
 */
public class RpcDemo {

    public static void main(String[] args) throws Exception {
        System.out.println("""
            === L6 gRPC-STYLE PIPELINE: Deadline + Retry Budget + Load Shedder ===

            Patterns composed:
              Deadline     → Value Object (absolute, propagatable)
              RetryBudget  → Strategy (sliding-window token budget)
              LoadShedder  → Chain + Strategy (priority-based admission)
              RpcPipeline  → Chain (steps in fixed order, short-circuit on failure)

            At Google scale, every RPC has ALL THREE:
              Client side:  deadline + retry budget
              Server side:  load shedder + deadline check
              Both sides:   pipeline wraps the gRPC stub
            """);

        var pipeline = new RpcPipeline(
            new LoadShedder(10, 5, 3),                // critical=10/s, interactive=5/s, batch=3/s
            Deadline.after(Duration.ofMillis(500)),   // deadline = 500ms from now
            new RetryBudget(5, 10),                   // 5 tokens/s refill, max 10
            3,                                        // max 3 attempts
            Executors.newVirtualThreadPerTaskExecutor()
        );

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO A: Success — all gates pass");
        System.out.println("=".repeat(70));

        var r1 = pipeline.call(LoadShedder.Priority.CRITICAL, () -> {
            System.out.println("  [RPC] critical call executing...");
            return "user-123";
        });
        System.out.println("  Result: " + (r1.success() ? "✅ " + r1.value() : "❌ " + r1.error()));

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO B: Load shedding — batch requests dropped under load");
        System.out.println("=".repeat(70));

        for (int i = 0; i < 6; i++) {
            final int id = i;
            var r = pipeline.call(LoadShedder.Priority.BATCH, () -> "batch-" + id);
            System.out.println("  Batch " + id + ": " + (r.success() ? "✅ " + r.value()
                : r.rejected() ? "⏸ " + r.error() : "❌ " + r.error()));
        }

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO C: Retry — transient failures retry within budget");
        System.out.println("=".repeat(70));

        var attempts = new java.util.concurrent.atomic.AtomicInteger(0);
        var r3 = pipeline.call(LoadShedder.Priority.INTERACTIVE, () -> {
            int attempt = attempts.incrementAndGet();
            System.out.println("  [RPC] interactive attempt " + attempt);
            if (attempt < 3) throw new RuntimeException("transient failure");
            return "retry-success";
        });
        System.out.println("  Result: " + (r3.success() ? "✅ " + r3.value() : "❌ " + r3.error()));

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO D: Deadline exceeded — expired deadline fast-fails");
        System.out.println("=".repeat(70));

        var latePipeline = new RpcPipeline(
            new LoadShedder(100, 100, 100),
            Deadline.after(Duration.ofMillis(100)),   // very short deadline
            new RetryBudget(10, 10),
            3,
            Executors.newVirtualThreadPerTaskExecutor()
        );

        var r4 = latePipeline.call(LoadShedder.Priority.CRITICAL, () -> {
            try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            return "too-late";
        });
        System.out.println("  Result: " + (r4.success() ? "✅ " + r4.value() : r4.rejected() ? "⏸ " + r4.error() : "❌ " + r4.error()));

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO E: Async — CompletableFuture + VirtualThread");
        System.out.println("=".repeat(70));

        var f1 = latePipeline.callAsync(LoadShedder.Priority.CRITICAL, () -> {
            try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            return "async-result";
        });

        try { System.out.println("  Async: ✅ " + f1.get()); }
        catch (ExecutionException e) { System.out.println("  Async: ❌ " + e.getCause().getMessage()); }

        System.out.println("\n" + "=".repeat(70));
        System.out.println("L6 TAKEAWAY: 3 patterns, 1 RPC pipeline.");
        System.out.println("  Deadline     → stops wasted work on timed-out calls");
        System.out.println("  RetryBudget  → caps retry volume, prevents storms");
        System.out.println("  LoadShedder  → drops low-priority when overloaded");
        System.out.println("  They compose: admit → check time → execute → retry");
        System.out.println("  Client+server: client sets deadline, server checks it.");
        System.out.println("  ScopedValue: deadline propagates through forks.");
        System.out.println("=".repeat(70));
    }
}
