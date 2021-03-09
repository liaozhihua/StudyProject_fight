package com.xm.module_api;

import android.app.Activity;
import android.view.View;

import com.xm.module_compiler.BindProxy;

import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 最后一步
 * 之前我们已经定义了注解 且在javaLibrary实现了处理注解的注解处理器 并实现了相应的代理类
 * 这块就是提供出来给其他开发者使用
 */
public class HelperApi {
    /**
     * 用来缓存反射出来的类，节省每次都去反射引起的性能问题
     */
    static final Map<Class<?>, Constructor<?>> BINDINGS = new LinkedHashMap<>();
    public static void inject(Activity o) {
        inject(o, o.getWindow().getDecorView());
    }

    public static void inject(Activity host, View decorView) {
        //下面这块理解起来有点困难
        String classFullName = host.getClass().getName() + BindProxy.ClassSuffix;
        try {
            Constructor constructor=BINDINGS.get(host.getClass());
            if(constructor==null){
                Class proxy = Class.forName(classFullName);
                //这块我个人估计是实现activiy根的构造函数
                constructor=proxy.getDeclaredConstructor(host.getClass(),View.class);
                BINDINGS.put(host.getClass(),constructor);
            }
            constructor.setAccessible(true);
            constructor.newInstance(host,decorView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
