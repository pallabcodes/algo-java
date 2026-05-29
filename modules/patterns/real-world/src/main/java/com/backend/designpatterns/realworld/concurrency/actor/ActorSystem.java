package com.backend.designpatterns.realworld.concurrency.actor;

import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * Factory + Registry — creates, caches, and manages actors.
 * Central point for actor lifecycle: start, stop, send, supervise.
 *
 * Without this: actors are created ad-hoc, no consistent lifecycle,
 * no centralized failure handling, hard to find actors by name.
 *
 * Pattern composition:
 *   Factory → creates Actor instances with configured mailboxes/executors
 *   Registry → named actor lookup
 *   Observer → connects actors to Supervisor
 */
public class ActorSystem {
    private final Map<String, Actor<?, ?>> registry = new ConcurrentHashMap<>();
    private final Executor defaultExecutor = Executors.newVirtualThreadPerTaskExecutor();
    private final Supervisor supervisor;

    public ActorSystem(String name, int maxRestarts, long windowMs) {
        this.supervisor = new Supervisor(name + "-supervisor", maxRestarts, windowMs);
    }

    public ActorSystem(String name, Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public Supervisor supervisor() { return supervisor; }

    @SuppressWarnings("unchecked")
    public <T, R> Actor<T, R> actor(String name, Consumer<ActorMessage<T, R>> handler) {
        return (Actor<T, R>) registry.computeIfAbsent(name, n -> {
            var actor = new Actor<T, R>(n, defaultExecutor, handler, supervisorCallback(n, handler));
            actor.start();
            return actor;
        });
    }

    @SuppressWarnings("unchecked")
    private <T, R> Consumer<Throwable> supervisorCallback(String name, Consumer<ActorMessage<T, R>> handler) {
        return cause -> {
            var actor = (Actor<T, R>) registry.get(name);
            var decision = supervisor.onFailure(name, cause, actor != null ? actor.pending() : 0);
            switch (decision) {
                case RESTART -> {
                    registry.remove(name);
                    var replacement = new Actor<T, R>(name, defaultExecutor, handler, supervisorCallback(name, handler));
                    registry.put(name, replacement);
                    replacement.start();
                    System.out.println("[ActorSystem] " + name + " restarted via supervisor '" + supervisor.name() + "'");
                }
                case STOP -> {
                    if (actor != null) actor.stop("supervisor: " + cause.getMessage());
                }
                case ESCALATE -> System.out.println("[ActorSystem] " + name + " escalation unhandled (root supervisor '" + supervisor.name() + "')");
            }
        };
    }

    @SuppressWarnings("unchecked")
    public <T, R> CompletableFuture<R> send(String actorName, T payload) {
        var actor = (Actor<T, R>) registry.get(actorName);
        if (actor == null) {
            var f = new CompletableFuture<R>();
            f.completeExceptionally(new IllegalArgumentException("unknown actor: " + actorName));
            return f;
        }
        return actor.send(payload);
    }

    public void shutdown() {
        registry.values().forEach(a -> a.stop("system shutdown"));
        supervisor.stop();
    }
}
