package com.backend.designpatterns.realworld.concurrency.rpc;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.*;

/**
 * [15/19] Deadline — propagates timeout across RPC call boundaries. At Google scale,
 * every RPC carries a deadline. The server reads the deadline and stops working
 * when it expires. This prevents wasted work on already-timed-out requests.
 *
 * Key design: deadlines are absolute (Instant), not relative (Duration).
 * Absolute deadlines survive serialization across network hops.
 * Relative timeouts are converted to absolute at the client.
 *
 * Alternative rejected: per-hop timeouts (sum of timeouts > actual deadline,
 * wasted work on retries, chain coordination complexity).
 */
public record Deadline(Instant deadline) {

    public static Deadline after(Duration duration) {
        return new Deadline(Instant.now().plus(duration));
    }

    public boolean isExpired() {
        return Instant.now().isAfter(deadline);
    }

    public Duration remaining() {
        var now = Instant.now();
        if (now.isAfter(deadline)) return Duration.ZERO;
        return Duration.between(now, deadline);
    }

    public Deadline min(Deadline other) {
        return this.deadline.isBefore(other.deadline) ? this : other;
    }
}
