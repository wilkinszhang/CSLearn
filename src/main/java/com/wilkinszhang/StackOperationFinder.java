package com.wilkinszhang;

import java.util.*;

public class StackOperationFinder {
    // 用于存储操作序列的结果
    private static class Result {
        boolean isPossible;
        List<String> operations;

        Result(boolean isPossible, List<String> operations) {
            this.isPossible = isPossible;
            this.operations = operations;
        }
    }

    public static Result findOperations(String input, String output) {
        // 检查输入输出长度是否相等
        if (input.length() != output.length()) {
            return new Result(false, new ArrayList<>());
        }

        List<String> operations = new ArrayList<>();
        Stack<Character> stack = new Stack<>();
        int inputIndex = 0;
        int outputIndex = 0;

        while (inputIndex < input.length()) {
            // 如果栈为空或栈顶元素不是当前需要的输出
            if (stack.isEmpty() || stack.peek() != output.charAt(outputIndex)) {
                // 如果已经没有输入元素了，但还需要输出
                if (inputIndex >= input.length()) {
                    return new Result(false, new ArrayList<>());
                }
                // 压入新元素
                stack.push(input.charAt(inputIndex));
                operations.add("push " + input.charAt(inputIndex));
                inputIndex++;
            }

            // 当栈顶元素匹配输出序列时
            while (!stack.isEmpty() && outputIndex < output.length()
                    && stack.peek() == output.charAt(outputIndex)) {
                stack.pop();
                operations.add("pop " + output.charAt(outputIndex));
                outputIndex++;
            }
        }

        // 处理栈中剩余的元素
        while (!stack.isEmpty() && outputIndex < output.length()) {
            if (stack.peek() != output.charAt(outputIndex)) {
                return new Result(false, new ArrayList<>());
            }
            operations.add("pop " + stack.pop());
            outputIndex++;
        }

        // 检查是否所有元素都处理完毕
        if (stack.isEmpty() && outputIndex == output.length()) {
            return new Result(true, operations);
        } else {
            return new Result(false, new ArrayList<>());
        }
    }

    public static void main(String[] args) {
        // 测试用例
        String input = "XYZW";
        String output = "XZYW";

        Result result = findOperations(input, output);

        if (result.isPossible) {
            System.out.println("可以通过栈操作得到目标序列。操作顺序为：");
            for (String op : result.operations) {
                System.out.println(op);
            }
        } else {
            System.out.println("无法通过栈操作得到目标序列。");
        }


    }
}