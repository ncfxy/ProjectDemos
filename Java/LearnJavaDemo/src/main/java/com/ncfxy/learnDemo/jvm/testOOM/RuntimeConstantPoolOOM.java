package com.ncfxy.learnDemo.jvm.testOOM;

import java.util.ArrayList;
import java.util.List;

/**
 * VM Args: -XX:PermSize=10M -XX:MaxPermSize=10M   注：在Java8 中PermGen取消
 *  Java8 中使用MaxMetaspaceSize代替
 */
public class RuntimeConstantPoolOOM {
    public static void main(String[] args) {
        // 使用List保持着常量池引用，避免Full GC回收常量池行为
        List<String> list = new ArrayList<>();
        // 10MB的PermSize在integer范围内足够产生OOM了
        int i = 0;
        while(true) {
            list.add(new String("longlonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglong"+(String.valueOf(i++))).intern());
            System.out.println(list.get(list.size()-1));
        }
    }
}
