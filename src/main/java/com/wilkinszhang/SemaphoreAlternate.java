package com.wilkinszhang;

import java.util.concurrent.Semaphore;

//线程交替打印数字
public class SemaphoreAlternate {
    private final int N;
    private final Semaphore semOdd=new Semaphore(1);
    private final Semaphore semEven=new Semaphore(0);

    public SemaphoreAlternate(int N){
        this.N=N;
    }

    public void printOdd(){
        for(int i=1;i<=N;i+=2){
            try{
                semOdd.acquire();
                System.out.println(i);
                semEven.release();
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }

    public void printEven(){
        for(int i=2;i<=N;i+=2){
            try{
                semEven.acquire();
                System.out.println(i);
                semOdd.release();
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[]args){
        SemaphoreAlternate printer=new SemaphoreAlternate(10);
        new Thread(printer::printOdd).start();
        new Thread(printer::printEven).start();
    }
}
