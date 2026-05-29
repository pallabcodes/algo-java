package com.backend.designpatterns.realworld.caching;

import java.util.function.Function;

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
