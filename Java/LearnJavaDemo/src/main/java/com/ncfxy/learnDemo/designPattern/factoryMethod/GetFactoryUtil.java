package com.ncfxy.learnDemo.designPattern.factoryMethod;

public class GetFactoryUtil {

    public static Object getBean(String factoryName) {
        Class c = null;
        try {
            c = Class.forName(factoryName);
            Object obj = c.newInstance();
            return obj;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
