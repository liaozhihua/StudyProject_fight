package com.xm.studyproject.java.proxy.dynamicPrxoy;

import com.xm.studyproject.java.proxy.staticProxy.PublicInterface;

/**
 * 动态代理的时候 目标对象1 比如这个是一间产生零食的作坊
 */
public class DynamicTarget1 implements PublicInterface {
    //辣条
    private String food1 = "辣条";

    //糖果
    private String food2 = "糖果";

    //买50包辣条
    @Override
    public String buyFood1() {
        System.out.println("买50包" + food1);
        return "买50包" + food1;
    }

    //买一箱糖果
    @Override
    public void buyFood2() {
        System.out.println("买一箱果" + food2);
    }
}
