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
