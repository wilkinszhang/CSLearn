package com.wilkinszhang;

import java.util.PriorityQueue;
import java.util.Scanner;

public class HuffmanTree {
    // 哈夫曼树节点类
    static class Node implements Comparable<Node> {
        int weight;    // 权值
        Node left;     // 左子节点
        Node right;    // 右子节点

        public Node(int weight) {
            this.weight = weight;
            this.left = null;
            this.right = null;
        }

        // 实现Comparable接口，用于优先队列中的排序
        @Override
        public int compareTo(Node other) {
            return this.weight - other.weight;
        }
    }

    // 计算哈夫曼树的带权路径长度(WPL)
    public static int calculateWPL(int[] weights) {
        // 处理特殊情况
        if (weights == null || weights.length == 0) {
            return 0;
        }
        if (weights.length == 1) {
            return 0; // 只有一个节点时，深度为0，WPL为0
        }

        // 创建优先队列（小根堆）
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();

        // 将所有权值放入优先队列
        for (int weight : weights) {
            priorityQueue.offer(new Node(weight));
        }

        // 构造哈夫曼树
        while (priorityQueue.size() > 1) {
            // 取出两个最小权值的节点
            Node left = priorityQueue.poll();
            Node right = priorityQueue.poll();

            // 创建新的父节点，权值为两个子节点的权值之和
            Node parent = new Node(left.weight + right.weight);
            parent.left = left;
            parent.right = right;

            // 将新节点加入优先队列
            priorityQueue.offer(parent);
        }

        // 计算带权路径长度
        Node root = priorityQueue.poll();
        return calculateWPLRecursive(root, 0);
    }

    // 递归计算带权路径长度
    private static int calculateWPLRecursive(Node node, int depth) {
        if (node == null) {
            return 0;
        }

        // 如果是叶子节点，返回权值乘以深度
        if (node.left == null && node.right == null) {
            return node.weight * depth;
        }

        // 递归计算左右子树的WPL并求和
        return calculateWPLRecursive(node.left, depth + 1) +
                calculateWPLRecursive(node.right, depth + 1);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入叶子节点的数量：");
        int n = scanner.nextInt();

        int[] weights = new int[n];
        System.out.println("请输入" + n + "个叶子节点的权值：");
        for (int i = 0; i < n; i++) {
            weights[i] = scanner.nextInt();
        }

        int wpl = calculateWPL(weights);
        System.out.println("哈夫曼树的带权路径长度(WPL)为：" + wpl);

        scanner.close();
    }
}