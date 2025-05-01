package com.wilkinszhang;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


//带过期时间和异步扫描的LRU
public class ExpiringLRUCache {
    private final int capacity;
    private final long ttlMillis;
    private final long scanIntervalMillis;
    private final Map<Integer, CacheEntry> map;
    private final ScheduledExecutorService cleaner;

    private static class CacheEntry {
        final int value;
        final long expiryTime;
        CacheEntry(int value, long ttl) {
            this.value = value;
            this.expiryTime = System.currentTimeMillis() + ttl;
        }
        boolean isExpired() {
            return System.currentTimeMillis() >= expiryTime;
        }
    }

    public ExpiringLRUCache(int capacity, long ttlMillis, long scanIntervalMillis) {
        this.capacity = capacity;
        this.ttlMillis = ttlMillis;
        this.scanIntervalMillis = scanIntervalMillis;
        this.map = new LinkedHashMap<Integer, CacheEntry>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, CacheEntry> eldest) {
                return size() > ExpiringLRUCache.this.capacity;
            }
        };
        this.cleaner = Executors.newSingleThreadScheduledExecutor();
        startCleaner();
    }

    private void startCleaner() {
        cleaner.scheduleAtFixedRate(() -> {
            synchronized (map) {
                Iterator<Map.Entry<Integer, CacheEntry>> it = map.entrySet().iterator();
                while (it.hasNext()) {
                    if (it.next().getValue().isExpired()) {
                        it.remove();
                    }
                }
            }
        }, scanIntervalMillis, scanIntervalMillis, TimeUnit.MILLISECONDS);
    }

    public Integer get(int key) {
        CacheEntry entry;
        synchronized (map) {
            entry = map.get(key);
        }
        if (entry == null || entry.isExpired()) {
            if (entry != null) {
                synchronized (map) {
                    map.remove(key);
                }
            }
            return null;
        }
        return entry.value;
    }

    public void put(int key, int value) {
        CacheEntry entry = new CacheEntry(value, ttlMillis);
        synchronized (map) {
            map.put(key, entry);
        }
    }

    public int size() {
        synchronized (map) {
            return map.size();
        }
    }

    public void shutdown() {
        cleaner.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        ExpiringLRUCache cache = new ExpiringLRUCache(3, 2000, 1000);
        cache.put(1, 100);
        cache.put(2, 200);
        Thread.sleep(1500);
        cache.put(3, 300);
        System.out.println("get(1) = " + cache.get(1));  // 还没过期，应输出 100
        Thread.sleep(1000);
        System.out.println("get(1) = " + cache.get(1));  // 已过期，输出 null
        cache.put(4, 400);
        System.out.println("当前 key 集合: " + cache.map.keySet()); // LRU 驱逐最老未被访问的
        cache.shutdown();
    }
}
