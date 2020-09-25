package com.example.redis;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.annotation.ProxyCachingConfiguration;
import org.springframework.cache.interceptor.BeanFactoryCacheOperationSourceAdvisor;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheInterceptor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Role;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.function.Supplier;

@Configuration
@AutoConfigureBefore(RedisAutoConfiguration.class)
@EnableCaching
public class RedisConfig implements InitializingBean, ApplicationContextAware {
//    @Bean
//    public RedisTemplate<String, Serializable> redisTemplate(LettuceConnectionFactory connectionFactory) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY,true);
//        objectMapper.deactivateDefaultTyping();
//          new GenericJackson2JsonRedisSerializer(objectMapper)
//        RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));
//        redisTemplate.setConnectionFactory(connectionFactory);
//        return redisTemplate;
//    }
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties) {
                ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY,true);
        objectMapper.deactivateDefaultTyping();
//        objectMapper.enableDefaultTypingAsProperty(ObjectMapper.DefaultTyping.NON_FINAL, "@class");
        GenericJackson2JsonRedisSerializer redisSerializer =   new GenericJackson2JsonRedisSerializer(objectMapper);

        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
        org.springframework.data.redis.cache.RedisCacheConfiguration config = org.springframework.data.redis.cache.RedisCacheConfiguration
                .defaultCacheConfig();
        config = config.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer));
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
    @Bean
    public Alo7RedisCacheManagerBuilderCustomizer alo7RedisCacheManagerBuilderCustomizer() {
        return new Alo7RedisCacheManagerBuilderCustomizer();
    }
//    @Bean
//    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
//    @Primary
//    public CacheInterceptor cacheInterceptor() {
//        CacheInterceptor interceptor = new CacheInterceptor();
////        interceptor.configure(this.errorHandler, this.keyGenerator, this.cacheResolver, this.cacheManager);
////        interceptor.setCacheOperationSource(cacheOperationSource());
//        return interceptor;
//    }

    private ApplicationContext applicationContext;
    @Autowired
    private RedisCacheManager cacheManage;

    public CacheManager getCacheManager() {
        return this.cacheManage;
    }
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Alo7CacheProperties alo7CacheProperties;
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheInterceptor getCacheInterceptor(
            @Nullable Supplier<CacheManager> redisCacheManager, BeanFactoryCacheOperationSourceAdvisor beanFactoryCacheOperationSourceAdvisor) {
        CacheInterceptor advice = (CacheInterceptor) beanFactoryCacheOperationSourceAdvisor.getAdvice();

        CacheInterceptor interceptor = new Alo7CacheInterceptor(objectMapper);
        interceptor.configure(advice::getErrorHandler, advice::getKeyGenerator, advice::getCacheResolver, redisCacheManager);
        interceptor.setCacheOperationSource(advice.getCacheOperationSource());
        return interceptor;
    }

    @Override
    public void afterPropertiesSet() {
        BeanFactoryCacheOperationSourceAdvisor beanFactoryCacheOperationSourceAdvisor = applicationContext.getBean(BeanFactoryCacheOperationSourceAdvisor.class);

//        CacheInterceptor advice = (CacheInterceptor) beanFactoryCacheOperationSourceAdvisor.getAdvice();

//        CacheInterceptor interceptor = new Alo7CacheInterceptor(objectMapper);
//        interceptor.configure(advice::getErrorHandler, advice::getKeyGenerator, advice::getCacheResolver, this::getCacheManager);
//        interceptor.setCacheOperationSource(advice.getCacheOperationSource());

        beanFactoryCacheOperationSourceAdvisor.setAdvice(getCacheInterceptor(this::getCacheManager,beanFactoryCacheOperationSourceAdvisor));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
