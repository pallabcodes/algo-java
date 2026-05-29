package com.backend.designpatterns.realworld.concurrency.actor;

import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * Actor = State + Mailbox + Command. Processes messages from its mailbox
 * sequentially. The Supervisor (Observer) is notified of failures.
 *
 * Pattern composition:
 *   Mailbox  → ordered message delivery (Chain variant)
 *   State    → lifecycle (Idle → Running → Suspended → Stopped)
 *   Command  → each message is an action with a reply channel
 *   Observer → Supervisor monitors failures via callback
 *
 * At Google scale: each Borg task is an actor. Each shard in a stateful
 * service is an actor. Each gRPC stream handler can be an actor.
 *
 * Without actors: shared mutable state + locks + race conditions.
 * With actors: each actor owns its state, communicates via messages.
 * No locks needed within an actor.
 */
public class Actor<T, R> {
    private final String name;
    private final Mailbox<ActorMessage<T, R>> mailbox;
    private final Consumer<ActorMessage<T, R>> handler;
    private volatile ActorState state = ActorState.initial();
    private final Consumer<Throwable> failureCallback;

    public Actor(String name, Executor executor,
                 Consumer<ActorMessage<T, R>> handler,
                 Consumer<Throwable> failureCallback) {
        this.name = name;
        this.mailbox = new Mailbox<>(name, executor);
        this.handler = handler;
        this.failureCallback = failureCallback;
    }

    public void start() {
        state = new ActorState.Running(0);
        mailbox.start(msg -> {
            try {
                if (!state.canAcceptMessages()) {
                    msg.fail(new RejectedExecutionException(name + " is " + state));
                    return;
                }
                state = new ActorState.Running(
                    state instanceof ActorState.Running r ? r.messagesProcessed() + 1 : 1);
                handler.accept(msg);
            } catch (Exception e) {
                failureCallback.accept(e);
                suspend(e.getMessage());
                msg.fail(e);
            }
        });
    }

    public CompletableFuture<R> send(T payload) {
        var msg = ActorMessage.<T, R>of("default", payload);
        if (!state.canAcceptMessages()) {
            msg.fail(new RejectedExecutionException(name + " is stopped"));
            return msg.reply();
        }
        mailbox.send(msg);
        return msg.reply();
    }

    public void suspend(String reason) {
        state = new ActorState.Suspended(reason, mailbox.pending());
        System.out.println("[Actor:" + name + "] SUSPENDED (" + reason + ") pending=" + mailbox.pending());
    }

    public void stop(String reason) {
        state = new ActorState.Stopped(reason);
        mailbox.stop();
    }

    public ActorState state() { return state; }
    public String name() { return name; }
    public int pending() { return mailbox.pending(); }
}
