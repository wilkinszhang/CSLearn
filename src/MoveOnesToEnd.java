public class MoveOnesToEnd {

    public static void moveOnes(int[] nums) {
        int j = 0; // 指向当前要放置非1元素的位置

        // 将所有非1元素移到数组前面
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 1) {
                nums[j] = nums[i];
                j++;
            }
        }

        // 将剩余位置全部置为1
        while (j < nums.length) {
            nums[j] = 1;
            j++;
        }
    }

    public static void main(String[] args) {
        int[] nums = {0, 1, 2, 1, 3, 1, 4};
        moveOnes(nums);
        for (int num : nums) {
            System.out.print(num + " ");
        }
    }
}
