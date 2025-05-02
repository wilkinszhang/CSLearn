package com.wilkinszhang;

//对角线之字打印
public class DiagonalZigzagPrint {
    public static void diagonalZigzag(int[][]a){
        if(a==null || a.length==0)return;
        int m=a.length;
        int n=a[0].length;
        for(int k=0;k<=m+n-2;k++){
            int low=Math.max(0,k-(n-1));
            int high=Math.min(k,m-1);
            if(k%2==0){
                for(int i=low;i<=high;i++){
                    int j=k-i;
                    System.out.print(a[i][j]+" ");
                }
            }else{
                for(int i=high;i>=low;i--){
                    int j=k-i;
                    System.out.print(a[i][j]+" ");
                }
            }
        }
    }

    public static void main(String []args){
        int[][]a={
                {1,2,3},
                {4,5,6},
                {7,8,9}
        };
        diagonalZigzag(a);
    }
}
