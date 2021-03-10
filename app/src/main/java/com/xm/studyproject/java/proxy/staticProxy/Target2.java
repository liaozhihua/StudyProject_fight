package com.xm.studyproject.java.proxy.staticProxy;

/**
 * 目标对象2：这是一家卖水果的店
 */
public class Target2{

    //苹果
    private String apple = "苹果";

    //橘子
    private String orange = "橘子";


    //买50斤橘子 水果
    public void buyFruit1() {
        System.out.println("买50斤" + apple);
    }

    //买50斤橘子
    public void buyFruit2() {
        System.out.println("买50斤" + orange);
    }
}
