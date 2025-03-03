package com.wilkinszhang;

import java.util.ArrayList;
import java.util.List;

public class OOMExample {
    private List<byte[]> memoryLeak = new ArrayList<>();

    public void generateMemoryLeak() {
        while (true) {
            // 每次循环向列表中添加10MB的字节数组
            memoryLeak.add(new byte[10 * 1024 * 1024]);
            System.out.println("当前列表大小: " + memoryLeak.size());
            try {
                Thread.sleep(100); // 暂停100毫秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        OOMExample example = new OOMExample();
        example.generateMemoryLeak();
    }
}
