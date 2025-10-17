package com.ncfxy.learnDemo.jvm.gc;

/**
 * 参数中加入 -XX:+PrintGCDetails 打印gc日志
 * 日志中发现循环引用的两个对象被回收，说明没有使用引用计数的方法
 */
public class ReferenceCountingGC {

    public Object instance = null;

    private static final int _1MB = 1024 * 1024;

    /**
     * 用来占一点内存，以便在GC日志中看清楚是否被回收过
     */
    private byte[] bigSize = new byte[2 * _1MB];

    public static void testGC() {
        ReferenceCountingGC objA = new ReferenceCountingGC();
        ReferenceCountingGC objB = new ReferenceCountingGC();
        objA.instance = objB;
        objB.instance = objA;

        objA = null;
        objB = null;

        // 进行观察，看objA和objB是否被回收。
        System.gc();
    }

    public static void main(String[] args) {
        testGC();
    }
}
