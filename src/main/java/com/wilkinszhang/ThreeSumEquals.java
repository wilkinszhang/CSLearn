package com.wilkinszhang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//有序数组找出所有（a+b=c）的三元组
public class ThreeSumEquals {
    public static List<List<Integer>> findTree(int[]arr){
        List<List<Integer>>res=new ArrayList<>();
        int n=arr.length;
        for(int k=n-1;k>=2;k--){
            int target=arr[k];
            int i=0,j=k-1;
            while(i<j){
                int sum=arr[i]+arr[j];
                if(sum==target){
                    res.add(Arrays.asList(arr[i],arr[j],target));
                    int vi=arr[i],vj=arr[j];
                    while(i<j && arr[i]==vi)i++;
                    while(i<j && arr[j]==vj)j--;
                }else if(sum<target){
                    i++;
                }else{
                    j--;
                }
            }
            while(k>2 && arr[k]==arr[k-1]){
                k--;
            }
        }
        return res;
    }

    public static void main(String[]args){
        int[]arr={1,2,2,3,4,5,7};
        List<List<Integer>>tri=findTree(arr);
        for(List<Integer>t:tri){
            System.out.println(t);
        }
    }
}
