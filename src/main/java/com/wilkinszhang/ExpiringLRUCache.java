package com.wilkinszhang;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


//带过期时间和异步扫描的LRU
public class ExpiringLRUCache<K,V> {
    private final int capacity;
    private final long ttlMillis;
    private final long scanIntervalMillis;
    private final Map<K,CacheEntry<V>> map;
    private final ScheduledExecutorService cleaner;

    private static class CacheEntry<V>{
        final V value;
        final long expiryTime;
        CacheEntry(V value,long ttl){
            this.value=value;
            this.expiryTime=System.currentTimeMillis()+ttl;
        }
        boolean isExpired(){
            return System.currentTimeMillis()>=expiryTime;
        }
    }

    public ExpiringLRUCache(int capacity,long ttlMillis,long scanIntervalMillis){
        this.capacity=capacity;
        this.ttlMillis=ttlMillis;
        this.scanIntervalMillis=scanIntervalMillis;
        this.map= Collections.synchronizedMap(new LinkedHashMap<K,CacheEntry<V>>(capacity,0.75f,true){
            @Override
            protected boolean removeEldestEntry(Map.Entry<K,CacheEntry<V>> eldest){
                return size()>ExpiringLRUCache.this.capacity;
            }
        });
        this.cleaner= Executors.newSingleThreadScheduledExecutor();
        startCleaner();
    }

    private void startCleaner(){
        cleaner.scheduleAtFixedRate(()->{
            long now=System.currentTimeMillis();
            synchronized (map){
                Iterator<Map.Entry<K,CacheEntry<V>>> it=map.entrySet().iterator();
                while (it.hasNext()){
                    Map.Entry<K,CacheEntry<V>> e=it.next();
                    if(e.getValue().isExpired()){
                        it.remove();
                    }
                }
            }
        },scanIntervalMillis,scanIntervalMillis, TimeUnit.MILLISECONDS);
    }

    public V get(K key){
        CacheEntry<V> entry=map.get(key);
        if(entry==null||entry.isExpired()){
            if(entry!=null){
                map.remove(key);
            }
            return null;
        }
        return entry.value;
    }

    public void put(K key,V value){
        map.put(key,new CacheEntry<>(value,ttlMillis));
    }

    public int size(){
        return map.size();
    }

    public void shutdown(){
        cleaner.shutdown();
    }

    public static void main(String [] args)throws InterruptedException{
        ExpiringLRUCache<String,String>cache=new ExpiringLRUCache<>(3,2000,1000);
        cache.put("a","A");
        cache.put("b","B");
        Thread.sleep(1500);
        cache.put("c","C");
        System.out.println(cache.get("a"));
        Thread.sleep(1000);
        System.out.println(cache.get("a"));
        cache.put("d","D");
        System.out.println(cache.map.keySet());
        cache.shutdown();
    }
}
