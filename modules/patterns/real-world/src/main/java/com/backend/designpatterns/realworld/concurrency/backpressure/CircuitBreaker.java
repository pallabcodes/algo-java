package com.backend.designpatterns.realworld.concurrency.backpressure;

/**
 * Circuit Breaker — State pattern. Protects downstream services from cascading
 * failures. When failures exceed a threshold, the circuit OPENS and all
 * subsequent calls fail fast (no downstream call attempted).
 *
 * States:
 *   CLOSED    → normal operation, calls pass through
 *   OPEN      → failures exceeded threshold, calls fail fast
 *   HALF_OPEN → after timeout, one probe call to test recovery
 *
 * Transitions:
 *   CLOSED → OPEN  (when failureCount >= failureThreshold)
 *   OPEN → HALF_OPEN (after resetTimeoutMs)
 *   HALF_OPEN → CLOSED (probe succeeds) | HALF_OPEN → OPEN (probe fails)
 *
 * At Google scale: every gRPC call has a circuit breaker. Without it,
 * a 5-second downstream blip becomes a 5-minute outage (retry storm).
 */
public class CircuitBreaker {
    public enum State { CLOSED, OPEN, HALF_OPEN }

    private final int failureThreshold;
    private final long resetTimeoutMs;
    private final long halfOpenMaxCalls;
    private State state = State.CLOSED;
    private int failureCount = 0;
    private long openedAt = 0;
    private long halfOpenCalls = 0;

    public CircuitBreaker(int failureThreshold, long resetTimeoutMs, long halfOpenMaxCalls) {
        this.failureThreshold = failureThreshold;
        this.resetTimeoutMs = resetTimeoutMs;
        this.halfOpenMaxCalls = halfOpenMaxCalls;
    }

    public synchronized boolean isCallAllowed() {
        return switch (state) {
            case CLOSED -> true;
            case OPEN -> {
                if (System.currentTimeMillis() - openedAt >= resetTimeoutMs) {
                    state = State.HALF_OPEN;
                    halfOpenCalls = 0;
                    System.out.println("[CircuitBreaker] OPEN → HALF_OPEN");
                    yield true;
                }
                yield false;
            }
            case HALF_OPEN -> ++halfOpenCalls <= halfOpenMaxCalls;
        };
    }

    public synchronized void onSuccess() {
        if (state == State.HALF_OPEN) {
            state = State.CLOSED;
            failureCount = 0;
            halfOpenCalls = 0;
            System.out.println("[CircuitBreaker] HALF_OPEN → CLOSED (recovered)");
        }
        if (state == State.CLOSED) {
            failureCount = Math.max(0, failureCount - 1); // gradual recovery
        }
    }

    public synchronized void onFailure() {
        failureCount++;
        if (state == State.HALF_OPEN || failureCount >= failureThreshold) {
            state = State.OPEN;
            openedAt = System.currentTimeMillis();
            System.out.println("[CircuitBreaker] → OPEN (failures=" + failureCount + "/" + failureThreshold + ")");
        }
    }

    public State state() { return state; }
}
