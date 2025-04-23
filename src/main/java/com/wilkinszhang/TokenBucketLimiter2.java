package com.wilkinszhang;

import java.util.concurrent.locks.ReentrantLock;

public class TokenBucketLimiter2{
    private final long capacity;
    private final long refillIntervalNanos;
    private double tokens;
    private long lastRefillTimestamp;
    private final ReentrantLock lock=new ReentrantLock();

    public TokenBucketLimiter2(double rate,long capacity){
        this.capacity=capacity;
        this.tokens=tokens;
        this.refillIntervalNanos=(long)(1_000_000_000L/rate);
        this.lastRefillTimestamp=System.nanoTime();
    }

    public boolean tryAcquire(){
        refill();
        lock.lock();
        try{
            if(tokens>=1){
                tokens-=1;
                return true;
            }
            return false;
        }finally{
            lock.unlock();
        }
    }

    private void refill(){
        long now=System.nanoTime();
        long delta=now-lastRefillTimestamp;
        long newTokens=delta/refillIntervalNanos;
        if(newTokens>0){
            lock.lock();
            try{
                tokens=Math.min(capacity,tokens+newTokens);
                lastRefillTimestamp+=newTokens*refillIntervalNanos;
            }finally{
                lock.unlock();
            }
        }
    }
}
