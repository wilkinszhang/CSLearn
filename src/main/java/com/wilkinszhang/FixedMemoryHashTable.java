package com.wilkinszhang;

/**
 * FixedMemoryHashTable：在固定内存大小固定为1M下的 int -> int 哈希表实现。
 * 使用开放寻址 + 线性探测方式，支持自动扩容。
 */
public class FixedMemoryHashTable {
    private int[]keys;
    private int[]values;
    private int size=0;
    private int capacity;
    private int threshold;
    private final float loadFactor;
    private final int salt;

    private static final int EMPTY=Integer.MIN_VALUE;

    public FixedMemoryHashTable(int initCapacity,float loadFactor){
        this.capacity=initCapacity;
        this.loadFactor=loadFactor;
        this.threshold=(int)(capacity*loadFactor);
        this.keys=new int[capacity];
        this.values=new int[capacity];
        this.salt=new java.util.Random().nextInt();
        for(int i=0;i<capacity;i++){
            keys[i]=EMPTY;
        }
    }

    public void put(int key,int value){
        if(size+1>threshold){
            resize();
        }
        int h=hash(key);
        int idx=h%capacity;
        while (keys[idx]!=EMPTY){
            if(keys[idx]==key){
                values[idx]=value;
                return;
            }
            idx=(idx+1)%capacity;
        }
        keys[idx]=key;
        values[idx]=value;
        size++;
    }

    public Integer get(int key){
        int h=hash(key);
        int idx=h%capacity;
        while(keys[idx]!=EMPTY){
            if(keys[idx]==key){
                return values[idx];
            }
            idx=(idx+1)%capacity;
        }
        return null;
    }

    public Integer delete(int key){
        int h=hash(key);
        int idx=h%capacity;
        while (keys[idx]!=EMPTY){
            if(keys[idx]==key){
                int oldVal=values[idx];
                keys[idx]=EMPTY;
                values[idx]=0;
                size--;
                rehashFrom(idx);
                return oldVal;
            }
            idx=(idx+1)%capacity;
        }
        return null;
    }

    private int hash(int key){
        return (key^salt) & 0x7fffffff;
    }

    private void resize(){
        int newCapacity=capacity<<1;
        int []oldKeys=keys;
        int []oldValues=values;
        keys=new int[newCapacity];
        values=new int[newCapacity];
        for(int i=0;i<newCapacity;i++){
            keys[i]=EMPTY;
        }

        int oldCap=capacity;
        capacity=newCapacity;
        threshold=(int)(capacity*loadFactor);
        size=0;

        for(int i=0;i<oldCap;i++){
            if(oldKeys[i]!=EMPTY){
                put(oldKeys[i],oldValues[i]);
            }
        }
    }

    private void rehashFrom(int i){
        i=(i+1)%capacity;
        while(keys[i]!=EMPTY){
            int k=keys[i];
            int v=values[i];
            keys[i]=EMPTY;
            values[i]=0;
            size--;
            put(k,v);
            i=(i+1)%capacity;
        }
    }
}
