package com.xm.studyproject.java.proxy.dynamicPrxoy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理：目标就是保证只有一个代理类满足所有需求，当然也需要一个公共的接口 这个接口就是InvocationHandler
 */
public class DynamicProxyDemo {

    //获取真实对象的代理类的实例 核心的核心方法！！！！！！
    public Object getProxy(final Object object) {
        return Proxy.newProxyInstance(object.getClass().getClassLoader(),
                object.getClass().getInterfaces(),
                new InvocationHandler() {
                    //切记proxy 是真实的代理类的实例 不是真实对象
                    //method 触发的方法
                    //args 方法中带的参数
                    //返回值是调用方法后 方法的返回值
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //可以自己添加一些前置逻辑
                        doSthBefore();
                        method.setAccessible(true);
                        //方法的返回值
                        Object result = method.invoke(object, args);
                        //可以自己添加一些后置逻辑
                        doSthAfter();
                        //所以不能返回代理类实例 而是应该是方法的返回值
                        //return proxy;
                        return result;
                    }
                });
    }

    /*后置处理器*/
    private void doSthAfter() {
        System.out.println("精美包装，快递一条龙服务");
    }
    /*前置处理器*/

    private void doSthBefore() {
        System.out.println("根据需求，进行市场调研和产品分析");
    }
}
