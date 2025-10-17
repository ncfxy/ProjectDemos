package com.ncfxy.learnDemo.designPattern.factorySimple;

public class Factory {

    public static Product getProduct(String arg) {
        Product product = null;
        if(arg.equalsIgnoreCase("A")){
            product = new ConcreteProductA();
        }else if(arg.equalsIgnoreCase("B")) {
            product = new ConcreteProductB();
        }
        return product;
    }
}
