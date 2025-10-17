package com.ncfxy.learnDemo.designPattern.singleton;

/**
 * 线程安全， 但效率很低
 * 懒汉模式
 */
public class Singleton3 implements SingletonInterface{
    private static Singleton3 instance;
    private OneBigObject bigObject;
    private Singleton3() {
        this.bigObject = new OneBigObject();
    }
    public static synchronized Singleton3 getInstance() {
        if(instance == null){
            instance = new Singleton3();
        }
        return instance;
    }
}
