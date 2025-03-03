package com.wilkinszhang;

import java.util.Stack;

public class PostfixToInfix {
    public static String postfixToInfix(String postfix) {
        Stack<String> stack = new Stack<>();

        // 遍历后缀表达式的每个字符
        for (int i = 0; i < postfix.length(); i++) {
            char ch = postfix.charAt(i);

            // 如果是操作符，需要弹出栈顶的两个元素进行运算
            if (isOperator(ch)) {
                // 弹出栈顶的两个元素
                String op2 = stack.pop();
                String op1 = stack.pop();

                // 组成中缀表达式并压回栈中
                String exp = "(" + op1 + ch + op2 + ")";
                stack.push(exp);
            }
            // 如果是数字或变量，则直接压栈
            else {
                stack.push(Character.toString(ch));
            }
        }

        // 最后栈顶的元素就是完整的中缀表达式
        return stack.pop();
    }

    // 检查给定的字符是否是操作符
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    public static void main(String[] args) {
        String postfix = "abcd+*e**";
        String infix = postfixToInfix(postfix);
        System.out.println("Postfix: " + postfix);
        System.out.println("Infix: " + infix);
    }
}
