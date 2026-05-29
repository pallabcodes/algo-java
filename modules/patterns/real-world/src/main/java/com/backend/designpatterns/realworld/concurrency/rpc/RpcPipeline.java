package com.backend.designpatterns.realworld.concurrency.rpc;

import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * RPC Pipeline — composes Deadline + RetryBudget + LoadShedder into a single
 * call path. This is the per-call equivalent of the per-service ResiliencePipeline.
 *
 * Call flow:
 *   1. LoadShedder — is the server willing to accept this priority?
 *   2. Deadline — has the caller already timed out?
 *   3. Execute the call
 *   4. On failure, check RetryBudget and retry if budget allows
 *
 * At Google scale: every gRPC client stub wraps calls in this pipeline.
 * The server also has a LoadShedder on the receiving end.
 *
 * Pattern composition:
 *   Deadline     → Value Object, propagated via ScopedValue or gRPC metadata
 *   RetryBudget  → Strategy (retry policy as a pluggable concern)
 *   LoadShedder  → Chain + Strategy (priority-based filtering in a chain)
 *   RpcPipeline  → Chain (steps execute in fixed order, each can short-circuit)
 */
public class RpcPipeline {
    private final LoadShedder loadShedder;
    private final Deadline deadline;
    private final RetryBudget retryBudget;
    private final int maxAttempts;
    private final ExecutorService executor;

    public RpcPipeline(LoadShedder loadShedder, Deadline deadline, RetryBudget retryBudget,
                       int maxAttempts, ExecutorService executor) {
        this.loadShedder = loadShedder;
        this.deadline = deadline;
        this.retryBudget = retryBudget;
        this.maxAttempts = maxAttempts;
        this.executor = executor;
    }

    public <T> Result<T> call(LoadShedder.Priority priority, Supplier<T> action) {
        // 1. Load shedding
        if (!loadShedder.shouldAccept(priority)) {
            return Result.rejected("load_shedded — priority=" + priority);
        }
        // 2. Deadline check (before any work)
        if (deadline.isExpired()) {
            return Result.rejected("deadline_exceeded — remaining=0ms");
        }
        // 3. Execute with retries
        Exception lastError = null;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            if (deadline.isExpired()) {
                return Result.rejected("deadline_exceeded after " + (attempt - 1) + " attempts");
            }
            if (attempt > 1) {
                // Consume retry budget for retries
                if (!retryBudget.tryConsume()) {
                    return Result.rejected("retry_budget_exhausted — available="
                        + String.format("%.1f", retryBudget.available()));
                }
            }
            try {
                T result = action.get();
                return Result.success(result);
            } catch (RuntimeException e) {
                lastError = e;
                // Will retry if budget allows
            }
        }
        return Result.failure(lastError != null ? lastError : new RuntimeException("exhausted retries"));
    }

    /**
     * VirtualThread variant — the executor is a VT executor, and the call runs
     * inside a VirtualThread. The deadline can be propagated as a ScopedValue.
     */
    public <T> CompletableFuture<T> callAsync(LoadShedder.Priority priority, Supplier<T> action) {
        return CompletableFuture.supplyAsync(() -> {
            var result = call(priority, action);
            if (result.success()) return result.value();
            throw new RuntimeException(result.error());
        }, executor);
    }

    public record Result<T>(boolean success, boolean rejected, T value, String error) {
        static <T> Result<T> success(T v) { return new Result<>(true, false, v, null); }
        static <T> Result<T> failure(Exception e) { return new Result<>(false, false, null, e.getMessage()); }
        static <T> Result<T> rejected(String reason) { return new Result<>(false, true, null, reason); }
    }
}
