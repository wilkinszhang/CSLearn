package com.wilkinszhang;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;

public class BatchRequestProcessor<T> {
    private final BlockingQueue<T> queue; // 请求缓存队列
    private final int batchSize;          // 阈值：触发批量入库的请求数
    private final long timeLimitMillis;   // 阈值：超时时间，单位毫秒
    private final ExecutorService executor; // 线程池
    private final Consumer<List<T>> batchProcessor; // 批量处理逻辑

    public BatchRequestProcessor(int batchSize, long timeLimitMillis, Consumer<List<T>> batchProcessor) {
        this.queue = new LinkedBlockingQueue<>();
        this.batchSize = batchSize;
        this.timeLimitMillis = timeLimitMillis;
        this.executor = Executors.newSingleThreadExecutor();
        this.batchProcessor = batchProcessor;
        startTimerTask();
    }

    // 添加请求
    public void addRequest(T request) {
        queue.add(request);
        if (queue.size() >= batchSize) {
            processBatch();
        }
    }

    // 触发批量入库
    private void processBatch() {
        List<T> batch = new ArrayList<>();
        queue.drainTo(batch, batchSize); // 从队列中获取 batchSize 个元素
        if (!batch.isEmpty()) {
            executor.submit(() -> batchProcessor.accept(batch));
        }
    }

    // 定时任务，时间触发批量入库
    private void startTimerTask() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::processBatch, timeLimitMillis, timeLimitMillis, TimeUnit.MILLISECONDS);
    }

    public void shutdown() {
        executor.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        // 模拟入库逻辑
        Consumer<List<String>> databaseInserter = batch -> System.out.println("入库：" + batch);

        BatchRequestProcessor<String> processor = new BatchRequestProcessor<>(5, 3000, databaseInserter);

        // 模拟请求
        for (int i = 1; i <= 12; i++) {
            processor.addRequest("Request-" + i);
            Thread.sleep(500);
        }

        Thread.sleep(5000); // 等待所有任务执行
        processor.shutdown();
    }
}

