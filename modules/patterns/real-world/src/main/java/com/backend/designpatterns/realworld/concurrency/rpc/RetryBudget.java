package com.backend.designpatterns.realworld.concurrency.rpc;

import java.time.Duration;
import java.time.Instant;

/**
 * [16/19] Retry Budget — limits retry volume over a sliding window. Prevents retry
 * storms. At Google scale, a simple "retry up to 3 times" policy amplifies
 * load: one blip × 1000 QPS = 3000 extra requests. Retry budgets cap this.
 *
 * Algorithm:
 *   - Each retry attempt consumes from a token budget
 *   - Budget refills at a configured rate
 *   - If budget is exhausted, retries are denied (fail fast)
 *   - Budget is shared across ALL retries to the same service
 *
 * This is Google's internal retry budget design (not the same as gRPC's
 * retryThrottling, but similar intent).
 *
 * Alternative rejected: fixed retry count (retry storm, no coordination).
 */
public class RetryBudget {
    private final double budgetPerSecond;
    private final double maxBudget;
    private double budget;
    private long lastRefillMs;

    public RetryBudget(double budgetPerSecond, double maxBudget) {
        this.budgetPerSecond = budgetPerSecond;
        this.maxBudget = maxBudget;
        this.budget = maxBudget;
        this.lastRefillMs = System.currentTimeMillis();
    }

    public synchronized boolean tryConsume() {
        refill();
        if (budget >= 1.0) {
            budget -= 1.0;
            return true;
        }
        return false;
    }

    public synchronized double available() {
        refill();
        return budget;
    }

    private void refill() {
        long now = System.currentTimeMillis();
        long elapsed = now - lastRefillMs;
        if (elapsed > 0) {
            budget = Math.min(maxBudget, budget + (budgetPerSecond * elapsed / 1000.0));
            lastRefillMs = now;
        }
    }
}
