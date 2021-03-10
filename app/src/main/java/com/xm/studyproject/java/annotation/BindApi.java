package com.xm.studyproject.java.annotation;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BindApi {
    //通过注解替换setContentView 跟findViewById方法
    public static void bind(Activity activity) {
        Class<? extends Activity> aClass = activity.getClass();
        //判断该类是否使用BindId.class注解
        if (aClass.isAnnotationPresent(BindId.class)) {
            //获取该类上的注解 就是该注解定义在该类上面
            BindId annotation = aClass.getAnnotation(BindId.class);
            //获取注解的value
            int value = annotation.value();
            try {
                //目的是替换activity中的setContentView方法
                //调用方法
                Method method = aClass.getMethod("setContentView", int.class);
                method.setAccessible(true);
                method.invoke(activity, value);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        //替换成员变量的注解
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(BindId.class)) {
                BindId annotation = field.getAnnotation(BindId.class);
                int value = annotation.value();
                //方式一
//                View viewById = activity.findViewById(value);
//                try {
//                    field.setAccessible(true);
//                    field.set(activity, viewById);
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
                //方式二
                Method method = null;
                try {
                    method = aClass.getMethod("findViewById", int.class);
                    //针对private修饰的方法进行可访问
                    method.setAccessible(true);
                    //通过反射调用该方法
                    Object invoke = method.invoke(activity, value);
                    field.setAccessible(true);
                    field.set(activity,invoke);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    //替换安卓中点击方法
    public static void click(final Activity activity) {
        final Class<? extends Activity> aClass = activity.getClass();
        //获取该activity中所有方法 包括private
        Method[] methods = aClass.getDeclaredMethods();
        for (final Method method : methods) {
            //判断该方法是否使用该注解
            if (method.isAnnotationPresent(BindClick.class)) {
                BindClick annotation = method.getAnnotation(BindClick.class);
                final int[] values = annotation.value();
                for (int i = 0; i < values.length; i++) {
                    int value = values[i];
                    final View viewById = activity.findViewById(value);
                    viewById.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //调用使用了该注解的方法
                            try {
                                method.setAccessible(true);
                                method.invoke(activity, viewById);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }
    }
}
