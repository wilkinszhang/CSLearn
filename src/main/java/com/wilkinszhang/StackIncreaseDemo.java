package com.wilkinszhang;

import java.util.Stack;

class EnhancedStack {
    private Stack<Integer> mainStack;
    private int[] increments;
    private int size;

    public EnhancedStack(int maxSize) {
        mainStack = new Stack<>();
        increments = new int[maxSize];
        size = 0;
    }

    public void push(int x) {
        mainStack.push(x);
        size++;
    }

    public int pop() {
        if (mainStack.isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }

        // 计算当前元素的最终值
        int index = size - 1;
        int totalIncrement = 0;
        for (int i = index; i >= 0; i--) {
            totalIncrement += increments[i];
        }

        size--;
        return mainStack.pop() + totalIncrement;
    }

    public void increase(int k, int val) {
        // 确保不超过栈的实际大小
        k = Math.min(k, size);

        // 仅在辅助数组中记录增量，O(1)操作
        if (k > 0) {
            increments[k - 1] += val;
        }
    }

    public int top() {
        if (mainStack.isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }

        // 计算栈顶元素的最终值
        int index = size - 1;
        int totalIncrement = 0;
        for (int i = index; i >= 0; i--) {
            totalIncrement += increments[i];
        }

        return mainStack.peek() + totalIncrement;
    }

    public boolean isEmpty() {
        return mainStack.isEmpty();
    }

    public int size() {
        return size;
    }
}

// 使用示例
public class StackIncreaseDemo {
    public static void main(String[] args) {
        EnhancedStack stack = new EnhancedStack(5);

        // 压入一些元素
        stack.push(1);
        stack.push(2);
        stack.push(3);

        // 对前两个元素增加2
        stack.increase(2, 2);

        // 弹出元素并打印
        System.out.println(stack.pop()); // 应该输出 5 (3 + 2)
        System.out.println(stack.pop()); // 应该输出 4 (2 + 2)
        System.out.println(stack.pop()); // 应该输出 3 (1 + 2)
    }
}