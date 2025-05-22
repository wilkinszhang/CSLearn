package com.wilkinszhang;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*把一个 long 类型的
counter，从 0 自增到 5 亿。一种方式是没有任何锁，另外一个方式是每次自增的时候都
要去取一个锁。*/
public class LockBenchmark {
    public static void runIncrement(){
        long counter=0;
        long max=5_00_000_000L;
        long start=System.currentTimeMillis();
        while(counter<max){
            counter++;
        }
        long end=System.currentTimeMillis();
        System.out.println(end-start);
    }

    public static void runIncrementWithLock(){
        Lock lock=new ReentrantLock();
        long counter=0;
        long max=5_00_000_000L;
        long start=System.currentTimeMillis();
        while(counter<max){
            if(lock.tryLock()){
                counter++;
                lock.unlock();
            }
        }
        long end=System.currentTimeMillis();
        System.out.println(end-start);
    }

    public static void main(String[]args){
        runIncrement();
        runIncrementWithLock();
    }
}
