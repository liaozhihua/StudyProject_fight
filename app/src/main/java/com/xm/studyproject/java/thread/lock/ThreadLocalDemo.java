package com.xm.studyproject.java.thread.lock;

import androidx.annotation.Nullable;

public class ThreadLocalDemo {
    private int count = 0;
    private ThreadLocal<Integer> threadLocal = new ThreadLocal() {
        //重写这个方法
        @Nullable
        @Override
        protected Integer initialValue() {
            return count;
        }
    };

    public void test() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    Integer value = threadLocal.get();
                    value++;
                    threadLocal.set(value);
                    System.out.println(Thread.currentThread().getName()+"xxx :"
                            +threadLocal.get());
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    Integer value = threadLocal.get();
                    value++;
                    threadLocal.set(value);
                    System.out.println(Thread.currentThread().getName()+"xxx :"
                            +threadLocal.get());
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    Integer value = threadLocal.get();
                    value++;
                    threadLocal.set(value);
                    System.out.println(Thread.currentThread().getName()+"xxx :"
                            +threadLocal.get());
                }
            }
        }).start();
    }

}
