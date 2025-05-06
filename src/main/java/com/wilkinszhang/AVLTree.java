package com.wilkinszhang;

class AVLTree {
    Node root;

    // 获取节点高度
    int height(Node node) {
        if (node == null) return 0;
        return node.height;
    }

    // 获取平衡因子
    int getBalance(Node node) {
        if (node == null) return 0;
        return height(node.left) - height(node.right);
    }

    // 更新节点高度
    void updateHeight(Node node) {
        if (node == null) return;
        node.height = Math.max(height(node.left), height(node.right)) + 1;
    }

    // 右旋转
    Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    // 左旋转
    Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    // 插入节点
    Node insert(Node node, int key) {
        if (node == null) {
            return new Node(key);
        }

        if (key < node.key) {
            node.left = insert(node.left, key);
        } else if (key > node.key) {
            node.right = insert(node.right, key);
        } else {
            return node; // 重复键值不插入
        }

        updateHeight(node);

        int balance = getBalance(node);

        // 左左情况
        if (balance > 1 && key < node.left.key) {
            return rightRotate(node);
        }

        // 右右情况
        if (balance < -1 && key > node.right.key) {
            return leftRotate(node);
        }

        // 左右情况
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // 右左情况
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // 构建AVL树并返回高度
    public int buildAndGetHeight(int[] keys) {
        root = null;
        for (int key : keys) {
            root = insert(root, key);
        }
        return height(root);
    }

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        int[] keys = {20, 10, 30, 5, 15, 25, 35,3,7,12,17};
        System.out.println("AVL树的高度: " + tree.buildAndGetHeight(keys));
    }
}