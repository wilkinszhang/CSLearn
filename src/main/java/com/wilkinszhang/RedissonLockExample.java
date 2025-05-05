package com.wilkinszhang;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

public class RedissonLockExample {
    public static void main(String[]args){
        Config config=new Config();
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379");
        RedissonClient redisson= Redisson.create(config);
        RLock lock=redisson.getLock("myLock");
        lock.lock(10, TimeUnit.SECONDS);
        try{
            System.out.println("get lock, do job：" + Thread.currentThread().getName());
        }finally {
            if(lock.isHeldByCurrentThread()){
                lock.unlock();
                System.out.println("unlock：" + Thread.currentThread().getName());
            }
        }
        redisson.shutdown();
    }
}
