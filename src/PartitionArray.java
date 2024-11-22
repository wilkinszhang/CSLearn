import java.util.Arrays;

public class PartitionArray {

    private static int minDifference = Integer.MAX_VALUE;  // 用于保存最小差值

    public static int minPartitionDifference(int[] nums) {
        int totalSum = Arrays.stream(nums).sum();  // 数组的总和
        boolean[] used = new boolean[nums.length];  // 记录哪些元素已被使用

        findMinDifference(nums, used, 0, 0, 0, totalSum / 2);
        return minDifference;
    }

    // 回溯函数，用于生成子集并计算差值
    private static void findMinDifference(int[] nums, boolean[] used, int index, int currentSum, int count, int target) {
        if (count == nums.length / 2) {  // 如果子集长度为 n
            int otherSum = Arrays.stream(nums).sum() - currentSum;
            minDifference = Math.min(minDifference, Math.abs(currentSum - otherSum));
            return;
        }

        // 剪枝条件，如果 index 超出数组长度则返回
        if (index >= nums.length) {
            return;
        }

        // 包含当前元素
        used[index] = true;
        findMinDifference(nums, used, index + 1, currentSum + nums[index], count + 1, target);
        used[index] = false;

        // 不包含当前元素
        findMinDifference(nums, used, index + 1, currentSum, count, target);
    }

    public static void main(String[] args) {
        int[] nums = {1, 6, 4, 2, 5, 3};
        int result = minPartitionDifference(nums);
        System.out.println("最小差值: " + result);
    }
}
