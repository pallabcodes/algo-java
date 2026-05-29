package com.backend.designpatterns.realworld.concurrency;

import java.util.concurrent.*;
import java.util.concurrent.StructuredTaskScope.*;

/**
 * Loom Trifecta + CompletableFuture + Backpressure — one orchestrator.
 *
 * This is the P0/P1 concurrency pattern composition for Google L6:
 *
 *   1. ScopedValue            → context propagates through STS forks
 *   2. StructuredTaskScope    → fan-out with ShutdownOnFailure fail-fast
 *   3. VirtualThread          → each subtask = 1 VT, no pool sizing
 *   4. CompletableFuture      → fire-and-forget post-processing via allOf
 *   5. BackpressureLimiter    → Semaphore caps concurrent downstream calls
 *
 * Phase 1 (Parallel): inventory + fraud via StructuredTaskScope forks.
 *   ScopedValue inherits automatically. ShutdownOnFailure: one fails, both cancel.
 *
 * Phase 2 (Sequential): payment → shipping. Sequential because payment may fail.
 *
 * Phase 3 (Fire-and-forget): notifications + audit via CompletableFuture.allOf.
 *   ScopedValue does NOT propagate through CF executor threads. Context is
 *   captured in closures instead. This is intentional and correct.
 *
 * Pattern composition insight: each concurrency construct has a DIFFERENT
 * ScopedValue propagation behavior. Know which applies when.
 */
public class FulfillmentOrchestrator {
    private final Executor vtExecutor = Executors.newVirtualThreadPerTaskExecutor();
    private final BackpressureLimiter limiter = new BackpressureLimiter(5, 200);

    public record FulfillmentResult(
        boolean success,
        DownstreamService.InventoryResult inventory,
        DownstreamService.FraudResult fraud,
        DownstreamService.PaymentResult payment,
        DownstreamService.ShippingResult shipping,
        long totalLatencyMs
    ) {}

    public FulfillmentResult fulfill(String orderId, String userId, String sku, int qty,
                                     long amount, String paymentMethod, String region) throws Exception {

        var ctx = new RequestContext(orderId, userId, "trace-" + orderId.substring(0, 6), region);
        long start = System.currentTimeMillis();

        return RequestContext.runWith(ctx, () -> {
            // ═══ PHASE 1: Parallel checks via StructuredTaskScope ═══
            // ScopedValue propagates to both forks automatically.
            // ShutdownOnFailure: if either fails, the other is cancelled.
            System.out.println("\n[Orchestrator] PHASE 1: parallel checks via StructuredTaskScope");

            DownstreamService.InventoryResult inventory;
            DownstreamService.FraudResult fraud;

            try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
                Subtask<DownstreamService.InventoryResult> invFork = scope.fork(
                    () -> limiter.tryAcquire(() -> DownstreamService.checkInventory(sku, qty)));
                Subtask<DownstreamService.FraudResult> fraudFork = scope.fork(
                    () -> limiter.tryAcquire(() -> DownstreamService.runFraudCheck(orderId, amount)));

                scope.join();
                scope.throwIfFailed();

                inventory = invFork.get();
                fraud = fraudFork.get();
            }

            if (!inventory.available()) {
                throw new IllegalStateException("inventory unavailable for " + sku);
            }
            if (!fraud.allowed()) {
                throw new SecurityException("fraud blocked order " + orderId);
            }

            // ═══ PHASE 2: Sequential steps ═══
            // Payment then shipping — sequential because payment may fail.
            System.out.println("[Orchestrator] PHASE 2: sequential steps");

            DownstreamService.PaymentResult payment = DownstreamService.charge(paymentMethod, amount);
            if ("declined".equals(payment.status())) {
                throw new IllegalStateException("payment declined for " + orderId);
            }

            DownstreamService.ShippingResult shipping = DownstreamService.estimateShipping(region, qty * 500L);

            // ═══ PHASE 3: Fire-and-forget via CompletableFuture ═══
            // CF does NOT propagate ScopedValue bindings, so we capture
            // context values in closures instead of calling RequestContext.current().
            // This is the correct pattern: STS → ScopedValue, CF → closures.
            System.out.println("[Orchestrator] PHASE 3: async post-processing via CompletableFuture");

            String traceId = ctx.traceId();
            CompletableFuture<Void> notifyFut = CompletableFuture.runAsync(
                () -> DownstreamService.sendNotification(orderId, userId, traceId), vtExecutor);
            CompletableFuture<Void> auditFut = CompletableFuture.runAsync(
                () -> DownstreamService.auditLog(orderId, amount, traceId), vtExecutor);

            CompletableFuture.allOf(notifyFut, auditFut).join();

            long latency = System.currentTimeMillis() - start;
            return new FulfillmentResult(true, inventory, fraud, payment, shipping, latency);
        });
    }
}
