package com.xm.studyproject.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解。
 */
@Retention(RetentionPolicy.RUNTIME) //注解的周期是运行期 默认是编译期 即运行后不存在
@Target({ElementType.TYPE,ElementType.FIELD})//注解使用范围 如本处是类跟成员变量上 默认是所有地方
public @interface BindId {
    int value();
}
