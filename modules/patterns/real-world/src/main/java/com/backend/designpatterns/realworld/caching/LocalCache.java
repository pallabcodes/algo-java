package com.backend.designpatterns.realworld.caching;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * [2/8] L1 cache — in-memory, low-latency (sub-microsecond).
 * Leaf node in the Decorator chain: the innermost layer that other layers wrap.
 * Uses LinkedHashMap with LRU-like eviction via removeEldestEntry.
 */
public class LocalCache<V> implements Cacheable<String, V> {
    private final Map<String, V> store;
    private final int maxSize;

    public LocalCache(int maxSize) {
        this.maxSize = maxSize;
        this.store = new LinkedHashMap<>(maxSize, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<String, V> eldest) {
                return size() > LocalCache.this.maxSize;
            }
        };
    }

    public V get(String key) {
        V val = store.get(key);
        if (val != null) System.out.println("[L1:LocalCache] HIT " + key);
        return val;
    }

    public void put(String key, V value) {
        store.put(key, value);
        System.out.println("[L1:LocalCache] PUT " + key + " (size=" + store.size() + ")");
    }

    public void evict(String key) { store.remove(key); }
    public void clear() { store.clear(); }
}
