import java.util.HashSet;
import java.util.Stack;

public class BinaryTreeTraversal {

    // 判断给定的前序遍历串是否能构成中序遍历
    public static boolean canBeInOrder(String preorder, String inorder) {
        // 使用栈模拟树的构建过程
        Stack<Character> stack = new Stack<>();
        int j = 0; // 中序遍历的指针

        for (char node : preorder.toCharArray()) {
            stack.push(node); // 将前序遍历的节点压入栈

            // 如果栈顶的节点与中序遍历的当前节点相同
            while (!stack.isEmpty() && j < inorder.length() && stack.peek() == inorder.charAt(j)) {
                stack.pop(); // 弹出栈顶节点
                j++; // 移动中序遍历的指针
            }
        }

        // 检查是否所有中序遍历的节点都被匹配
        return j == inorder.length();
    }

    public static void main(String[] args) {
        String preorder = "ABCDEFG"; // 前序遍历序列
        String inorder = "CBAEDFG";   // 中序遍历序列

        if (canBeInOrder(preorder, inorder)) {
            System.out.println("前序遍历可以形成中序遍历");
        } else {
            System.out.println("前序遍历不能形成中序遍历");
        }
    }
}
