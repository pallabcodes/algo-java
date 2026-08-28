package com.backend.designpatterns.realworld.caching;

/**
 * [1/8] Core interface for Proxy + Decorator pattern composition.
 * Unified cache contract that Proxy (load-through) and Decorator (layering) both implement.
 * Without this, each cache layer would need its own interface — Proxy and Decorator
 * require a single uniform type to wrap transparently.
 */
public interface Cacheable<K, V> {
    V get(K key);
    void put(K key, V value);
    void evict(K key);
    void clear();
}
