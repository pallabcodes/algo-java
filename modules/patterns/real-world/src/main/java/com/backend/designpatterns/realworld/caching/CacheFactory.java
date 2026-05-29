package com.backend.designpatterns.realworld.caching;

import java.util.function.Function;

public class CacheFactory {

    public static <V> Cacheable<String, V> singleLayer(Function<String, V> loader) {
        return new CacheProxy<>(new LocalCache<>(100), loader);
    }

    public static <V> Cacheable<String, V> twoLayer(Function<String, V> loader) {
        LayeredCacheDecorator<V> layered = new LayeredCacheDecorator<>(
            new LocalCache<>(100),
            new RemoteCache<>("L2:Redis", new EvictionStrategy.LRU<>())
        );
        return new CacheProxy<>(layered, loader);
    }

    public static <V> Cacheable<String, V> threeLayer(Function<String, V> loader) {
        LayeredCacheDecorator<V> layered = new LayeredCacheDecorator<>(
            new LocalCache<>(100),
            new RemoteCache<>("L2:Redis", new EvictionStrategy.LRU<>()),
            new RemoteCache<>("L3:CDN", new EvictionStrategy.TTL<>(3600000))
        );
        return new CacheProxy<>(layered, loader);
    }
}
