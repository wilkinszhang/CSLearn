package com.wilkinszhang;

public class ThreadOOMDemo {
    public static void main(String[] args) {
        int count = 0;
        try {
            while (true) {
                // 每个线程都会阻塞，保持活跃状态，不退出
                Thread t = new Thread(() -> {
                    try {
                        Thread.sleep(Long.MAX_VALUE);
                    } catch (InterruptedException e) {
                        // 被中断也不用退出
                    }
                });
                t.start();
                count++;
                System.out.println("已创建线程数：" + count);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        //        已创建线程数：15500
        //                [3.352s][warning][os,thread] Failed to start thread "Unknown thread" - pthread_create failed (EAGAIN) for attributes: stacksize: 1024k, guardsize: 0k, detached.
        //[3.352s][warning][os,thread] Failed to start the native thread for java.lang.Thread "Thread-15500"
        //        java.lang.OutOfMemoryError: unable to create native thread: possibly out of memory or process/resource limits reached
        //        at java.base/java.lang.Thread.start0(Native Method)
        //        at java.base/java.lang.Thread.start(Thread.java:798)
        //        at com.wilkinszhang.ThreadOOMDemo.main(ThreadOOMDemo.java:16)
    }
}

