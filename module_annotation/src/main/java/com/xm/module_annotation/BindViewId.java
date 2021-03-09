package com.xm.module_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义编译时期的注解，这种方式相对运行期注解优势在于不会影响app运行期间的性能
 */
//编译时期存在的注解
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE,ElementType.FIELD})
public @interface BindViewId {
    int value() default 2;//可以不给 针对于基本数据类型 注解是有默认值的
}
