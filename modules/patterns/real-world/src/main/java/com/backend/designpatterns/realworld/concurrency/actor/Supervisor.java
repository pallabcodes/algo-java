package com.backend.designpatterns.realworld.concurrency.actor;

/**
 * Observer + Strategy pattern — monitors actor health and decides recovery.
 * When an actor fails, the supervisor decides:
 *   RESTART  → recreate the actor (transient failure)
 *   STOP     → permanent failure, don't restart
 *   ESCALATE → notify higher-level supervisor (recursive supervision tree)
 *
 * At Google scale: Borg has a supervision tree. If a task fails, the
 * supervisor (borglet) restarts it. If the borglet fails, the master
 * reschedules. This pattern composes recursively.
 */
/**
 * Observer + Strategy pattern — monitors actor health and decides recovery.
 * Supports parent chaining for hierarchical supervision trees (Borg-style):
 *   task → borglet → machine → cluster
 *
 * When a supervisor decides ESCALATE, the decision is forwarded to the parent.
 * If no parent exists, ESCALATE means "no recovery possible" (system failure).
 *
 * Pattern composition:
 *   Observer → monitors actor failures via callback
 *   Strategy → restart/stop/escalate decisions are pluggable
 *   Chain    → parent chaining forms a chain of responsibility for failures
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
