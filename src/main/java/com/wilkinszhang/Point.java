package com.wilkinszhang;

import java.util.concurrent.locks.StampedLock;

// StampedLock
public class Point {
    private int x,y;
    final StampedLock sl=new StampedLock();

    double distanceFromOrigin(){
        // 乐观读
        long stamp=sl.tryOptimisticRead();
        int curX=x,curY=y;
        if(!sl.validate(stamp)){
            // 悲观读锁
            stamp=sl.readLock();
            try{
                curX=x;
                curY=y;
            }finally {
                sl.unlockRead(stamp);
           }
        }
        return Math.sqrt(
                curX*curX+curY*curY
        );
    }
}
