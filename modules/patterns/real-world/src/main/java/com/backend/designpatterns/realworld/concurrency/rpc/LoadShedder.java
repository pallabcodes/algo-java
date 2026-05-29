package com.backend.designpatterns.realworld.concurrency.rpc;

/**
 * Load Shedder — drops low-priority requests when the system is overloaded.
 * At Google scale, every server has a load shedding policy. When CPU/latency
 * exceeds a threshold, the server sheds requests by priority: batch jobs
 * first, then interactive, then critical.
 *
 * Priority levels (Google-standard triad):
 *   CRITICAL    → user-facing, hard latency SLO (e.g., search, auth)
 *   INTERACTIVE → user-facing, softer latency SLO (e.g., recommendations)
 *   BATCH       → background, no latency SLO (e.g., analytics, indexing)
 *
 * Algorithm:
 *   - Track request rate per priority
 *   - When total rate > capacity, shed BATCH first, then INTERACTIVE
 *   - CRITICAL is never shed (admission control handles critical overload)
 *
 * Alternative rejected: random load shedding (drops critical requests, bad UX).
 */
public class LoadShedder {
    public enum Priority { CRITICAL, INTERACTIVE, BATCH }

    private final double criticalCapacity;
    private final double interactiveCapacity;
    private final double batchCapacity;

    private long criticalCount = 0;
    private long interactiveCount = 0;
    private long batchCount = 0;
    private long lastResetMs;

    public LoadShedder(double criticalCapacity, double interactiveCapacity, double batchCapacity) {
        this.criticalCapacity = criticalCapacity;
        this.interactiveCapacity = interactiveCapacity;
        this.batchCapacity = batchCapacity;
        this.lastResetMs = System.currentTimeMillis();
    }

    public synchronized boolean shouldAccept(Priority priority) {
        resetIfNeeded();
        return switch (priority) {
            case CRITICAL    -> ++criticalCount <= criticalCapacity;
            case INTERACTIVE -> ++interactiveCount <= interactiveCapacity && criticalCount <= criticalCapacity;
            case BATCH       -> ++batchCount <= batchCapacity
                                && interactiveCount <= interactiveCapacity
                                && criticalCount <= criticalCapacity;
        };
    }

    private void resetIfNeeded() {
        long now = System.currentTimeMillis();
        if (now - lastResetMs > 1000) {
            criticalCount = 0;
            interactiveCount = 0;
            batchCount = 0;
            lastResetMs = now;
        }
    }
}
