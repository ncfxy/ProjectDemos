package com.ncfxy.learnDemo.designPattern.singleton;

/**
 * 线程安全，双重校验
 */
public class Singleton4 implements SingletonInterface {
    private static Singleton4 instance;
    private OneBigObject bigObject;
    private Singleton4() {
        this.bigObject = new OneBigObject();
    }
    public static Singleton4 getInstance() {
        if(instance == null) {
            synchronized (Singleton4.class) {
                if(instance == null) {
                    instance = new Singleton4();
                }
            }
        }
        return instance;
    }
}
