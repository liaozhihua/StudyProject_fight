package com.xm.module_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 针对点击事件 在编译期进行注解。
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface BindViewClick {
    int[] value() ;
}
