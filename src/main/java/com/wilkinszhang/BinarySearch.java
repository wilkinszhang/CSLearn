package com.wilkinszhang;

public class BinarySearch {
    public static int binarySearch(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;
        int count = 0;  // 记录比较次数

        while (left <= right) {
            int mid = left + (right - left) / 2;
            count++;  // 每次比较计数加一

            if (array[mid] == target) {
                System.out.println("比较次数: " + count);
                return mid; // 找到目标，返回其索引
            } else if (array[mid] < target) {
                left = mid + 1; // 调整左边界
            } else {
                right = mid - 1; // 调整右边界
            }
        }

        System.out.println("比较次数: " + count);
        return -1; // 未找到目标，返回-1
    }

    public static void main(String[] args) {
        int[] array = {4, 5, 6, 7, 9, 12,18,23};
        int target = 9;
        int result = binarySearch(array, target);

        if (result != -1) {
            System.out.println("元素位置: " + result);
        } else {
            System.out.println("元素未找到");
        }
    }
}
