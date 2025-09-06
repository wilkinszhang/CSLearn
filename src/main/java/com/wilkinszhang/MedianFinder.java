package com.wilkinszhang;


import java.util.Random;

//无序数组求中位数
public class MedianFinder {
    private static final Random RANDOM=new Random();
    public static double findMedian(int[]nums){
        if(nums==null || nums.length==0){
            return -1;
        }
        int n=nums.length;
        int[]arr=nums.clone();
        if(n%2==1){
            return quickSelect(arr,0,n-1,n/2);
        }else{
            int lower=quickSelect(arr.clone(),0,n-1,n/2-1);
            int upper=quickSelect(arr.clone(),0,n-1,n/2);
            return (lower+upper)/2.0;
        }
    }

    private static int quickSelect(int[]arr,int left,int right,int k){
        if(left==right){
            return arr[left];
        }
        int pivotIdx=left+RANDOM.nextInt(right-left+1);
        pivotIdx=partition(arr,left,right,pivotIdx);
        if(k==pivotIdx){
            return arr[k];
        }else if(k<pivotIdx){
            return quickSelect(arr,left,pivotIdx-1,k);
        }else{
            return quickSelect(arr,pivotIdx+1,right,k);
        }
    }
    private static int partition(int[]arr,int left,int right,int pivotIdx){
        int pivotValue=arr[pivotIdx];
        swap(arr,pivotIdx,right);
        int storeIdx=left;
        for(int i=left;i<right;i++){
            if(arr[i]<pivotValue){
                swap(arr,storeIdx,i);
                storeIdx++;
            }
        }
        swap(arr,storeIdx,right);
        return storeIdx;
    }
    private static void swap(int[]arr,int i,int j){
        int temp=arr[i];
        arr[i]=arr[j];
        arr[j]=temp;
    }

    public static void main(String[]args){
        int[]a={1,5,6,3,4};
        System.out.println(findMedian(a));
    }
}
