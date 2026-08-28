package com.backend.designpatterns.realworld.concurrency.actor;

/**
 * [8/19] Observer + Strategy + Chain — failure monitoring and recovery for actors.
 * Observes actor failures via callback. Decides: RESTART, STOP, or ESCALATE.
 * Forms a Chain: leaf supervisor tries, then parent, then root.
 *
 * Without supervision tree: actor failure silently kills the actor;
 * no recovery, no escalation, no different policies per failure level.
 */
public class Supervisor {

    public enum Decision { RESTART, STOP, ESCALATE }

    private final String name;
    private final int maxRestarts;
    private final long windowMs;
    private int failureCount = 0;
    private long firstFailureAt = 0;
    private final Supervisor parent;
    private volatile boolean running = true;

    public Supervisor(String name, int maxRestarts, long windowMs) {
        this(name, maxRestarts, windowMs, null);
    }

    public Supervisor(String name, int maxRestarts, long windowMs, Supervisor parent) {
        this.name = name;
        this.maxRestarts = maxRestarts;
        this.windowMs = windowMs;
        this.parent = parent;
    }

    public Decision onFailure(String actorName, Throwable cause, int pendingMessages) {
        var decision = decide(actorName, cause, pendingMessages);
        if (decision == Decision.ESCALATE && parent != null) {
            System.out.println("[Supervisor:" + name + "] ESCALATING to parent '" + parent.name() + "'");
            return parent.onFailure(actorName, cause, pendingMessages);
        }
        return decision;
    }

    private synchronized Decision decide(String actorName, Throwable cause, int pendingMessages) {
        long now = System.currentTimeMillis();
        if (now - firstFailureAt > windowMs) {
            failureCount = 0;
            firstFailureAt = now;
        }
        if (failureCount == 0) firstFailureAt = now;
        failureCount++;

        Decision decision;
        if (failureCount > maxRestarts) {
            decision = parent != null ? Decision.ESCALATE
                : pendingMessages > 100 ? Decision.ESCALATE : Decision.STOP;
        } else {
            decision = Decision.RESTART;
        }

        System.out.println("[Supervisor:" + name + "] " + actorName
            + " failed (" + failureCount + "/" + maxRestarts + " in window)"
            + " pending=" + pendingMessages + " → " + decision
            + " reason=" + cause.getMessage());
        return decision;
    }

    public String name() { return name; }
    public void stop() { running = false; }
}
