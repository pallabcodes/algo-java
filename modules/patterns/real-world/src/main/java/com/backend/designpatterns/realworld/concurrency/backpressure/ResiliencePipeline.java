package com.backend.designpatterns.realworld.concurrency.backpressure;

import java.util.concurrent.*;

/**
 * Combines TokenBucket + CircuitBreaker + Bulkhead into one call pipeline.
 * Each pattern solves a different concern (rate, health, isolation).
 * They compose — they don't compete.
 *
 * Call flow:
 *   1. TokenBucket — am I within rate limit?
 *   2. CircuitBreaker — is the downstream healthy?
 *   3. Bulkhead — do I have capacity for this downstream?
 *   4. Actual call
 *   5. Report success/failure to CircuitBreaker
 *
 * At Google scale: this pattern is applied per downstream service.
 * Each gRPC client has its own RateLimiter + CircuitBreaker + Bulkhead.
 */
public class ResiliencePipeline {
    private final TokenBucket rateLimiter;
    private final CircuitBreaker circuitBreaker;
    private final Bulkhead bulkhead;

    public ResiliencePipeline(TokenBucket rateLimiter, CircuitBreaker circuitBreaker, Bulkhead bulkhead) {
        this.rateLimiter = rateLimiter;
        this.circuitBreaker = circuitBreaker;
        this.bulkhead = bulkhead;
    }

    public <T> Result<T> execute(Callable<T> action) {
        // 1. Rate limit
        if (!rateLimiter.tryAcquire()) {
            return Result.rejected("rate_limited — tokens=" + String.format("%.1f", rateLimiter.availableTokens()));
        }
        // 2. Circuit breaker
        if (!circuitBreaker.isCallAllowed()) {
            return Result.rejected("circuit_open — state=" + circuitBreaker.state());
        }
        // 3. Bulkhead
        if (!bulkhead.tryAcquire()) {
            return Result.rejected("bulkhead_full — " + bulkhead.available() + "/" + bulkhead.maxConcurrent() + " available");
        }
        try {
            T result = action.call();
            circuitBreaker.onSuccess();
            return Result.success(result);
        } catch (Exception e) {
            circuitBreaker.onFailure();
            return Result.failure(e);
        } finally {
            bulkhead.release();
        }
    }

    public record Result<T>(boolean success, boolean rejected, T value, String error) {
        static <T> Result<T> success(T v) { return new Result<>(true, false, v, null); }
        static <T> Result<T> failure(Exception e) { return new Result<>(false, false, null, e.getMessage()); }
        static <T> Result<T> rejected(String reason) { return new Result<>(false, true, null, reason); }
    }
}
