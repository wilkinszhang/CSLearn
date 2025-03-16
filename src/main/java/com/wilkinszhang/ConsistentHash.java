package com.wilkinszhang;

import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHash<T> {
    private final HashFunction hashFunction;
    private final int numberOfReplicas;
    private final SortedMap<Integer, T> circle = new TreeMap<>();

    public ConsistentHash(HashFunction hashFunction, int numberOfReplicas) {
        this.hashFunction = hashFunction;
        this.numberOfReplicas = numberOfReplicas;
    }

    public void add(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.put(hashFunction.hash(node.toString() + i), node);
        }
    }

    public void remove(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.remove(hashFunction.hash(node.toString() + i));
        }
    }

    public T get(Object key) {
        if (circle.isEmpty()) {
            return null;
        }
        int hash = hashFunction.hash(key);
        if (!circle.containsKey(hash)) {
            // 获取比hash大的最小的key
            SortedMap<Integer, T> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }

    public static void main(String args[]) {
        // 创建哈希函数和一致性哈希实例
        HashFunction hashFunction = new Murmur3HashFunction();
        ConsistentHash<String> consistentHash = new ConsistentHash<>(hashFunction, 3);

        // 添加节点
        consistentHash.add("Node1");
        consistentHash.add("Node2");
        consistentHash.add("Node3");

        // 测试键的映射
        String[] testKeys = {"key1", "key2", "key3", "user123", "data456"};

        System.out.println("初始节点分配:");
        for (String key : testKeys) {
            String node = consistentHash.get(key);
            System.out.println("键 '" + key + "' 被分配到节点: " + node);
        }

        // 移除一个节点并重新测试
        consistentHash.remove("Node2");
        System.out.println("\n移除 Node2 后的节点分配:");
        for (String key : testKeys) {
            String node = consistentHash.get(key);
            System.out.println("键 '" + key + "' 被分配到节点: " + node);
        }
    }
}