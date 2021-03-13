package com.xm.studyproject.java.thread.singlethread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

//创建一个单线程
public class SingleThreadDemo {

    private Thread thread2;
    private SingleThread thread1;
    private Thread thread3;

    //创建单线程的几种方式
    public void createThread() {
        //方式1  extend Thread
        thread1 = new SingleThread();
        //开启线程 让线程处于就绪状态 等待cpu调度
        thread1.start();

        //方式二 实现runnable接口
        thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程2名字"+thread2.getName()+"id:"+thread2.getId());
            }
        });
        //方式3 ：实现callable接口 需要用FutureTask将Callable包装
        //FutureTask 是继承Runnable接口的 Callable不是
        //这种方式的区别是执行call后是有返回值的
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("线程3名字"+thread3.getName()+"id:"+thread3.getId());
                return "你好";
            }
        });
        thread3 = new Thread(futureTask);
        thread3.start();
        try {
            System.out.println("线程3返回值" + futureTask.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    class SingleThread extends Thread {
        //重写run方法
        @Override
        public void run() {
            //实现自己的操作
            System.out.println("线程1名字"+thread1.getName()+"id:"+thread1.getId());
        }
    }

}
