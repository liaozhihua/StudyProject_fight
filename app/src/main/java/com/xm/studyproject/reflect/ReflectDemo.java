package com.xm.studyproject.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 主要是针对反射的工具类。
 * 反射使用的周期是在运行期 如果信息需要在编译期拿到是做不到的 可能需要注解处理器。
 * 反射思想：就是在运行的时候才需要操作什么，获取类的完整结构 比如构造函数 变量 方法等并调用
 * 什么时候需要使用反射呢？正常情况下我们可以new对象，但是有些框架的时候 我们没法直接使用
 * 反射对性能是有一定影响的 但是没有我们想象的那么夸张
 */
public class ReflectDemo {

    public static void testClass(String className) {
        try {
            //通过包名+类名的形式
            Class<?> aClass = Class.forName(className);
            //通过类实例化对象
            Person person = (Person) aClass.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    //通过包名+类名的形式
    public static void testConstructor(String className) {
        Class<?> aClass = null;
        try {
            aClass = Class.forName(className);
            //获取当前类中的所有构造函数 包括private
            // private的范围是在当前类中
            // 默认修饰符的范围是当前包中的所有地方
            //protected的范围是当前包中所有地方 以及当前类的子类中
            Constructor<?>[] declaredConstructors = aClass.getDeclaredConstructors();
            for (Constructor<?> constructor : declaredConstructors) {
                System.out.println(constructor);
            }
            //获取当前类及父类的所有构造方法 但是不包括private
            Constructor<?>[] constructors = aClass.getConstructors();
            //todo 获取当前类(不包含父类)指定的构造函数??? 传入构造函数相应参数 但是如参数本身就是int 也是传入int而不是Integer
            for (Constructor<?> constructor : constructors) {
                System.out.println(constructor);
            }
            Constructor<?> constructor = aClass.getConstructor(String.class, int.class);
            //实例化构造函数 传入参数的真实值
            Person newInstance = (Person) constructor.newInstance("小米", 28);
            System.out.println(newInstance);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    //换种方式传入
    public static void testMethod(Class aClass) {
        //获取当前类中的所有方法 包括private方法 private的
        Method[] declaredMethods = aClass.getDeclaredMethods();
        //获取当前类以及父类的所有方法 但是不包括private
        Method[] methods = aClass.getMethods();
        for (Method method : methods) {
            System.out.println(method);
        }
        //todo 获取当前类(不包含父类)中指定的方法
        //第一个参数是方法名 也就是调用哪个方法 第二个参数就是方法中传入的参数
        Method method = null;
        try {
            method = aClass.getMethod("setInfo", String.class, int.class);
            //调用方法前 如果是私有方法 需要设置可访问权限为true
            method.setAccessible(true);
            //调用方法
            //第一个参数是对象 也就是实例 因为没有实例就不存在调用方法一说
            //第二个就是参数赋值 也就是实际的值
            //todo 返回值是什么？  同时不考虑没有无参的构造函数吗
            Object person = aClass.newInstance();
            System.out.println(method);
            Object invoke = method.invoke(person, "小花", 30);
            System.out.println(invoke);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void testField(Class aClass) {
        //todo 获取当前类的成员变量 局部变量算吗？
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field field : declaredFields) {
            System.out.println(field);
        }
        //获取当前类及父类的成员变量 但是不包括private
        Field[] fields = aClass.getFields();
        for (Field field : fields) {
            System.out.println(field);
        }
        //获取指定变量
        //参数就是变量名
        Field field = null;
        try {
            field = aClass.getField("name");
            //获取实例对象中变量的值
            Object object = aClass.newInstance();
            String name = (String) field.get(object);
            System.out.println(name);
            //对实例中的变量进行赋值
            field.set(object, "小陈同学");
            System.out.println(field.get(object));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }
}
