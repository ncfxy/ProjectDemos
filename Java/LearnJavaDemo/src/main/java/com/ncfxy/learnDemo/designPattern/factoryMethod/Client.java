package com.ncfxy.learnDemo.designPattern.factoryMethod;

public class Client {
    public static void main(String[] args) {
        String loggerName = "com.ncfxy.designPattern.factoryMethod.FileLoggerFactory";
        LoggerFactory factory = (LoggerFactory)GetFactoryUtil.getBean(loggerName);
        Logger logger = factory.createLogger();
        logger.writeLog();
    }
}
