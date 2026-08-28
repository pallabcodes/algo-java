package com.backend.designpatterns.realworld.concurrency.actor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * [6/19] Chain of Responsibility variant — ordered message delivery for actors.
 * LinkedBlockingQueue delivers messages one-at-a-time per actor.
 * Guarantees sequential processing within an actor — no locks needed.
 *
 * Without ordered mailbox: messages could be processed concurrently, requiring
 * locks on actor state and defeating the actor model's primary benefit.
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
