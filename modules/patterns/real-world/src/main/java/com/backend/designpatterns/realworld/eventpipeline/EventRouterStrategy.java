package com.backend.designpatterns.realworld.eventpipeline;

import java.util.List;

public interface EventRouterStrategy {
    void route(Event event, List<String> subscribers);

    record FanOut() implements EventRouterStrategy {
        public void route(Event event, List<String> subscribers) {
            System.out.println("[Router:FanOut] broadcasting " + event.type() + " to all " + subscribers.size() + " subscribers");
            subscribers.forEach(s -> System.out.println("  -> " + s));
        }
    }

    record Sharded(int shardCount) implements EventRouterStrategy {
        public void route(Event event, List<String> subscribers) {
            int shard = Math.abs(event.partitionKey().hashCode()) % subscribers.size();
            System.out.println("[Router:Sharded] routing " + event.type() + " to shard " + shard
                + " (key=" + event.partitionKey() + ") -> " + subscribers.get(shard));
        }
    }

    record PriorityFirst(List<String> priorityOrder) implements EventRouterStrategy {
        public void route(Event event, List<String> subscribers) {
            for (String sub : priorityOrder) {
                if (subscribers.contains(sub)) {
                    System.out.println("[Router:Priority] " + event.type() + " -> " + sub + " (first match)");
                    return;
                }
            }
            System.out.println("[Router:Priority] no priority match, fanning out");
        }
    }
}
