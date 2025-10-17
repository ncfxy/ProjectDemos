package com.ncfxy.learnDemo.designPattern.singleton;

/**
 * 线程安全, 没有lazy load的效果
 * 饿汉模式
 */
public class Singleton2  implements SingletonInterface{
    private static Singleton2 instance = new Singleton2();
    private OneBigObject bigObject;
    private Singleton2() {
        this.bigObject = new OneBigObject();
    }
    public static synchronized Singleton2 getInstance(){
        return instance;
    }
}
