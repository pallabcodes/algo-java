package com.backend.designpatterns.realworld.eventpipeline;

import java.util.function.Consumer;

public interface EventSource {
    String name();
    void subscribe(String topic, Consumer<Event> handler);
    void acknowledge(Event event);

    record PubSub(String projectId) implements EventSource {
        public String name() { return "PubSub:" + projectId; }
        public void subscribe(String topic, Consumer<Event> handler) {
            System.out.println("[PubSub] subscribed to " + topic + " in " + projectId);
        }
        public void acknowledge(Event event) {
            System.out.println("[PubSub] acked " + event.id());
        }
    }

    record Kafka(String cluster) implements EventSource {
        public String name() { return "Kafka:" + cluster; }
        public void subscribe(String topic, Consumer<Event> handler) {
            System.out.println("[Kafka] consumer group assigned to " + topic + " on " + cluster);
        }
        public void acknowledge(Event event) {
            System.out.println("[Kafka] committed offset for " + event.id());
        }
    }

    record CloudTasks(String queue) implements EventSource {
        public String name() { return "CloudTasks:" + queue; }
        public void subscribe(String topic, Consumer<Event> handler) {
            System.out.println("[CloudTasks] leasing tasks from " + queue);
        }
        public void acknowledge(Event event) {
            System.out.println("[CloudTasks] deleted task " + event.id());
        }
    }
}
