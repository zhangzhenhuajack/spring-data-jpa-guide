package com.example.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;

import java.util.Map;
import java.util.stream.Collectors;

public class Alo7RedisCacheManagerBuilderCustomizer implements RedisCacheManagerBuilderCustomizer {
    @Autowired
    private Alo7CacheProperties alo7CacheProperties;
    @Autowired
    private RedisCacheConfiguration redisCacheConfiguration;
    @Override
    public void customize(RedisCacheManager.RedisCacheManagerBuilder builder) {
        Map<String, RedisCacheConfiguration> cacheConfigurations = alo7CacheProperties.getCacheNameConfig().entrySet().stream()
                .collect(Collectors
                        .toMap(e->e.getKey(),v->builder
                                .getCacheConfigurationFor(v.getKey())
                                .orElse(RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(redisCacheConfiguration.getValueSerializationPair()))
                                .entryTtl(v.getValue())));

        builder.withInitialCacheConfigurations(cacheConfigurations);
    }
}
