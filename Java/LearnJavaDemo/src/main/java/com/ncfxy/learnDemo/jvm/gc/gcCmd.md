# GC相关命令

## JVM的GC日志的主要参数包括如下几个：

-XX:+PrintGC 输出GC日志

-XX:+PrintGCDetails 输出GC的详细日志

-XX:+PrintGCTimeStamps 输出GC的时间戳（以基准时间的形式）

-XX:+PrintGCDateStamps 输出GC的时间戳（以日期的形式，如 2013-05-04T21:53:59.234+0800）

-XX:+PrintHeapAtGC 在进行GC的前后打印出堆的信息

-Xloggc:../logs/gc.log 日志文件的输出路径

## 判断对象是否存活

- 引用记数法，简单，但是无法判断循环引用  
- 可达性分析，主流商用

## 内存分析

### JVM自带内存分析工具

- jps: 列出正在运行的JVM进程
- jstat: 虚拟机运行时状态信息
- jmap: 用于生成heap dump文件
- jhat: 与jmap搭配使用，用来分析jmap生成的dump
- jstack: 用于生成java虚拟机当前时刻的线程快照
- jinfo: 实时查看和调整虚拟机运行参数
- jcmd: 1.7之后提供的一个多功能的命令行工具 <https://docs.oracle.com/javase/8/docs/technotes/guides/troubleshoot/tooldescr006.html>
- 可视化
  - jconsole
  - jvisualvm
- Related URLs:
  - [java高分局之jstat命令使用](https://blog.csdn.net/maosijunzi/article/details/46049117)
  - [JVM自带内存分析工具详解](https://blog.csdn.net/wangxiaotongfan/article/details/82560739)
