package com.ncfxy.learnDemo.designPattern.singleton;

public enum Singleton6 implements SingletonInterface {
    INSTANCE;
    private OneBigObject bigObject = new OneBigObject();
}
