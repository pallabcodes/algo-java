package com.backend.designpatterns.realworld.concurrency.actor;

/**
 * Message envelope — every message sent to an actor has a type, payload,
 * and reply channel. The reply channel lets the actor respond without
 * knowing the caller's address (Observer pattern variant).
 */
public record ActorMessage<T, R>(
    String type,
    T payload,
    java.util.concurrent.CompletableFuture<R> reply
) {
    public static <T, R> ActorMessage<T, R> of(String type, T payload) {
        return new ActorMessage<>(type, payload, new java.util.concurrent.CompletableFuture<>());
    }

    public void reply(R result) { reply.complete(result); }
    public void fail(Throwable t) { reply.completeExceptionally(t); }
}
