package com.example.redis;

import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;

public class RedisCacheErrorHandler extends SimpleCacheErrorHandler {
    public RedisCacheErrorHandler() {
        super();
    }

    @Override
    public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
        super.handleCacheGetError(exception, cache, key);
    }

    @Override
    public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
        super.handleCachePutError(exception, cache, key, value);
    }

    @Override
    public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
        super.handleCacheEvictError(exception, cache, key);
    }

    @Override
    public void handleCacheClearError(RuntimeException exception, Cache cache) {
        super.handleCacheClearError(exception, cache);
    }
}
