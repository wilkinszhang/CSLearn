package com.wilkinszhang;

public class AsyncExample {
    public static void main(String[] args) {
        System.out.println("主线程开始...");

        // 创建异步任务
        AsyncTask task = new AsyncTask();
        task.execute(result -> System.out.println("回调结果: " + result));

        System.out.println("主线程继续执行...");
    }
}
