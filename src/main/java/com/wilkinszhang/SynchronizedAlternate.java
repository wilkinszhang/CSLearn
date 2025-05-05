package com.wilkinszhang;

//线程交替打印数字
public class SynchronizedAlternate {
    private final int N;
    private int num=1;
    private final Object lock=new Object();

    public SynchronizedAlternate(int N){
        this.N=N;
    }

    public void printOdd(){
        synchronized(lock){
            while(num<=N){
                if((num&1)==1){
                    System.out.println(num++);
                    lock.notify();
                }else{
                    try{
                        lock.wait();
                    }catch(InterruptedException e){
                        Thread.currentThread().interrupt();
                    }
                }
            }
            lock.notify();
        }
    }

    public void printEven(){
        synchronized(lock){
            while(num<=N){
                if((num&1)==0){
                    System.out.println(num++);
                    lock.notify();
                }else{
                    try{
                        lock.wait();
                    }catch(InterruptedException e){
                        Thread.currentThread().interrupt();
                    }
                }
            }
            lock.notify();
        }
    }

    public static void main(String []args){
        SynchronizedAlternate printer=new SynchronizedAlternate(10);
        Thread t1=new Thread(printer::printOdd);
        Thread t2=new Thread(printer::printEven);
        t1.start();
        t2.start();
    }
}
