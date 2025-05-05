package com.wilkinszhang;

//查询有序数组元素次数
public class CountOccurrences {
    public static int findFirst(int[]nums,int target){
        int low=0,high=nums.length-1;
        int ans=-1;
        while(low<=high){
            int mid=low+(high-low)/2;
            if(nums[mid]<target){
                low=mid+1;
            }else if(nums[mid]>target){
                high=mid-1;
            }else{
                ans=mid;
                high=mid-1;
            }
        }
        return ans;
    }

    public static int findLast(int[]nums,int target){
        int low=0,high=nums.length-1;
        int ans=-1;
        while(low<=high){
            int mid=low+(high-low)/2;
            if(nums[mid]<target){
                low=mid+1;
            }else if(nums[mid]>target){
                high=mid-1;
            }else{
                ans=mid;
                low=mid+1;
            }
        }
        return ans;
    }

    public static int countOccurrences(int[]nums,int target){
        int first=findFirst(nums,target);
        if(first==-1)return 0;
        int last=findLast(nums,target);
        return last-first+1;
    }

    public static void main(String []args){
        int[]nums={1,2,2,2,3,4,5,5,7};
        int target=2;
        System.out.println(countOccurrences(nums,target));
    }
}
