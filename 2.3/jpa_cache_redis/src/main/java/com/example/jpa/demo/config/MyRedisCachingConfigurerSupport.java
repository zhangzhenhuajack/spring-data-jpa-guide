package com.example.jpa.demo.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

@Log4j2
public class MyRedisCachingConfigurerSupport extends CachingConfigurerSupport {
    @Override
    public KeyGenerator keyGenerator() {
        return getKeyGenerator();
    }

    /**
     * 覆盖默认的redis key的生成规则，变成"方法名:参数:参数"
     * @return
     */
    public static KeyGenerator getKeyGenerator() {
        return (target, method, params) -> {
            StringBuilder key = new StringBuilder();
            key.append(ClassUtils.getQualifiedMethodName(method));
            for (Object obc : params) {
                key.append(":").append(obc);
            }
            return key.toString();
        };
    }

    /**
     * 覆盖默认异常处理方法，不抛异常，改打印error日志
     *
     * @return
     */
    @Override
    public CacheErrorHandler errorHandler() {
        return new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
                log.error(String.format("Spring cache GET error:cache=%s,key=%s", cache, key), exception);
            }

            @Override
            public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
                log.error(String.format("Spring cache PUT error:cache=%s,key=%s", cache, key), exception);
            }

            @Override
            public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
                log.error(String.format("Spring cache EVICT error:cache=%s,key=%s", cache, key), exception);
            }

            @Override
            public void handleCacheClearError(RuntimeException exception, Cache cache) {
                log.error(String.format("Spring cache CLEAR error:cache=%s", cache), exception);
            }
        };
    }
}
