package com.backend.designpatterns.realworld.eventpipeline;

/**
 * [7/8] Observer pattern — cross-cutting monitoring hooks for the event pipeline.
 * Each static factory creates a different observer (logAll, sliTracker, counter).
 * Without Observer: monitoring logic would be baked into each pipeline stage,
 * violating Single Responsibility and making stages untestable in isolation.
 */
@FunctionalInterface
public interface EventMonitor {
    void observe(Event event, String stage, long latencyMs);

    static EventMonitor logAll() {
        return (event, stage, latency) ->
            System.out.println("[Monitor:Log] " + stage + " | " + event.type() + " | " + latency + "ms");
    }

    static EventMonitor sliTracker() {
        return (event, stage, latency) -> {
            if (latency > 500) {
                System.out.println("[Monitor:SLI] LATENCY BUDGET EXCEEDED " + stage + " " + event.type());
            }
        };
    }

    static EventMonitor counter() {
        return (event, stage, latency) ->
            System.out.println("[Monitor:Counter] incrementing " + stage + "." + event.type());
    }
}
