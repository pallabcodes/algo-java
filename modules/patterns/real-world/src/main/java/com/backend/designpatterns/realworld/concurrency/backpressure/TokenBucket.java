package com.backend.designpatterns.realworld.concurrency.backpressure;

/**
 * [10/19] Token Bucket — rate limiter with burst capacity. At Google scale, every
 * service has a rate limit policy. This is the standard implementation.
 *
 * Algorithm: tokens are added at a fixed rate (permitsPerSecond). Each request
 * consumes one token. If no tokens are available, the request is denied.
 * The bucket can accumulate up to burstSize tokens for traffic spikes.
 *
 * Alternative rejected: fixed-window counters (thundering herd at window boundary).
 * Token bucket allows bursts within the average rate.
 */
public class TokenBucket {
    private final long burstSize;
    private final double permitsPerMs;
    private long tokens;
    private long lastRefillMs;

    public TokenBucket(double permitsPerSecond, long burstSize) {
        this.burstSize = burstSize;
        this.permitsPerMs = permitsPerSecond / 1000.0;
        this.tokens = burstSize;
        this.lastRefillMs = System.currentTimeMillis();
    }

    public synchronized boolean tryAcquire() {
        refill();
        if (tokens > 0) {
            tokens--;
            return true;
        }
        return false;
    }

    public synchronized double availableTokens() {
        refill();
        return tokens;
    }

    private void refill() {
        long now = System.currentTimeMillis();
        long elapsed = now - lastRefillMs;
        if (elapsed > 0) {
            tokens = Math.min(burstSize, tokens + (long)(elapsed * permitsPerMs));
            lastRefillMs = now;
        }
    }
}
