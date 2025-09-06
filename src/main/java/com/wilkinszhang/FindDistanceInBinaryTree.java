package com.wilkinszhang;

//找到二叉树中的距离
public class FindDistanceInBinaryTree {
    public int shortestDistance(TreeNode root,TreeNode a, TreeNode b){
        TreeNode lca=lowestCommonAncestor(root,a,b);
        if(lca==null){
            return -1;
        }
        int d1=distanceFrom(lca,a,0);
        int d2=distanceFrom(lca,b,0);
        if(d1<0 || d2<0){
            return -1;
        }
        return d1+d2;
    }

    private int distanceFrom(TreeNode root,TreeNode target,int dist){
        if(root==null)return -1;
        if(root==target)return dist;
        int d=distanceFrom(root.left,target,dist+1);
        if(d>=0)return d;
        return distanceFrom(root.right,target,dist+1);
    }

    private TreeNode lowestCommonAncestor(TreeNode root,TreeNode p,TreeNode q){
        if(root==null || root==p || root==q){
            return root;
        }
        TreeNode left=lowestCommonAncestor(root.left,p,q);
        TreeNode right=lowestCommonAncestor(root.right,p,q);
        if(left!=null && right!=null){
            return root;
        }
        return left!=null ? left:right;
    }
}
