package com.example.jpa.demo.config;

import org.hibernate.EmptyInterceptor;
import org.springframework.stereotype.Component;

@Component
public class SlicedTableInterceptor extends EmptyInterceptor {
    @Override
    public String onPrepareStatement(String sql) {
        System.out.println("================="+sql);
        return super.onPrepareStatement(sql);
    }
}
