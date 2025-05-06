package com.wilkinszhang;

class CpuIntensiveTask implements Runnable {

    @Override
    public void run() {
        while (true) {
            // 执行一些计算密集型任务
            double result = 0;
            for (int i = 0; i < 1000000; i++) {
                result += Math.sqrt(i);
            }

            // 可选：打印结果（会降低CPU使用率，因此建议注释掉）
            // System.out.println(Thread.currentThread().getName() + " result: " + result);
        }
    }
}
