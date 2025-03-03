package com.wilkinszhang;

import java.util.ArrayList;
import java.util.List;

class TreeNode3 {
    int value;
    List<TreeNode3> children;

    public TreeNode3(int value) {
        this.value = value;
        this.children = new ArrayList<>();
    }
}

public class MultiWayTreePath {

    // 在树中查找从根节点到目标节点的路径
    public static List<Integer> findPath(TreeNode3 root, int target) {
        List<Integer> path = new ArrayList<>();
        if (dfs(root, target, path)) {
            return path;
        }
        return new ArrayList<>();  // 如果找不到目标节点，返回空路径
    }

    // 深度优先搜索，找到目标节点后返回 true
    private static boolean dfs(TreeNode3 node, int target, List<Integer> path) {
        if (node == null) {
            return false;
        }

        // 将当前节点添加到路径中
        path.add(node.value);

        // 检查是否为目标节点
        if (node.value == target) {
            return true;
        }

        // 遍历当前节点的所有子节点
        for (TreeNode3 child : node.children) {
            if (dfs(child, target, path)) {
                return true;  // 如果找到目标节点，返回 true
            }
        }

        // 如果未找到目标节点，回溯，移除当前节点
        path.remove(path.size() - 1);
        return false;
    }

    public static void main(String[] args) {
        // 创建多叉树
        TreeNode3 root = new TreeNode3(1);
        TreeNode3 child1 = new TreeNode3(2);
        TreeNode3 child2 = new TreeNode3(3);
        TreeNode3 child3 = new TreeNode3(4);
        TreeNode3 child4 = new TreeNode3(5);

        root.children.add(child1);
        root.children.add(child2);
        child1.children.add(child3);
        child1.children.add(child4);

        // 查找从根节点到目标节点的路径
        int target = 5;
        List<Integer> path = findPath(root, target);
        System.out.println("从根节点到节点 " + target + " 的路径: " + path);  // 输出: [1, 2, 5]
    }
}
