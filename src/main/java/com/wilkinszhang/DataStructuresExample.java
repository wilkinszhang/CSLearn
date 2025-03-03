package com.wilkinszhang;

import java.util.*;

public class DataStructuresExample {
    public static void main(String[] args) {
        // ArrayList (Vector in C++)
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(2);
        arrayList.add(1);
        System.out.println("arrayList is empty : " + arrayList.isEmpty());
        Collections.sort(arrayList);
        if(!arrayList.isEmpty()){
            arrayList.remove(arrayList.size()-1);
        }
        for(int i=0;i<arrayList.size();++i){
            System.out.println(arrayList.get(i));
        }
        System.out.println("ArrayList: " + arrayList);

        // Stack
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        System.out.println("Stack: " + stack);
        System.out.println(stack.peek());
        System.out.println("Stack pop: " + stack.pop());

        // HashSet (unordered_set in C++)
        HashSet<Integer> hashSet = new HashSet<>();
        hashSet.add(1);
        hashSet.add(2);
        if(hashSet.contains(1)){
            System.out.println("HashSet contains 1" );
        }
        System.out.println("HashSet: " + hashSet);

        // TreeSet (set in C++)
        TreeSet<Integer> treeSet = new TreeSet<>();
        treeSet.add(3);
        treeSet.add(1);
        treeSet.add(2);
        System.out.println("TreeSet: " + treeSet);

        // Queue
        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        queue.add(2);
        System.out.println("Queue: " + queue);
        System.out.println("Queue poll: " + queue.poll());//相当于pop

        // HashMap (unordered_map in C++)
        HashMap<Integer, String> hashMap = new HashMap<>();
        hashMap.put(1, "one");
        hashMap.put(2, "two");
        System.out.println("hashMap contains key 3 : "+hashMap.containsKey(3));
        System.out.println(hashMap.get(1));
        System.out.println("HashMap: " + hashMap);

        // TreeMap (map in C++)
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        treeMap.put(3, "three");
        treeMap.put(1, "one");
        treeMap.put(2, "two");
        System.out.println("TreeMap: " + treeMap);

        // PriorityQueue
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(3);
        priorityQueue.add(1);
        priorityQueue.add(2);
        System.out.println("PriorityQueue: " + priorityQueue);
        System.out.println("PriorityQueue poll: " + priorityQueue.poll());
    }
}
