package com.backend.designpatterns.realworld.eventpipeline;

import java.util.Iterator;

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
