package com.backend.designpatterns.realworld.eventpipeline;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
