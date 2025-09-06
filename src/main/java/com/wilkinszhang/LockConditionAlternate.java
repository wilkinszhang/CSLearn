package com.wilkinszhang;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//线程交替打印数字
public class LockConditionAlternate {
    private final int N;
    private int num=1;
    private final Lock lock=new ReentrantLock();
    private final Condition oddTurn=lock.newCondition();
    private final Condition evenTurn=lock.newCondition();

    public LockConditionAlternate(int N){
        this.N=N;
    }

    public void printOdd(){
        lock.lock();
        try{
            while(num<=N){
                if((num&1)==1){
                    System.out.println(num++);
                    evenTurn.signal();
                }else{
                    oddTurn.await();
                }
            }
            evenTurn.signal();
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }finally{
            lock.unlock();
        }
    }

    public void printEven(){
        lock.lock();
        try{
            while(num<=N){
                if((num&1)==0){
                    System.out.println(num++);
                    oddTurn.signal();
                }else{
                    evenTurn.await();
                }
            }
            oddTurn.signal();
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }finally{
            lock.unlock();
        }
    }

    public static void main(String[]args){
        LockConditionAlternate printer=new LockConditionAlternate(10);
        new Thread(printer::printOdd).start();
        new Thread(printer::printEven).start();
    }
}
