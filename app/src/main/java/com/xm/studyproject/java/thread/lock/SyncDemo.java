package com.xm.studyproject.java.thread.lock;

public class SyncDemo {
    int count = 0;

    private Object object = new Object();
    //不加锁
    public void countAdd() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    count++;
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    count++;
                }
            }
        }).start();
        try {
            Thread.sleep(1000);
            System.out.println("值111:" + count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //加锁
    public void countAddSyn() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    //try {
                    //    Thread.sleep(10000);
                    //} catch (InterruptedException e) {
                    //    e.printStackTrace();
                    //}
                    for (int i = 0; i < 10000; i++) {
                        count++;
                    }
                    System.out.println("sleep1:" + count);
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    for (int i = 0; i < 10000; i++) {
                        count++;
                    }
                    System.out.println("sleep2:" + count);
                }
            }
        }).start();
        try {
            Thread.sleep(0);
            System.out.println("值222:" + count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
