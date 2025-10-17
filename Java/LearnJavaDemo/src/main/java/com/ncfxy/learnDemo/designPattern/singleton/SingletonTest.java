package com.ncfxy.learnDemo.designPattern.singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class SingletonTest {

    private SingletonInterface singleton;
    private Map<Long, SingletonInterface> map = new ConcurrentHashMap<>();

    private Supplier<SingletonInterface> supplier1 = new Supplier() {
        @Override
        public SingletonInterface get() {
            return Singleton1.getInstance();
        }
    };

    private Supplier<SingletonInterface> supplier2 = new Supplier() {
        @Override
        public SingletonInterface get() {
            return Singleton2.getInstance();
        }
    };

    private Supplier<SingletonInterface> supplier3 = new Supplier() {
        @Override
        public SingletonInterface get() {
            return Singleton3.getInstance();
        }
    };

    private Supplier<SingletonInterface> supplier4 = new Supplier() {
        @Override
        public SingletonInterface get() {
            return Singleton4.getInstance();
        }
    };

    private Supplier<SingletonInterface> supplier5 = new Supplier() {
        @Override
        public SingletonInterface get() {
            return Singleton5.getInstance();
        }
    };

    private Supplier<SingletonInterface> supplier6 = new Supplier() {
        @Override
        public SingletonInterface get() {
            return Singleton6.INSTANCE;
        }
    };

    public static void main(String[] args) {
        SingletonTest a = new SingletonTest();
        a.testAll();
    }

    public void testAll() {
//        System.out.println("Implement 1: ");
//        testOne(supplier1);
        System.out.println("Implement 2: ");
        testOne(supplier2);
//        System.out.println("Implement 3: ");
//        testOne(supplier3);
//        System.out.println("Implement 4: ");
//        testOne(supplier4);
//        System.out.println("Implement 5: ");
//        testOne(supplier5);
//        System.out.println("Implement 6: ");
//        testOne(supplier6);
    }

    /**
     * 获取单例的方法
     * @param supplier
     */
    private void testOne(Supplier<SingletonInterface> supplier){
        List<Thread> threadList = new ArrayList<>();
        for(int i = 0;i < 100;i++){
            threadList.add(new Thread(new GetInstanceThread(map, supplier)));
        }
        long startTime = System.currentTimeMillis();
        for(int i = 0;i < 100;i++){
            threadList.get(i).start();
        }
        for(int i = 0;i < 100;i++){
            try {
                threadList.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        singleton = supplier.get();
        map.entrySet().stream().forEach(entry ->{
            if(!entry.getValue().equals(singleton)){
                System.out.println("获取到的对象不是同一对象");
            }
        });
        long endTime = System.currentTimeMillis();
        System.out.println("cost time: "+(endTime - startTime));
    }



    private static class GetInstanceThread implements Runnable{
        private Map<Long, SingletonInterface> map;
        private Supplier<SingletonInterface> supplier;
        public GetInstanceThread(Map<Long, SingletonInterface> originalSingleton,Supplier<SingletonInterface> supplier ){
            this.map = originalSingleton;
            this.supplier = supplier;
        }
        public void run() {
            this.map.put(Thread.currentThread().getId(), supplier.get());
        }
    }
}
