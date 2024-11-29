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
