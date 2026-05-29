package com.backend.designpatterns.realworld.concurrency.actor;

/**
 * State pattern — actor lifecycle as a sealed interface.
 * Each state determines which messages are accepted and how failures are handled.
 *
 * States:
 *   IDLE       → initial state, waiting for first message
 *   RUNNING    → processing messages normally
 *   SUSPENDED  → degraded (e.g., downstream is slow, buffering messages)
 *   STOPPED    → terminal, no more messages accepted
 *
 * Alternative rejected: boolean flags (isRunning, isSuspended, isStopped) —
 * leads to invalid combinations (can be both running and stopped).
 * Sealed interface guarantees exactly one valid state at any time.
 */
public sealed interface ActorState {

    record Idle() implements ActorState {}
    record Running(long messagesProcessed) implements ActorState {}
    record Suspended(String reason, long pendingAtSuspend) implements ActorState {}
    record Stopped(String reason) implements ActorState {}

    default boolean canAcceptMessages() {
        return switch (this) {
            case Idle _, Running _ -> true;
            case Suspended _ -> true;  // buffer, don't process
            case Stopped _ -> false;
        };
    }

    default boolean isHealthy() {
        return this instanceof Running;
    }

    static ActorState initial() { return new Idle(); }
}
