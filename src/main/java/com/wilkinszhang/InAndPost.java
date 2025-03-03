package com.wilkinszhang;

public class InAndPost {
    static class TreeNode {
        char val;
        TreeNode left;
        TreeNode right;

        TreeNode(char x) {
            val = x;
        }
    }

    public static void main(String[] args) {
        String inOrder = "dgbaecfh";  // 输入的中序遍历
        String postOrder = "gdbehfca"; // 输入的后序遍历

        TreeNode root = buildTree(inOrder, postOrder);
        System.out.println("前序遍历结果：");
        printPreOrder(root);
    }

    // 根据中序遍历和后序遍历构建树
    private static TreeNode buildTree(String inOrder, String postOrder) {
        if (postOrder.isEmpty()) return null;

        // 获取根节点 (后序遍历的最后一个节点是根节点)
        char rootVal = postOrder.charAt(postOrder.length() - 1);
        TreeNode root = new TreeNode(rootVal);

        // 在中序遍历中找到根节点的位置
        int rootIndex = inOrder.indexOf(rootVal);

        // 切分中序遍历字符串
        String leftInOrder = inOrder.substring(0, rootIndex);
        String rightInOrder = inOrder.substring(rootIndex + 1);

        // 切分后序遍历字符串
        String leftPostOrder = postOrder.substring(0, leftInOrder.length());
        String rightPostOrder = postOrder.substring(leftInOrder.length(), postOrder.length() - 1);

        // 递归构建左右子树
        root.left = buildTree(leftInOrder, leftPostOrder);
        root.right = buildTree(rightInOrder, rightPostOrder);

        return root;
    }

    // 前序遍历并输出结果
    private static void printPreOrder(TreeNode root) {
        if (root == null) return;
        System.out.print(root.val + " ");
        printPreOrder(root.left);
        printPreOrder(root.right);
    }
}
