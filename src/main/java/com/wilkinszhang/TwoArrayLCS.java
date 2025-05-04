package com.wilkinszhang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TwoArrayLCS {
    public static List<Integer> longestCommonSubsequence(int[]a, int[]b){
        int n=a.length,m=b.length;
        int[][]dp=new int[n+1][m+1];
        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){
                if(a[i-1]==b[j-1]){
                    dp[i][j]=dp[i-1][j-1]+1;
                }else{
                    dp[i][j]=Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
        }
        List<Integer> lcs=new ArrayList<>();
        int i=n,j=m;
        while(i>0 && j>0){
            if(a[i-1]==b[j-1]){
                lcs.add(a[i-1]);
                i--;
                j--;
            }else{
                if(dp[i-1][j]>=dp[i][j-1]){
                    i--;
                }else{
                    j--;
                }
            }
        }
        Collections.reverse(lcs);
        return lcs;
    }

    public static void main(String[]args){
        int[]a={1,3,4,1,2,8};
        int[]b={3,4,1,2,1,7};
        List<Integer>lcs=longestCommonSubsequence(a,b);
        System.out.println(lcs);
    }
}
