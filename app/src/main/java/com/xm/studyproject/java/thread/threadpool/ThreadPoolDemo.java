package com.xm.studyproject.java.thread.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo {

    /**
     * 线程池实例
     */
    private ThreadPoolExecutor executor;

    /**
     * 核心线程数
     */
    private int CORE_NUM = 5;
    /**
     * 最大线程数
     */
    private int MAX_NUM = 10;
    /**
     * 阻塞队列 这边采用的是数组阻塞队列 阻塞队列有8种实现方式
     */
    private BlockingQueue blockingQueue = new ArrayBlockingQueue(100,false);
    /**
     * 线程空闲最大时间(空闲时长超过这个值线程会被回收)
     */
    private int KEEP_ALIVE_TIME = 3000;
    /**
     * 线程工厂 主要是命名 使用的不多 而且我这样使用是错误的 看源码发现 这里面是需要创建线程的
     */
//    private ThreadFactory threadFactory = new ThreadFactory() {
//        //这个对象是需要创建一个线程的
//        @Override
//        public Thread newThread(Runnable r) {
//            return Thread.currentThread();
//        }
//    };
    /**
     * 自定义拒绝策略 系统提供了四种策略
     */
    private RejectedExecutionHandler executionHandler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

        }
    };


    //获取线程池
    public void getThreadPool() {
        if (executor == null) {
            executor = new ThreadPoolExecutor(CORE_NUM, MAX_NUM, KEEP_ALIVE_TIME,
                    TimeUnit.MILLISECONDS, blockingQueue);
        }
    }

    // 执行任务,其实只是把任务加入任务队列，什么时候执行有线程池管理器决定
    private void execute(Runnable runnable) {
        executor.execute(runnable);
    }

    //任务数
    private int taskCount = 1000;
    public void test() {
        for (int i = 0; i < taskCount; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread()+"：线程打印处理");
                }
            };
            //为啥会需要做这么个操作呢  就是当taskCount非常大的时候比如10万 而且这10万个任务可以说是一瞬间创建完成的
            //那么我的阻塞队列+我的线程是没法一瞬间处理完的 那就会崩溃
//            try {
//                Thread.sleep(50);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            if (executor != null) {
                execute(runnable);
            }
        }
    }
}
