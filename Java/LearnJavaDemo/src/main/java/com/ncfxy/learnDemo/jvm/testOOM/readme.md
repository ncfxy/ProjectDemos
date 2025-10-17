# JVM学习

## JVM参数

1. -Xms20m      最小堆内存
1. -Xmx20m      最大堆内存
1. -XX:+HeapDumpOnOutOfMemoryError  出现内存溢出时输入堆快照dump文件
1. -Xss 虚拟机栈大小
1. -Xoss 本地方法栈大小
1. -XX:PermSize         方法区容量       Java 8 中取消
1. -XX:MaxPermSize     最大方法区容量      Java 8 中取消
1. -XX: MaxMetaspaceSize    元空间         Java 8 中新增

## 分析工具

MAT: Eclipse Memory Analyzer