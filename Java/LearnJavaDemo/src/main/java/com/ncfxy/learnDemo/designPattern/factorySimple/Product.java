package com.ncfxy.learnDemo.designPattern.factorySimple;

public abstract class Product {

    //所有产品类的公共业务方法
    public void methodSame() {
        //公共方法的实现
    }

    // 声明抽象业务方法
    public abstract void methodDiff();
}
