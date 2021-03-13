package com.xm.studyproject.java.thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockDemo {
    //可重入锁  synchronized也是可重入锁
    //当Synchronized递归重复去调用的时候 如果是不可重入锁会导致拿不到锁 把自己锁死
    // 导致后续代码执行不到 也不释放锁 所以Synchronized实现可重入锁
    private Lock lock = new ReentrantLock();

    //读写锁 ReadWriteLock是接口
    private ReadWriteLock locks = new ReentrantReadWriteLock();
    //读锁
    private Lock readLock = locks.readLock();
    //写锁
    private Lock writeLock = locks.writeLock();

    private int count = 0;
    private long timeMillis;

    public void test() {
        timeMillis = System.currentTimeMillis();
        for (int i = 0; i < 3; i++) {
            new Thread(new MyRunnable()).start();
        }
    }

    public void test2() {
        timeMillis = System.currentTimeMillis();
        for (int i = 0; i < 3; i++) {
            new Thread(new MyRunnable2()).start();
        }
    }
    class MyRunnable implements Runnable {

        @Override
        public void run() {
            //加锁
            lock.lock();
            //尝试获取锁 参数可以设置获取锁的时候
            //lock.tryLock();
            for (int i = 0; i < 100000; i++) {
                count++;
            }
            System.out.println("LockDemo:"+count);
            System.out.println("时间长度:"+(System.currentTimeMillis()-timeMillis));
            //可释放锁 但是这个释放最好放在finally里面执行 不然担心try的时候执行不到这行代码
            lock.unlock();
        }
    }

    class MyRunnable2 implements Runnable {

        @Override
        public void run() {
            //加锁 读锁
            readLock.lock();
            //lock.tryLock();
            for (int i = 0; i < 100000; i++) {
                count++;
            }
            System.out.println("时间长度:"+(System.currentTimeMillis()-timeMillis));
            readLock.unlock();
        }
    }
}
