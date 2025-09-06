package com.wilkinszhang;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class DirectBufferOOMDemo {
    public static void main(String[] args) {
        // 保存分配的直接缓冲区，防止被垃圾回收
        List<ByteBuffer> buffers = new ArrayList<>();
        int count = 0;
        try {
            while (true) {
                // 每次分配 10MB 的直接内存
                ByteBuffer buffer = ByteBuffer.allocateDirect(10 * 1024 * 1024);
                buffers.add(buffer);
                count++;
                System.out.println("分配了 " + (count * 10) + "MB 直接内存");
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        //        分配了 3900MB 直接内存
        //        java.lang.OutOfMemoryError: Direct buffer memory
        //        at java.base/java.nio.Bits.reserveMemory(Bits.java:175)
        //        at java.base/java.nio.DirectByteBuffer.<init>(DirectByteBuffer.java:118)
        //        at java.base/java.nio.ByteBuffer.allocateDirect(ByteBuffer.java:317)
        //        at com.wilkinszhang.DirectBufferOOMDemo.main(DirectBufferOOMDemo.java:15)
    }
}
