package com.xm.studyproject.java.proxy.staticProxy;

/**
 * 公共接口 抽象主题类：是代理类跟被代理类共同的接口方法
 */
public interface PublicInterface {

    //第一家目标对象卖零食
    String buyFood1();
    void buyFood2();

    //如果第二家目标对象卖水果
}
