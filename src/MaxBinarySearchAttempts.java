public class MaxBinarySearchAttempts {
    public static void main(String[] args) {
        int[] array = {1, 5, 11,30, 45, 47, 47, 50};
        int maxAttempts = calculateMaxAttempts(array.length);
        System.out.println("二分查找的最大查找次数为: " + maxAttempts);
    }

    // 计算二分查找的最大次数
    public static int calculateMaxAttempts(int arrayLength) {
        // 最大查找次数就是数组长度log2的向上取整
        return (int) Math.ceil(Math.log(arrayLength) / Math.log(2));
    }
}
