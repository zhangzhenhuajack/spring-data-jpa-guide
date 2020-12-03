package com.example.jpa.example1.common;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
/**
 * 自定义一个注解对返回结果进行包装
 */
public @interface WarpWithData {
}
