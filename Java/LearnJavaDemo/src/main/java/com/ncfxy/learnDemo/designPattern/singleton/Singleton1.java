package com.ncfxy.learnDemo.designPattern.singleton;

/**
 * 线程不安全
 * 懒汉模式
 */
public class Singleton1 implements SingletonInterface{
    private static Singleton1 instance;
    private OneBigObject bigObject;
    private Singleton1() {
        this.bigObject = new OneBigObject();
    }
    public static Singleton1 getInstance() {
        if(instance == null){
            instance = new Singleton1();
        }
        return instance;
    }
}
