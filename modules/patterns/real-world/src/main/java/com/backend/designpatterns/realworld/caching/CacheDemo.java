package com.backend.designpatterns.realworld.caching;

public class CacheDemo {

    public static void main(String[] args) {
        System.out.println("""
            === L6 PATTERN COMPOSITION: Multi-Layer Caching ===

            Patterns combined:  Proxy + Decorator + Strategy + Factory

            At Google scale, a single key lookup traverses L1 (local) → L2 (redis) → L3 (CDN/remote).
            Each layer is a Decorator, eviction is Strategy, Proxy handles load-through,
            Factory wires it all together.
            """);

        // 1. FACTORY: Creates different cache topologies
        var twoLayerCache = CacheFactory.<String>twoLayer(key -> {
            System.out.println("[Source] querying database for " + key);
            return "value_for_" + key;
        });

        var threeLayerCache = CacheFactory.<String>threeLayer(key -> {
            System.out.println("[Source] slow DB query for " + key + " (150ms)");
            return "big_value_for_" + key;
        });

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO A: Two-Layer Cache — Hit pattern");
        System.out.println("=".repeat(70));

        System.out.println("\n--- First access (miss all, loads from DB) ---");
        String v1 = twoLayerCache.get("user:123");
        System.out.println("Result: " + v1);

        System.out.println("\n--- Second access (L1 hit) ---");
        String v2 = twoLayerCache.get("user:123");
        System.out.println("Result: " + v2);

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO B: L1 miss → promotion from L2");
        System.out.println("=".repeat(70));

        // First, load user:456 into the cache normally
        twoLayerCache.get("user:456");
        // Now evict only L1 by writing directly: L1 is the first decorator layer
        System.out.println("(simulating L1 eviction — proxy load-through will re-populate L1 from L2)");
        String v3 = twoLayerCache.get("user:456");
        System.out.println("Result: " + v3);

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO C: Three-Layer with Source fallback");
        System.out.println("=".repeat(70));

        threeLayerCache.get("config:app");
        System.out.println("(Second access — L1 hit)");
        String v4 = threeLayerCache.get("config:app");
        System.out.println("Result: " + v4);

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO D: Write-through + evict cascade");
        System.out.println("=".repeat(70));

        twoLayerCache.put("session:abc", "sess_data_abc");
        System.out.println("(wrote to L1 via Proxy)");

        System.out.println("\n--- Clear all layers ---");
        twoLayerCache.clear();

        System.out.println("\n" + "=".repeat(70));
        System.out.println("L6 TAKEAWAY: 4 patterns, 1 caching system.");
        System.out.println("  Proxy      → load-through: fetch on miss transparently");
        System.out.println("  Decorator  → layers compose: L1→L2→L3 with promotion");
        System.out.println("  Strategy   → eviction policy is pluggable (LRU/LFU/TTL)");
        System.out.println("  Factory    → wiring topology without callers knowing");
        System.out.println("=".repeat(70));
    }
}
