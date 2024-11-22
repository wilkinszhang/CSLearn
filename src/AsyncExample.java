// 定义一个异步任务接口
interface Callback {
    void onComplete(String result);
}

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

public class AsyncExample {
    public static void main(String[] args) {
        System.out.println("主线程开始...");

        // 创建异步任务
        AsyncTask task = new AsyncTask();
        task.execute(result -> System.out.println("回调结果: " + result));

        System.out.println("主线程继续执行...");
    }
}
