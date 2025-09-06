package com.wilkinszhang;

import java.util.HashMap;
import java.util.Map;

// LRU缓存加过期时间
public class LRUCacheWithTTL {
    private static class Node{
        int key;
        int value;
        long expireTime;
        Node prev,next;
        Node(int k,int v,long exp){
            this.key=k;
            this.value=v;
            this.expireTime=exp;
        }
    }

    private final int capacity;
    private final Map<Integer,Node>map;
    private final Node head,tail;

    public LRUCacheWithTTL(int capacity){
        this.capacity=capacity;
        this.map=new HashMap<>(capacity);
        head=new Node(0,0,0);
        tail=new Node(0,0,0);
        head.next=tail;
        tail.prev=head;
    }

    public int get(int key){
        Node node=map.get(key);
        if(node==null)return -1;
        if(System.currentTimeMillis()>node.expireTime){
            removeNode(node);
            map.remove(node);
            return -1;
        }
        moveToHead(node);
        return node.value;
    }

    public void put(int key,int value,long ttlMillis){
        long now=System.currentTimeMillis();
        long expTime=now+ttlMillis;
        Node node=map.get(key);
        if(node!=null){
            node.value=value;
            node.expireTime=expTime;
            moveToHead(node);
        }else{
            cleanupExpiredLRU(now);

            node=new Node(key,value,expTime);
            map.put(key,node);
            addToHead(node);
            if(map.size()>capacity){
                Node lru=tail.prev;
                removeNode(lru);
                map.remove(lru.key);
            }
        }
    }

    private void cleanupExpiredLRU(long now){
        Node cur=tail.prev;
        while(cur!=head && now>cur.expireTime){
            Node prev=cur.prev;
            removeNode(cur);
            map.remove(cur.key);
            cur=prev;
        }
    }

    private void addToHead(Node node){
        node.next=head.next;
        node.prev=head;
        head.next.prev=node;
        head.next=node;
    }

    private void removeNode(Node node){
        node.prev.next=node.next;
        node.next.prev=node.prev;
    }

    private void moveToHead(Node node){
        removeNode(node);
        addToHead(node);
    }
}
