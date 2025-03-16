package com.wilkinszhang;

public class SyncExample {
    public synchronized void method1() {
        System.out.println("Method 1 starts");
        try {
            Thread.sleep(5000); // 让这个方法休眠5秒
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 处理中断
            System.out.println("Thread interrupted during sleep");
        }
        System.out.println("Method 1 ends");
    }

    public synchronized void method2() {
        System.out.println("Method 2 starts and ends");
    }

    public static void main(String[] args) {
        SyncExample obj = new SyncExample();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                obj.method1();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                obj.method2();
            }
        });

        t1.start();
        try {
            Thread.sleep(1000); // 确保t1先运行
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        t2.start();
    }
}
