package com.backend.designpatterns.realworld.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * Simulated downstream services. Methods are synchronous now — they run on
 * the caller's virtual thread. StructuredTaskScope handles the parallelism.
 *
 * ScopedValue propagates through StructuredTaskScope forks automatically.
 * CompletableFuture.supplyAsync does NOT propagate ScopedValue, so we use
 * CF only for fire-and-forget post-processing (notifications, audit).
 */
public class DownstreamService {

    public record InventoryResult(String sku, boolean available, int qty) {}
    public record FraudResult(String orderId, double score, boolean allowed) {}
    public record PaymentResult(String txnId, String status, long amountCents) {}
    public record ShippingResult(String method, long costCents, long estimatedDays) {}

    public static InventoryResult checkInventory(String sku, int qty) {
        var ctx = RequestContext.current();
        sleep(50 + (int)(Math.random() * 100));
        boolean avail = Math.random() > 0.15;
        System.out.println("[Inventory] " + ctx.traceId() + " sku=" + sku + " qty=" + qty
            + " → " + (avail ? "IN_STOCK" : "OUT_OF_STOCK"));
        return new InventoryResult(sku, avail, qty);
    }

    public static FraudResult runFraudCheck(String orderId, long amount) {
        var ctx = RequestContext.current();
        sleep(80 + (int)(Math.random() * 120));
        double score = Math.random() * 100;
        boolean allowed = score < 70;
        System.out.println("[Fraud] " + ctx.traceId() + " order=" + orderId + " amount=" + amount
            + " score=" + String.format("%.1f", score) + " → " + (allowed ? "ALLOW" : "BLOCK"));
        return new FraudResult(orderId, score, allowed);
    }

    public static PaymentResult charge(String method, long amount) {
        var ctx = RequestContext.current();
        sleep(100 + (int)(Math.random() * 200));
        boolean declined = "bad_card".equals(method) || amount > 500_000;
        String status = declined ? "declined" : "captured";
        System.out.println("[Payment] " + ctx.traceId() + " " + method + " " + amount + " → " + status);
        return new PaymentResult(ctx.requestId() + "-txn", status, amount);
    }

    public static ShippingResult estimateShipping(String region, long weight) {
        var ctx = RequestContext.current();
        sleep(30 + (int)(Math.random() * 50));
        var result = switch (region) {
            case "US" -> new ShippingResult("standard", 1200, 3);
            case "EU" -> new ShippingResult("international", 3500, 7);
            case "IN" -> new ShippingResult("international", 2000, 5);
            default -> new ShippingResult("standard", 1500, 5);
        };
        System.out.println("[Shipping] " + ctx.traceId() + " region=" + region
            + " → " + result.method() + " $" + result.costCents());
        return result;
    }

    public static void sendNotification(String orderId, String userId, String traceId) {
        sleep(30);
        System.out.println("[Notification] " + traceId + " order=" + orderId + " confirmed → email to " + userId);
    }

    public static void auditLog(String orderId, long amount, String traceId) {
        sleep(20);
        System.out.println("[Audit] " + traceId + " order=" + orderId + " amount=" + amount + " logged");
    }

    private static void sleep(long ms) {
        try { TimeUnit.MILLISECONDS.sleep(ms); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
