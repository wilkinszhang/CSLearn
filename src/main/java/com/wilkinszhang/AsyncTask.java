package com.wilkinszhang;

class AsyncTask {
    // 模拟异步执行
    public void execute(Callback callback) {
        new Thread(() -> {
            try {
                System.out.println("异步任务开始执行...");
                // 模拟耗时任务
                Thread.sleep(2000);
                String result = "任务执行完成";
                System.out.println("异步任务完成");
                // 执行回调
                callback.onComplete(result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
