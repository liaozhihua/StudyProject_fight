package com.xm.studyproject.java.proxy.staticProxy;

/**
 * 代理：也就是使用者不直接对目标对象进行访问，通过第三方的形式去访问，防止直接访问目标对象给系统带来的不必要复杂性。
 * 静态代理：违背了开闭原则，对修改关闭，对新增开放 导致扩展能力差 可维护差
 * 静态代理类
 */
public class StaticProxyDemo implements PublicInterface{
    //我要去买目标对象1中的辣条 需要有目标对象的引用

    private Target1 target1;
    public StaticProxyDemo(Target1 target1) {
        this.target1 = target1;
    }

    //如果直接这样自己去命名方法名 会导致最后不知道这个真实对象(目标对象)的哪个方法
    //所以该代理类跟真实对象需要使用一个公共接口
    //public void buy() {
    //    if (target1 != null) {
    //        target1.buyFood();
    //    }
    //}

    @Override
    public String buyFood1() {
        if (target1 != null) {
            String buyFood1 = target1.buyFood1();
            return buyFood1;
        }
        return "";
    }

    @Override
    public void buyFood2() {
        if (target1 != null) {
            target1.buyFood2();
        }
    }
}
