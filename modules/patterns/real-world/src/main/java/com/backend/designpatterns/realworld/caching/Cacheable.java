package com.backend.designpatterns.realworld.caching;

public interface Cacheable<K, V> {
    V get(K key);
    void put(K key, V value);
    void evict(K key);
    void clear();
}
