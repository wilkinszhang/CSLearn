package com.wilkinszhang;

import java.util.Stack;

//最大栈 力扣会员题
public class MaxStack {
    private Stack<Integer> stack; // 主栈
    private int max; // 当前最大值

    public MaxStack() {
        stack = new Stack<>();
        max = Integer.MIN_VALUE; // 初始最大值为最小整数
    }

    // 入栈操作
    public void push(int x) {
        // 如果当前值大于或等于最大值，将当前最大值存储到栈中，并更新最大值
        if (x >= max) {
            stack.push(max); // 存储旧的最大值
            max = x; // 更新最大值
        }
        stack.push(x); // 将新值入栈
    }

    // 出栈操作
    public int pop() {
        int top = stack.pop(); // 弹出栈顶元素
        if (top == max) { // 如果栈顶是最大值
            max = stack.pop(); // 恢复之前的最大值
        }
        return top;
    }

    // 获取栈的最大值
    public int getMax() {
        return max; // 最大值存储在变量 max 中
    }

    public static void main(String[] args) {
        MaxStack maxStack = new MaxStack();
        maxStack.push(5);
        maxStack.push(1);
        maxStack.push(5);
        System.out.println(maxStack.getMax()); // 输出: 5
        System.out.println(maxStack.pop());    // 输出: 5
        System.out.println(maxStack.getMax()); // 输出: 5
        maxStack.push(7);
        maxStack.push(7);
        maxStack.push(8);
        System.out.println(maxStack.getMax()); // 输出: 8
        System.out.println(maxStack.pop());    // 输出: 8
        System.out.println(maxStack.getMax()); // 输出: 7
    }
}