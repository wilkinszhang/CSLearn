package com.wilkinszhang;

public class OddCount {
    public static int oddEvenCheck(String binaryString) {
        // 初始化两个变量，分别记录 '0' 和 '1' 的奇偶性
        char zeroParity = 0; // '0' 的奇偶性
        char oneParity = 0;  // '1' 的奇偶性

        // 遍历字符串
        for (int i = 0; i < binaryString.length(); i++) {
            char c = binaryString.charAt(i);
            if (c == '0') {
                zeroParity ^= 1; // 翻转 '0' 的奇偶性
            } else if (c == '1') {
                oneParity ^= 1;  // 翻转 '1' 的奇偶性
            }
        }

        // 如果 '0' 和 '1' 的奇偶性都为 1，则返回 1，否则返回 0
        return (zeroParity == 1 && oneParity == 1) ? 1 : 0;
    }

    public static void main(String[] args) {
        // 测试用例
        System.out.println(oddEvenCheck("010101")); // 输出 1
        System.out.println(oddEvenCheck("0101"));   // 输出 0
        System.out.println(oddEvenCheck("111"));    // 输出 0
        System.out.println(oddEvenCheck("000111")); // 输出 1
    }
}
