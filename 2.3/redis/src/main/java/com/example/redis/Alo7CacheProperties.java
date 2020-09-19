package com.example.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;

@Component
@Getter
@Setter
@ConfigurationProperties("spring.cache.redis")
public class Alo7CacheProperties {
//    @Value("${spring.cache.redis.cache-name-config}")
    private HashMap<String, Duration> cacheNameConfig;

}
