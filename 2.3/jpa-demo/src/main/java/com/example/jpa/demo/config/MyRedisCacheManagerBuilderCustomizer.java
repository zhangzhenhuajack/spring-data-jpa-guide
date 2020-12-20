package com.example.jpa.demo.config;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * 这个依赖spring boot 2.2 以上版本才有效
 */
public class MyRedisCacheManagerBuilderCustomizer implements RedisCacheManagerBuilderCustomizer {
    private MyCacheProperties myCacheProperties;
    private RedisCacheConfiguration redisCacheConfiguration;

    public MyRedisCacheManagerBuilderCustomizer(MyCacheProperties myCacheProperties, RedisCacheConfiguration redisCacheConfiguration) {
        this.myCacheProperties = myCacheProperties;
        this.redisCacheConfiguration = redisCacheConfiguration;
    }

    /**
     * 利用默认配置的只需要这里加就可以了
     * spring.cache.cache-names=abc,def,userlist2,user3
     * 下面是不同的cache-name可以配置不同的过期时间，yaml也支持，如果以后还有其它属性扩展可以改这里
     * spring.cache.redis.cache-name-config.user2=2h
     * spring.cache.redis.cache-name-config.def=2m
     * @param builder
     */
    @Override
    public void customize(RedisCacheManager.RedisCacheManagerBuilder builder) {
        if (ObjectUtils.isEmpty(myCacheProperties.getCacheNameConfig())) {
            return;
        }

        Map<String, RedisCacheConfiguration> cacheConfigurations = myCacheProperties.getCacheNameConfig().entrySet().stream()
                .collect(Collectors
                        .toMap(e->e.getKey(),v->builder
                                .getCacheConfigurationFor(v.getKey())
                                .orElse(RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(redisCacheConfiguration.getValueSerializationPair()))
                                .entryTtl(v.getValue())));

        builder.withInitialCacheConfigurations(cacheConfigurations);
    }
}
