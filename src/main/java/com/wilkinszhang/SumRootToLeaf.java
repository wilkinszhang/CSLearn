package com.wilkinszhang;

// 二叉树路径和，12345输出为124+125+13=262
public class SumRootToLeaf {
    public static void main(String []args){
        TreeNode root=new TreeNode(1);
        TreeNode root2=new TreeNode(2);
        TreeNode root3=new TreeNode(3);
        TreeNode root4=new TreeNode(4);
        TreeNode root5=new TreeNode(5);
        root.left=root2;
        root.right=root3;
        root2.left=root4;
        root2.right=root5;
        int total=sumNumbers(root);
        System.out.println(total);
    }

    public static int sumNumbers(TreeNode root){
        return dfs(root,0);
    }

    private static int dfs(TreeNode node,int cur){
        if(node==null)return 0;
        cur=cur*10+node.val;
        if(node.left==null && node.right==null){
            return cur;
        }
        return dfs(node.left,cur)+dfs(node.right,cur);
    }
}
