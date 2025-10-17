package com.ncfxy.learnDemo.multiThread.lock.specialUsage;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class CopyOnWriteArrayListDemo {
    private static class ReadThread implements Runnable {
        private List<Integer> list;
        public ReadThread(List<Integer> list){
            this.list = list;
        }
        
        @Override
        public void run() {
            System.out.println("This is thread" + Thread.currentThread().getName());
            for(Integer ele: list){
                System.out.println("ReadThread: " + ele);
            }
        }
    }

    private static class WriteThread implements Runnable {
        private List<Integer> list;
        public WriteThread(List<Integer> list) {
            this.list = list;
        }

        @Override
        public void run() {
            this.list.add(9);
        }
    }

    private void test() {
        // 1. 初始化CopyOnWriteArrayList
        List<Integer> tempList = Arrays.asList(new Integer[] {1,2});
        CopyOnWriteArrayList<Integer> copyList = new CopyOnWriteArrayList<>(tempList);

        // 2. 模拟多线程对list进行读和写
//        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(new ReadThread(copyList));
        executorService.execute(new WriteThread(copyList));
        executorService.execute(new WriteThread(copyList));
        executorService.execute(new WriteThread(copyList));
        executorService.execute(new ReadThread(copyList));
        executorService.execute(new WriteThread(copyList));
        executorService.execute(new ReadThread(copyList));
        executorService.execute(new WriteThread(copyList));
        executorService.shutdown();
    }

    public static void main(String[] args) {
        new CopyOnWriteArrayListDemo().test();
    }
    
}
