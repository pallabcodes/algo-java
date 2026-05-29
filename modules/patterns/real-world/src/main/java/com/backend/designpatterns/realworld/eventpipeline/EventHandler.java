package com.backend.designpatterns.realworld.eventpipeline;

import java.util.Iterator;

/**
 * Chain of Responsibility pattern — each EventHandler is a stage in the
 * event processing pipeline. Stages: deserialize → validate → transform → route.
 * Any stage can short-circuit (drop malformed events at validation).
 *
 * Without this, all processing logic is in one class. Adding a new stage means
 * editing that class. With Chain, each stage is independently testable,
 * reorderable, and skippable per event type (lightweight events skip transform).
 * Composes with EventPipeline which chains them together.
 */
@FunctionalInterface
public interface EventHandler {
    void handle(Event event, Iterator<EventHandler> chain);

    static EventHandler deserialize() {
        return (event, chain) -> {
            System.out.println("[Pipeline:Deserialize] parsing " + event.type() + " payload");
            if (chain.hasNext()) chain.next().handle(event, chain);
        };
    }

    static EventHandler validate() {
        return (event, chain) -> {
            if (event.payload() == null || event.payload().length == 0) {
                System.out.println("[Pipeline:Validate] DROPPING empty payload: " + event.id());
                return;
            }
            System.out.println("[Pipeline:Validate] schema check passed for " + event.type());
            if (chain.hasNext()) chain.next().handle(event, chain);
        };
    }

    static EventHandler transform() {
        return (event, chain) -> {
            System.out.println("[Pipeline:Transform] enriching " + event.type()
                + " with context (region, experiment id...)");
            if (chain.hasNext()) chain.next().handle(event, chain);
        };
    }

    static EventHandler route() {
        return (event, chain) -> {
            System.out.println("[Pipeline:Route] dispatching " + event.type()
                + " to downstream subscribers");
        };
    }
}
