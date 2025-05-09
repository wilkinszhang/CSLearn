package com.wilkinszhang;

//二维矩阵中第k个最大值，矩阵中的元素符合按行递增和按列递增
public class KthLargestInMatrix {
    public static int kthLargest(int[][]matrix,int k){
        int m=matrix.length,n=matrix[0].length;
        int lo=matrix[0][0],hi=matrix[m-1][n-1],ans=lo;
        while(lo<=hi){
            int mid=lo+((hi-lo)>>1);
            int cnt=countGreaterEqual(matrix,mid);
            if(cnt>=k){
                ans=mid;
                lo=mid+1;
            }else{
                hi=mid-1;
            }
        }
        return ans;
    }

    private static int countGreaterEqual(int[][]matrix,int target){
        int m=matrix.length,n=matrix[0].length;
        int row=m-1,col=0,count=0;
        while(row>=0 && col<n){
            if(matrix[row][col]>=target){
                count+=(n-col);
                row--;
            }else{
                col++;
            }
        }
        return count;
    }

    public static void main(String[]args){
        int[][]mat={
                {1,5,9},
                {10,11,13},
                {12,13,15}
        };
        System.out.println(kthLargest(mat,3));
    }
}
