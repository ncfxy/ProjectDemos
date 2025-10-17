# 线程池 ThreadPool

参考: [深入理解Java之线程池](http://www.importnew.com/19011.html)

## ThreadPoolExecutor 参数

- corePoolSize:核心池的大小
- maximumPoolSize:线程池最大线程数
- keepAliveTime: 表示线程没有任务执行时最多保持多久时间会终止
- unit: 参数keepAliveTime的时间单位
- workQueue: 一个阻塞队列，用来存储等待执行的任务。ArrayBlockingQueue和PriorityBlockingQueue使用较少，一般使用LinkedBlockingQueue和Synchronous
- threadFactory: 线程工厂，主要用来创建线程
- handler: 表示当拒绝处理任务时的策略

## 线程池状态

```java
volatile int runState;
static final int RUNNING    = 0;
static final int SHUTDOWN   = 1;
static final int STOP       = 2;
static final int TERMINATED = 3;
```

## 任务的执行

- 如果当前线程池中的线程数目小于corePoolSize，则每来一个任务，就会创建一个线程去执行这个任务；
- 如果当前线程池中的线程数目>=corePoolSize，则每来一个任务，会尝试将其添加到任务缓存队列当中，若添加成功，则该任务会等待空闲线程将其取出去执行；若添加失败（一般来说是任务缓存队列已满），则会尝试创建新的线程去执行这个任务；
- 如果当前线程池中的线程数目达到maximumPoolSize，则会采取任务拒绝策略进行处理；
- 如果线程池中的线程数量大于 corePoolSize时，如果某线程空闲时间超过keepAliveTime，线程将被终止，直至线程池中的线程数目不大于corePoolSize；如果允许为核心池中的线程设置存活时间，那么核心池中的线程空闲时间超过keepAliveTime，线程也会被终止。

## 常用缓存队列

1. ArrayBlockingQueue：基于数组的先进先出队列，此队列创建时必须指定大小；
2. LinkedBlockingQueue：基于链表的先进先出队列，如果创建时没有指定此队列大小，则默认为Integer.MAX_VALUE；
3. synchronousQueue：这个队列比较特殊，它不会保存提交的任务，而是将直接新建一个线程来执行新来的任务。

## 任务拒绝策略

- ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
- ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
- ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
- ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务

- <https://www.cnblogs.com/java-stack/p/11952065.html>

## 线程池关闭方法

- ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
- ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
- ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
- ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务

## 使用Executors创建线程池

- Executors.newFixedThreadPool(n);
- Executors.newSingleThreadExecutor();
- Executors.newCachedThreadPool();
- Executors.newScheduledThreadPool();

## 一般设置线程池的大小

- N为CPU的总核心数
- CPU密集型: N + 1
- IO密集型: 2N + 1

