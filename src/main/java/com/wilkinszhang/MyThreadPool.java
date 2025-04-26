package com.wilkinszhang;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MyThreadPool {
    BlockingQueue<Runnable> workQueue;
    List<WorkerThread> threads=new ArrayList<>();
    MyThreadPool(int poolSize,BlockingQueue<Runnable> workQueue){
        this.workQueue=workQueue;
        for(int i=0;i<poolSize;i++){
            WorkerThread work=new WorkerThread();
            work.start();
            threads.add(work);
        }
    }
    public void execute(Runnable command) throws InterruptedException {
        workQueue.put(command);
    }
    class WorkerThread extends Thread{
        public void run(){
            while(true){
                Runnable task= null;
                try {
                    task = workQueue.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                task.run();
            }
        }
    }
    public static void main(String[]args) throws InterruptedException {
        BlockingQueue<Runnable>workQueue=new LinkedBlockingQueue<>(2);
        MyThreadPool pool=new MyThreadPool(10,workQueue);
        pool.execute(()->{
            System.out.println("hello");
        });
    }
}
