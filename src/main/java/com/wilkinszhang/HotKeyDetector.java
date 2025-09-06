package com.wilkinszhang;
import java.util.*;

//给定一个时间窗口M和阈值N，判断M时间内的keyData是否是热点数据。
public class HotKeyDetector {
    private final long timeWindow; // 时间窗口 (毫秒)
    private final int threshold;   // 热点阈值 (访问次数)
    private final Map<String, Deque<Long>> dataMap; // 记录 keyData 访问时间

    public HotKeyDetector(int timeWindowSeconds, int threshold) {
        this.timeWindow = timeWindowSeconds * 1000L; // 转换为毫秒
        this.threshold = threshold;
        this.dataMap = new HashMap<>();
    }

    public boolean access(String key) {
        long now = System.currentTimeMillis(); // 获取当前时间戳 (毫秒)

        dataMap.putIfAbsent(key, new LinkedList<>());
        Deque<Long> queue = dataMap.get(key);
        queue.addLast(now);

        // 移除窗口外的访问记录
        while (!queue.isEmpty() && queue.peekFirst() < now - timeWindow) {
            queue.pollFirst();
        }

        // 判断是否达到热点阈值
        return queue.size() >= threshold;
    }

    public static void main(String[] args) throws InterruptedException {
        HotKeyDetector detector = new HotKeyDetector(60, 100); // 60秒窗口, 阈值100

        // 模拟访问 105 次
        for (int i = 0; i < 105; i++) {
            if (detector.access("keyData")) {
                System.out.println("keyData 是热点数据");
            }
            Thread.sleep(10); // 模拟时间间隔
        }
    }
}
