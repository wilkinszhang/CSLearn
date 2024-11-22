import java.util.*;

public class StackCapacity {
    public static int minStackCapacity(int[] input, int[] output) {
        Stack<Integer> stack = new Stack<>();
        int capacity = 0;
        int inputIndex = 0;
        int outputIndex = 0;

        while (outputIndex < output.length) {
            // 尝试将输入序列中的元素入栈
            while (inputIndex < input.length && (stack.isEmpty() || stack.peek() != output[outputIndex])) {
                stack.push(input[inputIndex++]);
                capacity = Math.max(capacity, stack.size());
            }

            // 如果栈顶元素等于当前出栈元素，执行出栈
            if (!stack.isEmpty() && stack.peek() == output[outputIndex]) {
                stack.pop();
                outputIndex++;
            } else {
                // 如果无法匹配，说明不可能形成该出栈序列
                break;
            }
        }

        return capacity;
    }

    public static void main(String[] args) {
        int[] input = {1, 2, 3, 4, 5, 6}; // 输入序列
        int[] output = {2, 3, 4, 6, 5, 1}; // 出栈序列

        int capacity = minStackCapacity(input, output);
        System.out.println("最小栈容量: " + capacity);
    }
}
