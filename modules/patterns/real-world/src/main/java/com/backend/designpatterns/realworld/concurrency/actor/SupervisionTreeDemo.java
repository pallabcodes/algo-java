package com.backend.designpatterns.realworld.concurrency.actor;

/**
 * Demonstrates a 3-level Borg-style supervision tree:
 *   cluster (3 restarts) → machine (2 restarts) → borglet (1 restart) → task actors
 *
 * When a task actor fails repeatedly, failures escalate up the tree.
 * Each level tries restart before escalating. This is Chain of Responsibility
 * applied to failure handling.
 *
 * At Google scale: if a task fails, borglet restarts it.
 * If borglet fails too often, machine reschedules it on another machine.
 * If the whole machine is failing, cluster evacuates it.
 */
public class SupervisionTreeDemo {

    public static void main(String[] args) throws Exception {
        System.out.println("""
            === L6 HIERARCHICAL SUPERVISION TREE (Borg-style) ===

            Tree:
              cluster (restarts=3, window=10s)
               └── machine (restarts=2, window=5s)
                    └── borglet (restarts=1, window=2s)
                         └── task actors

            Patterns composed:
              State    → ActorState (Idle→Running→Suspended→Stopped)
              Command  → ActorMessage with reply channel
              Chain    → failure escalation up the supervision tree
              Observer → each supervisor monitors its children
              Strategy → restart/stop/escalate decision per level
              Factory  → ActorSystem creates/manages actors

            Key insight: failure handling IS Chain of Responsibility.
            Each supervisor tries, then passes up. Same pattern as middleware.
            """);

        // Build the tree bottom-up (children reference parents)
        var clusterSupervisor = new Supervisor("cluster-sup", 3, 10_000);
        var machineSupervisor = new Supervisor("machine-sup", 2, 5_000, clusterSupervisor);
        var borgletSupervisor = new Supervisor("borglet-sup", 1, 2_000, machineSupervisor);

        var system = new ActorSystem("borg-tree", borgletSupervisor);

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO: Task actor fails repeatedly — escalates up the tree");
        System.out.println("=".repeat(70));

        var taskActor = system.actor("task-42", msg -> {
            String cmd = (String) msg.payload();
            System.out.println("  [task-42] received: " + cmd);
            if ("fail".equals(cmd)) throw new RuntimeException("OOM in task-42");
            msg.reply("ok: " + cmd);
        });

        // Fail the actor enough times to exhaust all supervisors
        for (int i = 0; i < 7; i++) {
            try {
                system.send("task-42", i < 6 ? "fail" : "ping").get();
            } catch (java.util.concurrent.ExecutionException e) {
                System.out.println("  [Demo] attempt " + (i + 1) + " failed: " + e.getCause().getMessage());
            }
            Thread.sleep(50);
        }

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO: New actor — tree resets after clean start");
        System.out.println("=".repeat(70));

        var freshSystem = new ActorSystem("borg-tree-2",
            new Supervisor("cluster-sup", 3, 10_000,
            new Supervisor("machine-sup", 2, 5_000,
            new Supervisor("borglet-sup", 1, 2_000))));

        var healthyActor = freshSystem.actor("task-99", msg -> {
            msg.reply("healthy: " + msg.payload());
        });

        var healthyResult = freshSystem.send("task-99", "hello").get();
        System.out.println("  Fresh actor: ✅ " + healthyResult);

        freshSystem.shutdown();
        system.shutdown();

        System.out.println("\n" + "=".repeat(70));
        System.out.println("L6 TAKEAWAY:");
        System.out.println("  Supervision tree = Chain of Responsibility for FAILURES.");
        System.out.println("  Each level has different policy: borglet retries locally,");
        System.out.println("  machine reschedules, cluster evacuates.");
        System.out.println("  Same pattern as middleware pipeline — but for reliability.");
        System.out.println("  No supervisor is a single point of failure (parent takes over).");
        System.out.println("=".repeat(70));
    }
}
