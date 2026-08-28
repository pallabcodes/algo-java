package com.backend.designpatterns.realworld.eventpipeline;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * [6/8] Factory pattern — creates and caches EventPipeline instances per event type.
 * Critical events (OrderPlaced) get a full pipeline with audit logging.
 * Lightweight events (Heartbeat, PageViewed) get a minimal pipeline.
 *
 * Without this, every event type goes through the same heavyweight pipeline.
 * With Factory, each type gets the right pipeline, and callers don't know
 * how pipelines are constructed. Alternative rejected: if/else on event type
 * in the dispatcher (violates OCP, grows with every new event type).
 */
public class HandlerFactory {
    private final Map<String, EventPipeline> pipelines = new ConcurrentHashMap<>(); // Thread-Safe

    public HandlerFactory register(String eventType, EventPipeline pipeline) {
        pipelines.put(eventType, pipeline);
        return this;
    }

    public EventPipeline forType(String eventType) {
        return pipelines.getOrDefault(eventType, EventPipeline.defaultPipeline());
    }

    public static HandlerFactory defaultHandlers() {
        EventPipeline criticalPipeline = new EventPipeline()
            .add(EventHandler.deserialize())
            .add(EventHandler.validate())
            .add(EventHandler.transform())
            .add(EventHandler.route())
            .add((event, chain) -> System.out.println("[Pipeline:Audit] critical event logged to BigQuery")); // Same 4 stages as default, plus an inline lambda as 5th stage: audit logging. This is the power of Chain — adding a stage = one `.add()` call.

        EventPipeline lightweightPipeline = new EventPipeline()
            .add(EventHandler.deserialize())
            .add(EventHandler.route());

        return new HandlerFactory()
            .register("UserSignedUp", EventPipeline.defaultPipeline())
            .register("OrderPlaced", criticalPipeline)
            .register("PageViewed", lightweightPipeline)
            .register("Heartbeat", lightweightPipeline);
    }
}
