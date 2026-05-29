package com.backend.designpatterns.realworld.concurrency.actor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Mailbox = BlockingQueue + Executor — delivers messages to an actor one at
 * a time, preserving order. This is the core of the actor model: each actor
 * has a mailbox, messages are delivered sequentially, no concurrency within
 * an actor.
 *
 * Pattern: Chain of Responsibility variant — messages flow through mailbox
 * → actor's onMessage handler. No shared state between actors.
 *
 * At Google scale: Borg tasks, sharded stateful services.
 * Each shard has a mailbox. Messages to the same key route to the same mailbox.
 */
public class Mailbox<T> {
    private final LinkedBlockingQueue<T> queue = new LinkedBlockingQueue<>();
    private final Executor executor;
    private final String name;
    private volatile boolean running = true;

    public Mailbox(String name, Executor executor) {
        this.name = name;
        this.executor = executor;
    }

    public void send(T message) {
        if (!running) throw new RejectedExecutionException(name + " is stopped");
        queue.offer(message);
    }

    public void start(java.util.function.Consumer<T> handler) {
        executor.execute(() -> {
            while (running) {
                try {
                    T msg = queue.poll(1, TimeUnit.SECONDS);
                    if (msg != null) {
                        handler.accept(msg);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    System.err.println("[Mailbox:" + name + "] handler error: " + e.getMessage());
                }
            }
        });
    }

    public void stop() { running = false; }
    public int pending() { return queue.size(); }
    public String name() { return name; }
}
