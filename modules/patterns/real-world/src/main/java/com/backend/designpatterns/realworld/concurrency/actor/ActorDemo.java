package com.backend.designpatterns.realworld.concurrency.actor;

import java.util.concurrent.*;

/**
 * [9/19] Demonstrates State + Command + Chain + Observer + Factory for actor model.
 * Shows: actor lifecycle (Idle→Running→Stopped), message passing with reply,
 * supervisor failure handling (RESTART/STOP/ESCALATE).
 *
 * Without actors: shared mutable state requires locks, race conditions common.
 */
public class ActorDemo {

    public static void main(String[] args) throws Exception {
        System.out.println("""
            === L6 ACTOR MODEL: State + Command + Mailbox + Observer + Factory ===

            Patterns composed:
              State    → ActorState sealed interface (Idle→Running→Suspended→Stopped)
              Command  → ActorMessage with reply channel (request-response pattern)
              Mailbox  → ordered message delivery (LinkedBlockingQueue + VT executor)
              Observer → Supervisor monitors failures, decides restart/stop/escalate
              Factory  → ActorSystem creates, caches, and wires actors

            At Google scale:
              - Borg tasks = actors (borglet = supervisor)
              - Sharded stateful services = actor per shard
              - gRPC stream handlers = actor per stream
            """);

        var system = new ActorSystem("order-system", 3, 10_000);

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO A: Actor handles messages sequentially");
        System.out.println("=".repeat(70));

        var orderActor = system.actor("order-42", msg -> {
            String payload = (String) msg.payload();
            System.out.println("  [order-42] processing: " + payload);
            try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            msg.reply("done: " + payload);
        });

        var f1 = orderActor.send("item:sku-001,qty:2");
        var f2 = orderActor.send("item:sku-002,qty:1");
        var f3 = orderActor.send("checkout");

        System.out.println("Sent 3 messages — they process sequentially:");
        System.out.println("  f1=" + f1.get() + " | f2=" + f2.get() + " | f3=" + f3.get());

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO B: Actor failure → Supervisor decides restart");
        System.out.println("=".repeat(70));

        var fragileActor = system.actor("fragile", msg -> {
            String cmd = (String) msg.payload();
            System.out.println("  [fragile] received: " + cmd);
            if ("crash".equals(cmd)) throw new RuntimeException("simulated failure");
            msg.reply("ok: " + cmd);
        });

        try { fragileActor.send("crash").get(); }
        catch (ExecutionException e) { System.out.println("  [Demo] caught: " + e.getCause().getMessage()); }

        // Actor is recreated by supervisor — send another message
        var f4 = system.<String, String>send("fragile", "ping");
        System.out.println("  After restart: " + f4.get());

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO C: Multiple actors, isolated state, no shared locks");
        System.out.println("=".repeat(70));

        for (int i = 0; i < 3; i++) {
            final int id = i;
            system.actor("worker-" + i, msg -> {
                int count = (int) msg.payload();
                try { Thread.sleep(30 + (int)(Math.random() * 50)); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                msg.reply("worker-" + id + " processed " + count + " items");
            });
        }

        var futures = new java.util.ArrayList<CompletableFuture<?>>();
        for (int i = 0; i < 3; i++) {
            futures.add(system.send("worker-" + i, i * 10));
        }
        for (var f : futures) {
            System.out.println("  " + f.get());
        }

        system.shutdown();

        System.out.println("\n" + "=".repeat(70));
        System.out.println("L6 TAKEAWAY: 5 patterns compose the Actor Model.");
        System.out.println("  State    → lifecycle transitions, no invalid states");
        System.out.println("  Command  → message envelope with reply channel");
        System.out.println("  Mailbox  → ordered delivery (Chain variant)");
        System.out.println("  Observer → Supervisor monitors + decides recovery");
        System.out.println("  Factory  → ActorSystem creates/wires/manages lifecycle");
        System.out.println("  NO LOCKS: each actor processes sequentially.");
        System.out.println("  SCALE: add actors (not threads) to scale.");
        System.out.println("=".repeat(70));
    }
}
