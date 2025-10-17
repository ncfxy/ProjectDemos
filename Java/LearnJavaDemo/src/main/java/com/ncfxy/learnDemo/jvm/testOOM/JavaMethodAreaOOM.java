package com.ncfxy.learnDemo.jvm.testOOM;

/**
 * VM Args: -XX:PermSize=10M -XX:MaxPermSize=10M   注：在Java8 中PermGen取消
 *  Java8 中使用MaxMetaspaceSize代替
 */
public class JavaMethodAreaOOM {
    public static void main(String[] args) {
        while(true) {
            // 需要使用CGlib的Enhancer
        }
    }

    static class OOMObject{

    }
}
