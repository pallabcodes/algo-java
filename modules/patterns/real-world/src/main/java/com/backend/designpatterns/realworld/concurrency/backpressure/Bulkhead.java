package com.backend.designpatterns.realworld.concurrency.backpressure;

/**
 * [12/19] Bulkhead — Strategy pattern. Isolates downstream service failures by
 * allocating a separate resource pool per service. If one downstream fails,
 * it doesn't consume resources from other downstreams.
 *
 * At Google scale: each gRPC downstream gets its own bulkhead. Without this,
 * a slow downstream (e.g., image service taking 10s) can exhaust all threads
 * and block fast downstreams (e.g., auth taking 5ms).
 *
 * Alternative rejected: shared thread pool for all downstreams (one slow
 * caller starves others). Bulkhead partitions resources explicitly.
 *
 * Implementation: Simple semaphore-based. Real Google implementations use
 * separate thread pools, connection pools, and queue sizes per downstream.
 */
public class Bulkhead {
    private final String name;
    private final int maxConcurrent;
    private final java.util.concurrent.Semaphore semaphore;

    public Bulkhead(String name, int maxConcurrent) {
        this.name = name;
        this.maxConcurrent = maxConcurrent;
        this.semaphore = new java.util.concurrent.Semaphore(maxConcurrent);
    }

    public boolean tryAcquire() {
        return semaphore.tryAcquire();
    }

    public void release() {
        semaphore.release();
    }

    public int available() { return semaphore.availablePermits(); }
    public int maxConcurrent() { return maxConcurrent; }
    public String name() { return name; }
}
