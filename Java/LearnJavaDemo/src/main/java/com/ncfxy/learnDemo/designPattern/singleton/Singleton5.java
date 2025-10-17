package com.ncfxy.learnDemo.designPattern.singleton;

/**
 * 线程安全
 * 静态内部类
 */
public class Singleton5 implements SingletonInterface {
    private OneBigObject bigObject;
    private Singleton5() {
        this.bigObject = new OneBigObject();
    }

    public static Singleton5 getInstance() {
        return InnerSingleton5.instance;
    }

    private static class InnerSingleton5 {
        private final static Singleton5 instance = new Singleton5();
    }
}
