package com.backend.designpatterns.realworld.eventpipeline;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * [4/8] Factory + Builder + Chain invoker — three roles in one class.
 *
 * Factory: {@code defaultPipeline()} is a static factory that creates a
 * pre-wired pipeline with all 4 default stages.
 *
 * Builder: fluent {@code add()} returns {@code this}, so callers chain stages:
 * {@code new EventPipeline().add(deserialize()).add(validate()).add(transform()).add(route())}.
 *
 * Chain invoker: {@code execute()} hands iterator to first handler.
 * Handlers advance chain themselves: {@code chain.next().handle(event, chain)}.
 * (The Chain pattern itself = EventHandler + EventPipeline together.)
 *
 * Default stages: deserialize → validate → transform → route.
 *
 * Without this: cross-cutting concerns duplicated across event sources or
 * mixed into route logic. Alternative rejected: monolithic if/else per concern.
 */
public class EventPipeline {
    private final List<EventHandler> handlers = new ArrayList<>();

    public EventPipeline add(EventHandler handler) {
        handlers.add(handler);
        return this;
    }

    public void execute(Event event) {
        System.out.println("\n[Pipeline] processing event: " + event.type() + " [" + event.id() + "]");
        if (!handlers.isEmpty()) {
            handlers.getFirst().handle(event, handlers.listIterator(1));
        }
    }

    public static EventPipeline defaultPipeline() {
        return new EventPipeline()
            .add(EventHandler.deserialize())
            .add(EventHandler.validate())
            .add(EventHandler.transform())
            .add(EventHandler.route());
    }
}
