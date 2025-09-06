package com.wilkinszhang;

public class HighCpuSimulator {

    public static void main(String[] args) {
        int numberOfThreads = 4; // 你可以根据你的CPU核心数调整这个值

        for (int i = 0; i < numberOfThreads; i++) {
            Thread worker = new Thread(new CpuIntensiveTask(), "CpuWorker-" + i);
            worker.start();
        }

        // 主线程保持运行，以防程序退出
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

