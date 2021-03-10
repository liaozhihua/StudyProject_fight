package com.xm.studyproject.java.proxy.dynamicPrxoy;

/**
 * 目标对象2：这是一家卖水果的店
 */
public class DynamicTarget2 implements PublicInterface2{

    //苹果
    private String apple = "苹果";

    //橘子
    private String orange = "橘子";


    //买50斤橘子 水果
    @Override
    public void buyFruit1() {
        System.out.println("买50斤" + apple);
    }

    //买50斤橘子
    @Override
    public void buyFruit2() {
        System.out.println("买50斤" + orange);
    }
}
