package com.ncfxy.learnDemo.multiThread.lock;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class ForkAndJoinDemo  extends RecursiveTask<Long> {

    public static void main(String[] args) {
        ForkAndJoinDemo demo = new ForkAndJoinDemo();
//        demo.noFork();
        demo.withFork();
    }

    public void noFork() {
        Long sum = 0L;
        Long maxSize = 1000000000L;
        long startTime = System.currentTimeMillis();
        for(Long i = 1L; i <= maxSize;i++) {
            sum += i;
        }
        System.out.println("result: " + sum);
        long endTime = System.currentTimeMillis();
        System.out.println("costTime: " + (endTime - startTime));
    }

    public void withFork() {
        Long startNum = 0L;
        Long endNum = 1000000000L;

        long startTime = System.currentTimeMillis();

        ForkAndJoinDemo countTask = new ForkAndJoinDemo(startNum, endNum);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Future<Long> result = forkJoinPool.submit(countTask);
        try{
            System.out.println("result: " + result.get());
            long endTime = System.currentTimeMillis();
            System.out.println("costTime: " + (endTime - startTime));
        } catch (InterruptedException e){
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    Long maxCountRange = 1000000L;
    Long startNum, endNum;

    public ForkAndJoinDemo() {

    }

    public ForkAndJoinDemo(Long startNum, Long endNum) {
        this.startNum = startNum;
        this.endNum = endNum;
    }

    @Override
    protected Long compute() {
        long range = endNum - startNum;
        long sum = 0;
        if(range >= maxCountRange) {
            //如果这次计算的范围大于了计算时规定的最大范围，则进行拆分
            //每次拆分时，都拆分成原来任务范围的一半
            //如1-10,则拆分为1-5,6-10
            Long middle = (startNum + endNum) / 2;
            ForkAndJoinDemo subTask1 = new ForkAndJoinDemo(startNum, middle);
            ForkAndJoinDemo subTask2 = new ForkAndJoinDemo(middle + 1, endNum);

            // 拆分后， 执行fork
            subTask1.fork();
            subTask2.fork();

            sum += subTask1.join();
            sum += subTask2.join();
        } else {
            for(; startNum <= endNum;startNum++){
                sum += startNum;
            }
        }
        return sum;
    }
}
