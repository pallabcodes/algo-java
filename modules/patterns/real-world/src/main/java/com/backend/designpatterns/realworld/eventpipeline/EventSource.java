package com.backend.designpatterns.realworld.eventpipeline;

import java.util.function.Consumer;

/**
 * [2/8] Adapter pattern — unifies different event sources (PubSub, Kafka, CloudTasks)
 * behind a single interface. Each source has a different ack model, subscription
 * API, and client library. Without this, the event pipeline would need to know
 * which source it's reading from, coupling processing logic to infrastructure.
 *
 * Alternative rejected: using each source's native API directly in the pipeline
 * (tight coupling, can't swap sources without pipeline changes).
 * Composes with EventPipeline (Chain) which processes events uniformly
 * regardless of source.
 */
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
