package com.example.redis;

import lombok.extern.log4j.Log4j2;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Configuration;

@Configuration
@Log4j2
public class RedisCachingConfigurerSupport extends CachingConfigurerSupport {
    @Override
    public CacheErrorHandler errorHandler() {
        return new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
                log.warn(String.format("Spring cache GET error:cache=%s,key=%s", cache, key), exception);
            }

            @Override
            public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
                log.warn(String.format("Spring cache PUT error:cache=%s,key=%s", cache, key), exception);
            }

            @Override
            public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
                log.warn(String.format("Spring cache EVICT error:cache=%s,key=%s", cache, key), exception);
            }

            @Override
            public void handleCacheClearError(RuntimeException exception, Cache cache) {
                log.warn(String.format("Spring cache CLEAR error:cache=%s", cache), exception);
            }
        };
    }
}
