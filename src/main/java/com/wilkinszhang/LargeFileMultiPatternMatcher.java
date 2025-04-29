package com.wilkinszhang;

import com.hankcs.algorithm.AhoCorasickDoubleArrayTrie;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//1TB的长字符串数据，有1000个要匹配的字符串，怎么在单机情况下设计程序
public class LargeFileMultiPatternMatcher {
    public static void main(String[]args) throws IOException {
        List<String> patterns= Arrays.asList("Alice", "Cloud", "Tifa");//待匹配的1000个串
        AhoCorasickDoubleArrayTrie<String> aho=new AhoCorasickDoubleArrayTrie<>();
        Map<String,String> patternMap=new HashMap<>();
        for(String p:patterns){
            patternMap.put(p,p);
        }
        aho.build(patternMap);

        Path filePath= Paths.get("hugefile.txt");
        try (FileChannel channel = FileChannel.open(filePath, StandardOpenOption.READ)) {
            long fileSize = channel.size();
            long offset = 0;
            int maxPatternLen = 100; // 假设最长模式长度约100
            int overlap = maxPatternLen - 1;
            int chunkSize = 64 * 1024 * 1024; // 每块 64MB，可根据内存调整
            ByteBuffer buffer = ByteBuffer.allocate(chunkSize + overlap);
            byte[] carry = new byte[overlap];  // 保存上块末尾

            while (offset < fileSize) {
                buffer.clear();
                // 如果有残留字符，先放入缓冲区开头
                buffer.put(carry);
                // 读取当前块
                int toRead = (int) Math.min(chunkSize, fileSize - offset);
                buffer.limit(overlap + toRead);
                int bytesRead = channel.read(buffer, offset);
                buffer.flip(); // 准备读取
                if (bytesRead <= 0) break;

                // 转为CharSequence（假设ASCII可直接转，或使用Decoder）
                CharBuffer text = StandardCharsets.UTF_8.decode(buffer);
                // 3. 运行匹配，并记录位置
                long chunkStart = offset;
                aho.parseText(text, (begin, end, value) -> {
                    long matchPos = chunkStart - overlap + begin;
                    System.out.println("Found pattern \"" + value + "\" at position " + matchPos);
                });

                // 保存尾部 overlap 字节到carry，用于下一块
                if (bytesRead >= overlap) {
                    buffer.position(bytesRead); // 移至有效数据末尾
                    buffer.get(carry, 0, overlap); // 取末尾overlap字节
                } else {
                    // 少于overlap则全部搬到carry
                    int rem = bytesRead;
                    buffer.position(overlap);
                    buffer.get(carry, 0, rem);
                }
                offset += bytesRead;
            }
        }
    }
}
