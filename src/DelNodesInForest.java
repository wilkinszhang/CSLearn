import java.util.*;

public class DelNodesInForest {

    public List<TreeNode2> delNodes(TreeNode2 root, int[] nums) {
        Set<Integer> toDelete = new HashSet<>();
        for (int num : nums) {
            toDelete.add(num);
        }

        List<TreeNode2> forest = new ArrayList<>();
        deleteNodes(root, toDelete, forest, true);
        return forest;
    }

    private TreeNode2 deleteNodes(TreeNode2 node, Set<Integer> toDelete, List<TreeNode2> forest, boolean isRoot) {
        if (node == null) {
            return null;
        }

        // 判断当前节点是否要被删除
        boolean deleted = toDelete.contains(node.val);

        // 如果是根节点且未被删除，将该节点加入森林
        if (isRoot && !deleted) {
            forest.add(node);
        }

        // 递归处理左子树和右子树
        node.left = deleteNodes(node.left, toDelete, forest, deleted);
        node.right = deleteNodes(node.right, toDelete, forest, deleted);

        // 如果当前节点被删除，返回 null
        return deleted ? null : node;
    }

    public static void main(String[] args) {
        // 构造二叉树
        TreeNode2 root = new TreeNode2(1);
        root.left = new TreeNode2(2);
        root.right = new TreeNode2(3);
        root.left.left = new TreeNode2(4);
        root.left.right = new TreeNode2(5);
        root.right.left = new TreeNode2(6);
        root.right.right = new TreeNode2(7);

        int[] nums = {3, 5};

        DelNodesInForest solution = new DelNodesInForest();
        List<TreeNode2> forest = solution.delNodes(root, nums);

        // 打印结果森林的根节点值
        System.out.println("森林的根节点:");
        for (TreeNode2 tree : forest) {
            System.out.println(tree.val);
        }
    }
}