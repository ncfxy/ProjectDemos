package com.ncfxy.learnDemo.multiThread.threadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 当线程池中线程的数目大于5时，便将任务放入任务缓存队列里面，
 * 当任务缓存队列满了之后，便创建新的线程。
 * 此程序中，如果将for循环中改成执行20个任务，就会抛出任务拒绝异常了
 */
public class ThreadPoolBasicSample {
    
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(5));
        
        for(int i = 0;i < 20;i++){
            MyTask myTask = new MyTask(i);
            executor.execute(myTask);
            System.out.println("线程池中线程数目: " + executor.getPoolSize()+"， 队列中等待执行的任务数目:" + executor.getQueue().size()+
                    ", 以执行完别的任务数目: " + executor.getCompletedTaskCount());
        }
    }

    public void useExecutors() {
        /**
         * 定长线程池，多余的放在LinkedBlockingQueue中等待
         */
        Executors.newFixedThreadPool(10);
        /**
         * corePoolSize为0, maximumPoolSize为maxInteger.MAX_VALUE
         * 线程存活时间为60秒
         * 使用SynchronousQueue
         */
        Executors.newCachedThreadPool();
        // 只有一个线程的定长线程池
        Executors.newSingleThreadExecutor();
        Executors.newSingleThreadScheduledExecutor();
        Executors.newScheduledThreadPool(10).schedule(new Thread(),3000, TimeUnit.MILLISECONDS);
    }
    
    static class MyTask implements Runnable {
        
        private int taskNum;
        
        public MyTask(int num) {
            this.taskNum = num;
        }

        @Override
        public void run() {
            System.out.println("正在执行 task " + taskNum);
            try {
                Thread.currentThread().sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task " + taskNum + "执行完毕");
        }
    }
}
