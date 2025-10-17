package com.ncfxy.learnDemo.jvm.testOOM;

/**
 * 建立多线程导致内存溢出
 * 32位每个进程的内存限制为2GB - Xmx - MaxPermSize就是线程栈可以分配的最大内存
 *  这个程序跑起来会死机
 * VM Args: -Xss2M
 */
public class JavaVMStackOOM {

    private void dontStop() {
        while(true) {
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stackLeakByThread() {
        while(true) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    dontStop();
                }
            });
            thread.start();
        }
    }

    public static void main(String[] args) {
        JavaVMStackOOM oom = new JavaVMStackOOM();
        oom.stackLeakByThread();
    }
}
