package com.backend.designpatterns.realworld.caching;

import java.util.function.Function;

/**
 * Proxy pattern — adds load-through behavior to any Cacheable. On cache miss,
 * fetches the value from the source (DB, API) via a loader function and populates
 * the cache transparently. The caller doesn't know whether the cache was hot or cold.
 *
 * Without this, every cache consumer needs to write the same check-then-load pattern.
 * Alternative rejected: making callers check for null and load themselves
 * (duplicates error handling, timeout logic, retry across the codebase).
 */
public class CacheProxy<V> implements Cacheable<String, V> {
    private final Cacheable<String, V> delegate;
    private final Function<String, V> loader;

    public CacheProxy(Cacheable<String, V> delegate, Function<String, V> loader) {
        this.delegate = delegate;
        this.loader = loader;
    }

    public V get(String key) {
        V cached = delegate.get(key);
        if (cached != null) return cached;
        System.out.println("[CacheProxy] loading " + key + " from source");
        V loaded = loader.apply(key);
        delegate.put(key, loaded);
        return loaded;
    }

    public void put(String key, V value) { delegate.put(key, value); }
    public void evict(String key) { delegate.evict(key); }
    public void clear() { delegate.clear(); }
}
