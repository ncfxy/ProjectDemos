package com.ncfxy.learnDemo.multiThread.lock;

import java.util.concurrent.CountDownLatch;

/**
 * 允许一个或多个线程一直等待，直到其他线程的操作执行完后再执行
 */
public class CountDownLatchDemo {

    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(3);
        Thread cacheService = new Thread(new TestService("CacheService", 3000, latch));
        Thread alertService = new Thread(new TestService("AlertService", 1000, latch));
        Thread validationService = new Thread(new TestService("ValidationService", 1000, latch));

        cacheService.start();
        alertService.start();
        validationService.start();

        new Thread(new AnotherWaitThread(latch)).start();
        new Thread(new AnotherWaitThread(latch)).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("All service are up, Application is starting now.");
    }

    private static class AnotherWaitThread implements Runnable {
        private final CountDownLatch latch;
        public AnotherWaitThread(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("This is another waiting thread");
        }
    }

    private static class TestService implements Runnable {
        private final String name;
        private final int timeToStart;
        private final CountDownLatch latch;

        public TestService(String name, int timeToStart, CountDownLatch latch) {
            this.name = name;
            this.timeToStart = timeToStart;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(timeToStart);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + " is Up.");
            latch.countDown();
        }
    }
}


