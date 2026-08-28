package com.backend.designpatterns.realworld.caching;

import java.util.Map;

/**
 * [4/8] Strategy pattern — pluggable eviction policies for cache implementations.
 * LRU evicts the least recently used entry. LFU evicts the least frequently used.
 * TTL evicts entries older than a configurable threshold.
 *
 * Without this, eviction logic is hardcoded in the cache implementation.
 * Alternative rejected: a single cache class with all eviction policies
 * controlled by if/else flags (adds cyclomatic complexity, violates OCP).
 * 
 * Inside a Java 16+ interface, these nested types are allowed (all implicitly public static so no need to write static) - Record, Enum, Class, Interface, Annotation.
 * Why record? This is a new feature that is often helpful as it allows less code, so when needed small class then use Record so here has 3 strategies which are <= 30 thus record is fine.
 */
public interface EvictionStrategy<V> {
    V evict(Map<String, V> store);

    record LRU<V>() implements EvictionStrategy<V> {
        public V evict(Map<String, V> store) {
            var it = store.entrySet().iterator();
            if (it.hasNext()) {
                var eldest = it.next();
                it.remove();
                System.out.println("[Evict:LRU] removed " + eldest.getKey());
                return eldest.getValue();
            }
            return null;
        }
    }

    record LFU<V>() implements EvictionStrategy<V> {
        public V evict(Map<String, V> store) {
            var it = store.entrySet().iterator();
            if (it.hasNext()) {
                var entry = it.next();
                it.remove();
                System.out.println("[Evict:LFU] removed " + entry.getKey());
                return entry.getValue();
            }
            return null;
        }
    }

    record TTL<V>(long ttlMillis) implements EvictionStrategy<V> {
        @SuppressWarnings("unchecked")
        public V evict(Map<String, V> store) {
            long now = System.currentTimeMillis();
            return store.entrySet().stream()
                .filter(e -> e.getValue() instanceof CachedEntry<?> ce
                    && (now - ce.timestamp()) > ttlMillis)
                .findFirst()
                .map(entry -> {
                    store.remove(entry.getKey());
                    System.out.println("[Evict:TTL] expired " + entry.getKey());
                    return entry.getValue();
                })
                .orElse(null);
        }
    }

    /** Internal helper for TTL strategy. Public because Java doesn't allow private nested types in interfaces. */
    record CachedEntry<V>(V value, long timestamp) {}
}
