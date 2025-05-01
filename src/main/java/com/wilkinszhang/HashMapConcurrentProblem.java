package com.wilkinszhang;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HashMapConcurrentProblem {
    public static void concurrentPutDataLoss()throws InterruptedException{
        final Map<Integer,String>map=new HashMap<>(2);
        Runnable task=()->{
            for(int i=0;i<10_000;i++){
                map.put(i,Thread.currentThread().getName()+"-"+i);
            }
        };
        Thread t1=new Thread(task,"T1");
        Thread t2=new Thread(task,"T2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(map.size());
    }


    static Map<Integer,String>visMap=new HashMap<>();
    public static void visibility()throws InterruptedException{
        Thread writer=new Thread(()->{
            try{Thread.sleep(100);}catch (InterruptedException e){}
            visMap.put(1,"One");
            System.out.println("writer: put(1,One)");
        });
        Thread reader=new Thread(()->{
            while(true){
                String v=visMap.get(1);
                if(v!=null){
                    System.out.println("reader: get 1 : "+v);
                    break;
                }else {
                    System.out.println("can not see it");
                }
            }
        });
        reader.start();
        writer.start();
        writer.join();
        Thread.sleep(1000);
        System.out.println("main exit");
        System.exit(0);
    }

    public static void iterationInconsistency()throws InterruptedException{
        final Map<Integer,Integer>iMap=new HashMap<>();
        for(int i=0;i<10;i++){
            iMap.put(i,i);
        }
        Thread iterThread=new Thread(()->{
            try {
                Iterator<Map.Entry<Integer,Integer>>it=iMap.entrySet().iterator();
                while (it.hasNext()){
                    Map.Entry<Integer,Integer> e=it.next();
                    System.out.println("Iter: "+e.getKey());
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ConcurrentModificationException cme){
                System.out.println("iter: caught CME");
            } catch (NullPointerException npe){
                System.out.println("iter: caught NPE,链表结构被破坏");
            }
        });
        Thread writeThread=new Thread(()->{
           for(int i=10;i<20;i++){
               iMap.put(i,i);
               System.out.println("write: put "+i);
               try{Thread.sleep(30);}catch (InterruptedException ignore){}
           }
        });
        iterThread.start();
        writeThread.start();
        iterThread.join();
        writeThread.join();
        System.out.println("Done");
    }

    public static void main(String[]args) throws InterruptedException {
        concurrentPutDataLoss();
//        visibility();
//        iterationInconsistency();
    }
}
