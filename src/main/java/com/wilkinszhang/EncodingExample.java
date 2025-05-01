package com.wilkinszhang;

import java.io.UnsupportedEncodingException;

public class EncodingExample {
    public static void main(String[] args) {
        // 文本编码示例
        String text = "Hello, World!";
        try {
            byte[] textBytes = text.getBytes("UTF-8"); // 将文本编码为UTF-8格式的二进制数据
            System.out.println(textBytes.length);
            System.out.println("文本编码后的二进制数据: " + bytesToHex(textBytes));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 二进制编码示例
        byte[] binaryData = {0x48, 0x65, 0x6C, 0x6C, 0x6F}; // 二进制数据
        String binaryText = new String(binaryData); // 将二进制数据解码为文本
        System.out.println("二进制数据解码后的文本: " + binaryText);
    }

    // 将字节数组转换为十六进制字符串
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02X ", b));
        }
        return hexString.toString();
    }
}
