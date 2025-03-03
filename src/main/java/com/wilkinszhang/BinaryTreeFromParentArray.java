package com.wilkinszhang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TreeNode2 {
    int val;
    TreeNode2 left, right;

    TreeNode2(int val) {
        this.val = val;
    }
}

public class BinaryTreeFromParentArray {
    public static TreeNode2 createTree(int[] parent) {
        Map<Integer, TreeNode2> nodeMap = new HashMap<>();
        TreeNode2 root = null;

        // 1. 创建节点并保存到 map 中
        for (int i = 0; i < parent.length; i++) {
            nodeMap.put(i, new TreeNode2(i));
        }

        // 2. 根据父节点数组建立树
        for (int i = 0; i < parent.length; i++) {
            int parentIndex = parent[i];
            TreeNode2 currentNode = nodeMap.get(i);

            if (parentIndex == -1) {
                root = currentNode; // -1 表示根节点
            } else {
                TreeNode2 parentNode = nodeMap.get(parentIndex);
                // 先将其作为左子节点，若已存在左节点，则作为右子节点
                if (parentNode.left == null) {
                    parentNode.left = currentNode;
                } else {
                    parentNode.right = currentNode;
                }
            }
        }

        return root;
    }

    // 后序遍历函数
    public static List<Integer> postOrderTraversal(TreeNode2 root) {
        List<Integer> result = new ArrayList<>();
        postOrderHelper(root, result);
        return result;
    }

    private static void postOrderHelper(TreeNode2 node, List<Integer> result) {
        if (node == null) return;
        postOrderHelper(node.left, result);   // 左
        postOrderHelper(node.right, result);  // 右
        result.add(node.val);                 // 根
    }

    public static void main(String[] args) {
        int[] parent = {-1, 0, 0, 1, 1, 2, 2};
        TreeNode2 root = createTree(parent);

        List<Integer> postOrder = postOrderTraversal(root);
        System.out.println("后序遍历结果: " + postOrder);
    }
}
