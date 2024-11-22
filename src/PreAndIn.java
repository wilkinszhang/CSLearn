public class PreAndIn {
    static class TreeNode {
        char val;
        TreeNode left;
        TreeNode right;

        TreeNode(char x) {
            val = x;
        }
    }

    public static void main(String[] args) {
        String preOrder = "ABCDEFG";
        String inOrder = "CEDFBAH";
        TreeNode root = buildTree(preOrder, inOrder);
        System.out.println("后序遍历结果：");
        printPostOrder(root);
    }

    private static TreeNode buildTree(String preOrder, String inOrder) {
        if (preOrder.isEmpty()) return null;

        char rootVal = preOrder.charAt(0);
        TreeNode root = new TreeNode(rootVal);

        int rootIndex = inOrder.indexOf(rootVal);

        // 切分中序遍历字符串
        String leftInOrder = inOrder.substring(0, rootIndex);
        String rightInOrder = inOrder.substring(rootIndex + 1);

        // 切分先序遍历字符串
        String leftPreOrder = preOrder.substring(1, rootIndex + 1);
        String rightPreOrder = preOrder.substring(rootIndex + 1);

        // 递归构建左右子树
        root.left = buildTree(leftPreOrder, leftInOrder);
        root.right = buildTree(rightPreOrder, rightInOrder);

        return root;
    }

    private static void printPostOrder(TreeNode root) {
        if (root == null) return;
        printPostOrder(root.left);
        printPostOrder(root.right);
        System.out.print(root.val + " ");
    }
}
