package com.xm.studyproject.java.reflect;

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
            //通过类实例化对象 如果没有非参的构造函数 需要传参 不然会报错
            // https://blog.csdn.net/qq_41378597/article/details/102486128
            //Class.newInstance() 只能够调用无参的构造函数，即默认的构造函数 如果该类没有默认的构造函数 直接这样调用会报错
            //如果想通过调用带参的生成带参的实例 需要通过newInstance Constructor.newInstance() 可以根据传入的参数，调用任意构造函数。
            //具体使用参考 https://blog.csdn.net/qq_41378597/article/details/102486128
            //会报错 下面这种写法会报错
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
            for (Constructor<?> constructor : constructors) {
                System.out.println(constructor);
            }
            //获取当前类(不包含父类)指定的构造函数 传入构造函数相应参数 但是如参数本身就是int 也是传入int而不是Integer
            //这种方式是获取不到私有的构造函数
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
        //获取当前类中的所有方法 包括private方法
        Method[] declaredMethods = aClass.getDeclaredMethods();
        //获取当前类以及父类的所有方法 但是不包括private
        Method[] methods = aClass.getMethods();
        for (Method method : methods) {
            System.out.println(method);
        }
        //获取当前类(不包含父类)中指定的方法
        //第一个参数是方法名 也就是调用哪个方法 第二个参数就是方法中传入的参数
        Method method = null;
        try {
            //这种方式是获取不到私有方法的
            //method = aClass.getMethod("setInfo", String.class, int.class);
            //需要是这种方式
            method = aClass.getDeclaredMethod("setInfo", String.class, int.class);
            //调用方法前 如果是私有方法 需要设置可访问权限为true 不然会报错
            method.setAccessible(true);
            //调用方法
            //第一个参数是对象 也就是实例 因为没有实例就不存在调用方法一说
            //第二个就是参数赋值 也就是实际的值
            Constructor constructor = aClass.getDeclaredConstructor(String.class);
            Object o = constructor.newInstance("小小");
            System.out.println(method);
            Object invoke = method.invoke(o, "小花", 30);
            System.out.println(invoke);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }  catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static void testField(Class aClass) {
        //获取当前类的成员变量 不包括局部变量
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field field : declaredFields) {
            System.out.println(field);
        }
        //获取当前类及父类的成员变量 但是不包括private Object成员变量都是私有的
        Field[] fields = aClass.getFields();
        for (Field field : fields) {
            System.out.println(field);
        }
        //获取指定变量
        //参数就是变量名
        Field field = null;
        try {
            //获取实例
            Constructor constructor = aClass.getDeclaredConstructor(String.class);
            Object object = constructor.newInstance("小小");

            //获取私有成员变量 如果没有获取到是会报错的
            //field = aClass.getField("name");
            //获取实例对象中变量的值
            field = aClass.getDeclaredField("name");
            //需要上面getDeclaredField可以获取到私有成员变量 但是如果想获取私有变量的值还是需要
            field.setAccessible(true);

            String name = (String) field.get(object);
            System.out.println(name);
            //对实例中的变量进行赋值 如果该变量是私有的需要设置
            field.setAccessible(true);
            field.set(object, "小陈同学");
            System.out.println(field.get(object));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
