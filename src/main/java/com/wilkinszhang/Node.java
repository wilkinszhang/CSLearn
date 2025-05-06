package com.wilkinszhang;

class Node {
    int key;
    Node left;
    Node right;
    int height;

    Node(int key) {
        this.key = key;
        this.height = 1;
    }
}
