package com.ncfxy.learnDemo.jvm.testOOM;

/**
 * VM Args: -Xss128k
 * Java VM Stack Over Flow Error
 */
public class JavaVMStackSOF {
    private int stackLength = 1;

    public void  stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {
        JavaVMStackSOF sof = new JavaVMStackSOF();
        try {
            sof.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length: " + sof.stackLength);
            throw e;
        }
    }
}
