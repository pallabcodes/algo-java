package com.backend.designpatterns.realworld.caching;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * [3/8] L2/L3 cache — represents a remote/cross-process cache (Redis, CDN).
 * Can be used as a standalone leaf or within LayeredCacheDecorator.
 * Composes with EvictionStrategy (Strategy pattern) for pluggable eviction policy.
 * Without Strategy, eviction logic would be if/else inside this class.
 */
public class RemoteCache<V> implements Cacheable<String, V> {
    private final Map<String, V> store = Collections.synchronizedMap(
        new LinkedHashMap<String, V>(16, 0.75f, true)
    );
    private final String name;
    private final EvictionStrategy<V> evictionStrategy;

    public RemoteCache(String name, EvictionStrategy<V> evictionStrategy) {
        this.name = name;
        this.evictionStrategy = evictionStrategy;
    }

    public V get(String key) {
        V val = store.get(key);
        if (val != null) System.out.println("[" + name + "] HIT " + key);
        else System.out.println("[" + name + "] MISS " + key);
        return val;
    }

    public void put(String key, V value) {
        store.put(key, value);
        System.out.println("[" + name + "] PUT " + key);
    }

    public void evict(String key) {
        store.remove(key);
        System.out.println("[" + name + "] EVICT " + key);
    }

    public void clear() { store.clear(); }

    public void enforceEviction() {
        V evicted;
        while ((evicted = evictionStrategy.evict(store)) != null) {
            System.out.println("[" + name + "] evicted via " + evictionStrategy.getClass().getSimpleName());
        }
    }
}
