# Java多线程

## CAS(乐观锁)(Compare and Swap)

- 在CPU级保证这个操作的原子性
- ABA问题：AtomicStampedReference/AtomicMarkableReference 加标记
- <https://www.cnblogs.com/javalyy/p/8882172.html>
- <https://www.jianshu.com/p/ae25eb3cfb5d>

## 线程间通信的方法

- volatile关键字：使用共享内存
- Object类的wait和notify
  - notify方法不释放锁
  - 使用时需要先对该对象加锁
- ReentrantLock的Condition： 类似wait和notify
- 使用CountDownLatch
- LockSupport： park,unpark
  - 不需要获取某个对象的锁
  - park,unpark保证先后，不会出现死锁
- 管道输入 / 输出流： PipedOutputStream、PipedInputStream、PipedReader、PipedWriter
- Thread.join()
- ThreadLocal
