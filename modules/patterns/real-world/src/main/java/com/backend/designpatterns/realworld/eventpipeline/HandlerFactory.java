package com.backend.designpatterns.realworld.eventpipeline;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HandlerFactory {
    private final Map<String, EventPipeline> pipelines = new ConcurrentHashMap<>();

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
            .add((event, chain) -> System.out.println("[Pipeline:Audit] critical event logged to BigQuery"));

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
