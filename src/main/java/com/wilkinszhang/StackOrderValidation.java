package com.wilkinszhang;

import java.util.Stack;

public class StackOrderValidation {

    public static boolean validateStackSequences(char[] pushed, char[] popped) {
        Stack<Character> stack = new Stack<>();
        int popIndex = 0;
        int consecutivePush = 0;

        for (char ch : pushed) {
            // Check if we can push another element or if we need to pop
            while (!stack.isEmpty() && stack.peek() == popped[popIndex]) {
                stack.pop();
                popIndex++;
                consecutivePush = 0;  // Reset the consecutive push count after a pop
            }

            // Push the current element to the stack if not exceeding the limit
            if (consecutivePush < 2) {
                stack.push(ch);
                consecutivePush++;
            } else {
                return false;  // Return false if trying to push the third time consecutively
            }
        }

        // Check if remaining elements in the stack can be popped
        while (!stack.isEmpty()) {
            if (stack.peek() == popped[popIndex]) {
                stack.pop();
                popIndex++;
            } else {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        char[] pushed = {'a', 'b', 'c', 'd', 'e'};
        char[] popped =  {'d', 'c', 'e', 'a', 'b'};

        if (validateStackSequences(pushed, popped)) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }
}
