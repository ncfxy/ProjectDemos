package com.ncfxy.learnDemo.multiThread.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {


    // true 为公平锁， false为非公平锁
    // 公平锁会维护一个线程的队列，每个线程抢占锁的顺序为先后调用lock方法的顺序依次获取锁
    ReentrantLock reentrantLock = new ReentrantLock(false);

    public static void main(String[] args) {
        ReentrantLockDemo reentrantLockDemo = new ReentrantLockDemo();
        reentrantLockDemo.runThreads();
    }

    public void runThreads() {
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0;i < 5;i++){
            exec.execute(new Thread(new InnerService()));
        }
        exec.shutdown();
    }
    
    class InnerService implements Runnable{

        @Override
        public void run() {
            System.out.println("我要开始运行了 " + Thread.currentThread().getName());
            try{
                reentrantLock.lock();
                System.out.println("我拿到了锁 " + Thread.currentThread().getName());
                for(int i = 0;i < 100;i++){
                    Thread.sleep(10);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
            // 最好使用try 块包裹起来，在finally中保证执行unlock操作
        }
    }
}
