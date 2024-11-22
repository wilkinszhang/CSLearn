import java.util.Stack;
import java.util.Scanner;

public class StackSequenceValidatorWithLen {
    public static void main(String[] args) {
        // 测试用例
        String pushSequence = "seecneuq";  // 入栈序列
        String popSequence = "sequence";   // 出栈序列
        int len = 5;  // 栈的最大空间

        if (isPopSequenceValid(pushSequence, popSequence, len)) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }

    public static boolean isPopSequenceValid(String pushSeq, String popSeq, int maxLen) {
        Stack<Character> stack = new Stack<>();  // 辅助栈
        int pushIndex = 0;  // 入栈序列的索引
        int popIndex = 0;   // 出栈序列的索引

        while (popIndex < popSeq.length()) {
            // 如果栈为空或者栈顶元素不等于当前出栈元素，则继续入栈
            while (stack.isEmpty() || (stack.peek() != popSeq.charAt(popIndex))) {
                if (pushIndex >= pushSeq.length()) {
                    break;  // 如果入栈序列处理完了，跳出循环
                }

                // 如果栈的当前大小超过最大容量，则返回NO
                if (stack.size() >= maxLen) {
                    return false;
                }

                stack.push(pushSeq.charAt(pushIndex));  // 入栈
                pushIndex++;
            }

            // 如果栈顶元素等于当前出栈元素，进行出栈操作
            if (stack.peek() == popSeq.charAt(popIndex)) {
                stack.pop();  // 出栈
                popIndex++;
            } else {
                return false;  // 不匹配，返回NO
            }
        }

        return true;  // 如果成功完成，返回YES
    }
}