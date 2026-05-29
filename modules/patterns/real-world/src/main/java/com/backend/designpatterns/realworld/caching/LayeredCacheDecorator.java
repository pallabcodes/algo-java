package com.backend.designpatterns.realworld.caching;

import java.util.List;

public class LayeredCacheDecorator<V> implements Cacheable<String, V> {
    private final List<Cacheable<String, V>> layers;

    @SafeVarargs
    public LayeredCacheDecorator(Cacheable<String, V>... layers) {
        this.layers = List.of(layers);
    }

    public V get(String key) {
        for (int i = 0; i < layers.size(); i++) {
            V value = layers.get(i).get(key);
            if (value != null) {
                if (i > 0) {
                    System.out.println("[Decorator] promoting from L" + (i + 1) + " to L1");
                    layers.getFirst().put(key, value);
                }
                return value;
            }
        }
        System.out.println("[Decorator] MISS across all " + layers.size() + " layers");
        return null;
    }

    public void put(String key, V value) {
        for (var layer : layers) {
            layer.put(key, value);
        }
    }

    public void evict(String key) {
        layers.forEach(l -> l.evict(key));
    }

    public void clear() {
        layers.forEach(Cacheable::clear);
    }
}
