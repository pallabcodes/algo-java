package com.backend.designpatterns.realworld.caching;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RemoteCache<V> implements Cacheable<String, V> {
    private final Map<String, V> store = new ConcurrentHashMap<>();
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

    @SuppressWarnings("unchecked")
    public void enforceEviction() {
        V evicted;
        while ((evicted = evictionStrategy.evict((Map<String, V>) store)) != null) {
            System.out.println("[" + name + "] evicted via " + evictionStrategy.getClass().getSimpleName());
        }
    }
}
