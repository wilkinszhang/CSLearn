package com.wilkinszhang;

import java.util.concurrent.*;

public class NestedSubmissionExample {

    public static void main(String[] args) {
        // 创建线程池：核心线程 5，最大线程 10，空闲线程存活时间 60 秒，阻塞队列大小 10，拒绝策略 CallerRunsPolicy
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                5,
                10,
                60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        // 外层任务
        Runnable outerTask = () -> {
            System.out.println("外层任务开始执行，线程：" + Thread.currentThread().getName());
            try {
                // 模拟一些耗时操作
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // 在外层任务内部再提交一个子任务
            Runnable innerTask = () -> {
                System.out.println("    内层子任务开始执行，线程：" + Thread.currentThread().getName());
            };
            executor.execute(innerTask);

            System.out.println("外层任务提交子任务完毕，线程：" + Thread.currentThread().getName());
        };

        // 提交外层任务到线程池
        executor.execute(outerTask);
        System.out.println("即将调用 shutdown 方法");
        executor.shutdown();
//        即将调用 shutdown 方法
//        外层任务开始执行，线程：pool-1-thread-1
//        外层任务提交子任务完毕，线程：pool-1-thread-1
    }
}
