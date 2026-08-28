package com.backend.designpatterns.realworld.caching;

/**
 * [8/8] Demonstrates Proxy + Decorator + Strategy + Factory composition for caching.
 * Scenarios: two-layer hit pattern, L1 full → L2 promotion, three-layer fallback,
 * write-through and clear cascade.
 *
 * Without this composition: each cache layer would be manually managed,
 * eviction hardcoded, topologies duplicated across callers.
 */
public class CacheDemo {

    public static void main(String[] args) {
        System.out.println("""
            === L6 PATTERN COMPOSITION: Multi-Layer Caching ===

            Patterns combined:  Proxy + Decorator + Strategy + Factory

            At Google scale, a single key lookup traverses L1 (local) → L2 (redis) → L3 (CDN/remote).
            Each layer is a Decorator, eviction is Strategy, Proxy handles load-through,
            Factory wires it all together.
            """);

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO A: Two-Layer — Cold miss → source → L1 hit");
        System.out.println("=".repeat(70));

        var twoLayerCache = CacheFactory.<String>twoLayer(key -> "value_for_" + key);

        System.out.println("\n--- First access (miss all, loads from source) ---");
        String v1 = twoLayerCache.get("user:123");
        System.out.println("Result: " + v1);

        System.out.println("\n--- Second access (L1 hit) ---");
        String v2 = twoLayerCache.get("user:123");
        System.out.println("Result: " + v2);

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO B: L1 full by LRU → L2 hit → promote back to L1");
        System.out.println("=".repeat(70));

        var smallL1 = new CacheProxy<>(
            new LayeredCacheDecorator<>(
                new LocalCache<>(2),
                new RemoteCache<>("L2:Redis", new EvictionStrategy.LRU<>())
            ),
            key -> { System.out.println("[Source] loading " + key); return "v_" + key; }
        );

        System.out.println("\n--- Load key-A, key-B, key-C (L1 size=2, key-A evicted from L1) ---");
        smallL1.get("key-A");
        smallL1.get("key-B");
        smallL1.get("key-C");
        System.out.println("(L1 has key-B, key-C only. key-A evicted via LRU but still in L2)");

        System.out.println("\n--- Re-access key-A → L1 MISS → L2 HIT → promotes back to L1 ---");
        String va = smallL1.get("key-A");
        System.out.println("Result: " + va);

        System.out.println("\n--- Third access → L1 HIT (promotion persisted) ---");
        String vb = smallL1.get("key-A");
        System.out.println("Result: " + vb);

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO C: Three-Layer — fallback through L1→L2→L3→source");
        System.out.println("=".repeat(70));

        var threeLayerCache = CacheFactory.<String>threeLayer(key -> {
            System.out.println("[Source] slow DB query for " + key + " (150ms)");
            return "big_value_for_" + key;
        });

        threeLayerCache.get("config:app");
        System.out.println("(Second access — L1 hit)");
        String v4 = threeLayerCache.get("config:app");
        System.out.println("Result: " + v4);

        // ==========================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SCENARIO D: Write-through → all layers + clear cascade");
        System.out.println("=".repeat(70));

        twoLayerCache.put("session:abc", "sess_data_abc");
        System.out.println("(put cascaded through L1→L2 via Decorator)");

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
