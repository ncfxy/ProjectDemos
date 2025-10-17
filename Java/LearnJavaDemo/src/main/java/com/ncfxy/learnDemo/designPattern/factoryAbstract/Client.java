package com.ncfxy.learnDemo.designPattern.factoryAbstract;

public class Client {
    public static void main(String[] args) {
        SkinFactory factory = new SummerSkinFactory();
        Button bt = factory.createButton();
        TextField tf = factory.createTextField();
        ComboBox cb = factory.createComboBox();
        bt.display();
        tf.display();
        cb.display();
    }
}
