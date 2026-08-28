package com.backend.designpatterns.realworld.concurrency.actor;

/**
 * [5/19] Command pattern — message envelope for actor communication.
 * Carries type, payload, and reply CompletableFuture for request-response.
 * Without Command pattern: actors would call each other's methods directly,
 * coupling sender to receiver's implementation (no location transparency).
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
