package com.example.jpa.demo.config;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import static com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module.Feature.REPLACE_PERSISTENT_COLLECTIONS;

@EnableCaching
@Configuration
@EnableConfigurationProperties(value = {MyCacheProperties.class,CacheProperties.class})
@AutoConfigureAfter({CacheAutoConfiguration.class})
public class CacheConfiguration {
    /**
     * 支持 不同的 cache name有不同的缓存时间的配置
     *
     * @param myCacheProperties
     * @param redisCacheConfiguration
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "myRedisCacheManagerBuilderCustomizer")
    @ConditionalOnClass(RedisCacheManagerBuilderCustomizer.class)
    public MyRedisCacheManagerBuilderCustomizer myRedisCacheManagerBuilderCustomizer(MyCacheProperties myCacheProperties, RedisCacheConfiguration redisCacheConfiguration) {
        return new MyRedisCacheManagerBuilderCustomizer(myCacheProperties,redisCacheConfiguration);
    }
    /**
     * cache 异常不抛异常，只打印error 日志
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "myRedisCachingConfigurerSupport")
    public MyRedisCachingConfigurerSupport myRedisCachingConfigurerSupport() {
        return new MyRedisCachingConfigurerSupport();
    }

    /**
     * 依赖默认的ObjectMapper，实现普通的json序列化
     * @param defaultObjectMapper
     * @return
     */
    @Bean(name = "genericJackson2JsonRedisSerializer")
    @ConditionalOnMissingBean(name = "genericJackson2JsonRedisSerializer")
    public GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer(ObjectMapper defaultObjectMapper) {
        ObjectMapper objectMapper = defaultObjectMapper.copy();
        objectMapper.registerModule(new Hibernate5Module().enable(REPLACE_PERSISTENT_COLLECTIONS)); //支持JPA的实体的json的序列化
        objectMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);//培训
        objectMapper.deactivateDefaultTyping(); //关闭 defaultType，不需要关心reids里面是否对象的类型
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }
    /**
     * 覆盖 RedisCacheConfiguration，只是修改serializeValues with jackson
     *
     * @param cacheProperties
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "jacksonRedisCacheConfiguration")
    public RedisCacheConfiguration jacksonRedisCacheConfiguration(CacheProperties cacheProperties,
                                                                  GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer) {

        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
        RedisCacheConfiguration config = RedisCacheConfiguration
                .defaultCacheConfig();
        config = config.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(genericJackson2JsonRedisSerializer));
        if (redisProperties.getTimeToLive() != null) {
            config = config.entryTtl(redisProperties.getTimeToLive());
        }
        if (redisProperties.getKeyPrefix() != null) {
            config = config.prefixCacheNameWith(redisProperties.getKeyPrefix());
        }
        if (!redisProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }
        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }

        return config;
    }
}


