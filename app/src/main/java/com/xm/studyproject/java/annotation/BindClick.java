package com.xm.studyproject.java.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解针对于方法。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BindClick {
    //因为该注解就是为了避免安卓频繁的设置setOnclickListener方法
    // 可以一次性针对 setOnclickListener方法设置
    int[] value();
}
