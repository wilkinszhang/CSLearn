package com.wilkinszhang;

import java.util.Stack;

//public class com.wilkinszhang.StackSequenceValidator {
//    public static boolean isValidStackSequence(int[] pushed, int[] popped) {
//        Stack<Integer> stack = new Stack<>();
//        int j = 0; // Index for popped sequence
//
//        for (int num : pushed) {
//            stack.push(num);
//
//            while (!stack.isEmpty() && j < popped.length && stack.peek() == popped[j]) {
//                stack.pop();
//                j++;
//            }
//        }
//
//        return j == popped.length;
//    }
//
//    public static void main(String[] args) {
//        // 测试用例
//        int[] pushed1 = {6, 5, 4, 3, 2,1};
//        int[] popped1 = {3, 4, 6, 5, 2,1};
//        System.out.println("com.wilkinszhang.Test case 1 is valid: " + isValidStackSequence(pushed1, popped1));
//
//    }
//}
public class StackSequenceValidator {
    public static boolean validateStackSequences(String inSequence, String outSequence) {
        if (inSequence.length() != outSequence.length()) {
            return false; // 入栈和出栈序列长度不同，直接返回false
        }

        Stack<Character> stack = new Stack<>();
        int j = 0; // 用于跟踪出栈序列的索引

        for (int i = 0; i < inSequence.length(); i++) {
            char c = inSequence.charAt(i);
            stack.push(c); // 将入栈序列的当前字符推入栈

            // 检查栈顶元素是否与出栈序列的当前字符匹配
            while (!stack.isEmpty() && stack.peek() == outSequence.charAt(j)) {
                stack.pop(); // 出栈
                j++; // 移动到出栈序列的下一个字符
            }
        }

        return stack.isEmpty(); // 如果栈为空，则所有元素正确匹配，否则不匹配
    }

    public static void main(String[] args) {
        String inSequence = "ABCDE";  // 入栈序列
        String outSequence = "DCEAB"; // 出栈序列，可以修改测试不同情况

        boolean result = validateStackSequences(inSequence, outSequence);
        System.out.println("The out sequence " + outSequence + " is " +
                (result ? "possible" : "not possible") + " for the in sequence " + inSequence);
    }
}