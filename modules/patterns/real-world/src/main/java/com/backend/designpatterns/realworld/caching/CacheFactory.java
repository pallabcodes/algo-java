package com.backend.designpatterns.realworld.caching;

import java.util.function.Function;

/**
 * [7/8] Factory pattern — creates different cache topologies without callers knowing
 * the layer wiring. singleLayer(), twoLayer(), threeLayer() each compose Proxy +
 * Decorator + Strategy internally.
 *
 * Alternative rejected: callers constructing LayeredCacheDecorator directly
 * (every caller duplicates topology logic, violates DRY).
 * 
 * 
 */
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
