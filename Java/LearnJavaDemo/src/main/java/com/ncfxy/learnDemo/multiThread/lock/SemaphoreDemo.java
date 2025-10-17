package com.ncfxy.learnDemo.multiThread.lock;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

    public static void main(String[] args) {
        // 8个工人使用5台机器的例子
        int N = 8;
        Semaphore semaphore = new Semaphore(5);
        for(int i = 0;i < N;i++){
            new Thread(new Worker(i,semaphore)).start();
        }
    }

    private static class Worker implements Runnable{
        private int num;
        private Semaphore semaphore;
        public Worker(int num, Semaphore semaphore) {
            this.num = num;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try{
                semaphore.acquire();
                System.out.println("工人" + this.num + "占用一个机器生产");
                Thread.sleep(2000);
                System.out.println("工人" + this.num + "释放出机器");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 使用示例
     */
    public void usage() {
        Semaphore s1 = new Semaphore(15); //参数permits表示许可数目，即同时可以允许多少线程进行访问
        Semaphore s2 = new Semaphore(15, true); // fair 表示是否公平
        try {
            s1.acquire(); //获取一个许可
            s2.acquire(2); //获取permits个许可
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        s1.release(); //释放一个许可
        s2.release(2); //释放permits个许可
        // 尝试获取一个许可，若获取成功，则立即返回true，若获取失败，则立即返回false
        // 不会阻塞
        s1.tryAcquire();
        s1.availablePermits();
    }

}
