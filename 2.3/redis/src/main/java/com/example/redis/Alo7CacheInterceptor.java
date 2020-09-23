package com.example.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.interceptor.CacheInterceptor;
import org.springframework.cache.interceptor.CacheOperationInvoker;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;

public class Alo7CacheInterceptor extends CacheInterceptor {
    private ObjectMapper objectMapper;
    public Alo7CacheInterceptor(ObjectMapper objectMapper)
    {
        System.out.println("jack................");
        this.objectMapper = objectMapper;
    }
    @Nullable
    protected Object execute(CacheOperationInvoker invoker, Object target, Method method, Object[] args) {
        try {
            if (LinkedHashMap.class.isInstance(result)) {
                return objectMapper.convertValue(result,method.getReturnType());
            }
        } catch (Exception e) {
            this.errorHandler.get().handleCacheGetError(new RuntimeException("!result.getClass().isInstance(method.getReturnType())"),null,method.getName());
            return invoker.invoke();
        }

        if (result!=null && !method.getReturnType().isInstance(result)) {
            this.errorHandler.get().handleCacheGetError(new RuntimeException("!result.getClass().isInstance(method.getReturnType())"),null,method.getName());
            return invoker.invoke();
        }
        return result;
    }
}
