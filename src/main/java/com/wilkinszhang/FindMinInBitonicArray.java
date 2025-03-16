package com.wilkinszhang;

public class FindMinInBitonicArray {

    public static int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] > nums[mid + 1]) {
                // 转折点在 mid+1 之后，或mid+1即为最小值
                left = mid + 1;
            } else {
                // 在下降趋势中，mid前面的部分可能包含最小值
                right = mid;
            }
        }

        return nums[left];
    }

    public static void main(String[] args) {
        int[] nums = {9, 7, 5, 3, 1, 2, 4, 6, 8};
        System.out.println("最小值是: " + findMin(nums));
    }
}
