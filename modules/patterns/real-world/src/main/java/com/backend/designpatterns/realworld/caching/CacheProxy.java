package com.backend.designpatterns.realworld.caching;

import java.util.function.Function;

/**
 * [6/8] Proxy pattern — adds load-through behavior to any Cacheable. On cache miss,
 * fetches the value from the source (DB, API) via a loader function and populates
 * the cache transparently. The caller doesn't know whether the cache was hot or cold.
 *
 * Without this, every cache consumer needs to write the same check-then-load pattern.
 * Alternative rejected: making callers check for null and load themselves
 * (duplicates error handling, timeout logic, retry across the codebase).
 *
 * Call chain on cache miss (what actually happens):
 * <pre>
 * CacheProxy.get("user:123")
 *   → delegate.get("user:123")           // L1 → L2 → all miss → null
 *   → loader.apply("user:123")           // runs caller's lambda (see below)
 *                                         // returns "value_for_user:123"
 *   → delegate.put("user:123", loaded)   // populates L1 + L2
 *   → return "value_for_user:123"
 * </pre>
 *
 * The {@code loader} is a {@code Function<String, V>}.
 * {@code Function} is NOT a language keyword — it's a standard Java interface (Java 8+)
 * with a single abstract method {@code R apply(T t)}.
 *
 * When caller writes {@code key -> "value_for_" + key}, Java compiles it to:
 * <pre>{@code
 * Function<String, String> loader = new Function<>() {
 *     public String apply(String key) {     // ← this is the apply() being called
 *         return "value_for_" + key;
 *     }
 * };
 * }</pre>
 * So {@code loader.apply("user:123")} → executes lambda body → {@code "value_for_user:123"}.
 */
public class CacheProxy<V> implements Cacheable<String, V> {
    private final Cacheable<String, V> delegate; // actual cache LocalCache, RemoteCache or LayeredCacheDecorator
    private final Function<String, V> loader; // only invoked on cache miss

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
