package com.backend.designpatterns.realworld.concurrency;

import java.util.concurrent.*;

/**
 * [4/19] Backpressure limiter (Semaphore-based) — controls how many concurrent calls
 * are made to downstream services. When limit is reached, callers are rejected
 * (RejectedExecutionException) instead of queuing indefinitely.
 *
 * Why not unbounded? At Google scale, a slow downstream causes cascading
 * failures: thread accumulation → memory pressure → GC stalls → OOM →
 * load-shedding at LB → retry storm from clients → total system collapse.
 *
 * Why VirtualThread doesn't fix this: VTs let you create threads faster than
 * downstreams can handle them. Without backpressure, you overwhelm the
 * downstream faster than with platform threads (more concurrency = more harm).
 *
 * Alternative rejected: unbounded Executor (no protection), or fixed thread
 * pool (defeats VT benefits). Semaphore + VT = best of both: controlled
 * concurrency with lightweight threads.
 */
public class BackpressureLimiter {
    private final Semaphore semaphore;
    private final int maxConcurrent;
    private final int timeoutMs;

    public BackpressureLimiter(int maxConcurrent, int timeoutMs) {
        this.semaphore = new Semaphore(maxConcurrent);
        this.maxConcurrent = maxConcurrent;
        this.timeoutMs = timeoutMs;
    }

    public <T> T tryAcquire(Callable<T> action) throws Exception {
        if (!semaphore.tryAcquire(timeoutMs, TimeUnit.MILLISECONDS)) {
            var ctx = RequestContext.current();
            System.out.println("[Backpressure] REJECTED " + ctx.traceId()
                + " — " + maxConcurrent + " concurrent calls in flight");
            throw new RejectedExecutionException("backpressure: " + maxConcurrent + " concurrent limit");
        }
        try {
            return action.call();
        } finally {
            semaphore.release();
        }
    }

    public int availablePermits() { return semaphore.availablePermits(); }
}
