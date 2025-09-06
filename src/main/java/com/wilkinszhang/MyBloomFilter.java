package com.wilkinszhang;

import java.util.BitSet;

public class MyBloomFilter<T>{
    private final BitSet bits;
    private final int m;
    private final int k;
    private final int n;
    public MyBloomFilter(int n, double falsePositiveRate){
        this.n=n;
        this.m=(int)Math.ceil(-n*Math.log(falsePositiveRate)/Math.log(2)*Math.log(2));
        this.k=(int)Math.ceil((m/(double)n)*Math.log(2));
        this.bits=new BitSet(m);
    }
    public void add(T item){
        int[]hashes=hash(item);
        for(int idx:hashes){
            bits.set(idx,true);
        }
    }
    public boolean contain(T item){
        int[]hashes=hash(item);
        for(int idx:hashes){
            if(!bits.get(idx)){
                return false;
            }
        }
        return true;
    }
    private int[] hash(T item){
        int[] res=new int[k];
        int h1=item.hashCode();
        int h2=Integer.rotateLeft(h1,16);
        for(int i=0;i<k;i++){
            long combined=(long)h1+i*h2;
            res[i]=(int)((combined&0x7FFFFFFF)%m);
        }
        return res;
    }
}