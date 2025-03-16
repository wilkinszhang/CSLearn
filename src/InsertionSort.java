public class InsertionSort {
    public static int insertionSortWithComparisonCount(int[] arr) {
        int comparisonCount = 0; // 用于记录比较次数

        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;

            // 比较当前元素与已排序部分的元素，并移动较大的元素
            while (j >= 0) {
                comparisonCount++; // 每次比较都记录
                if (arr[j] > key) {
                    arr[j + 1] = arr[j];
                    j--;
                } else {
                    break; // 找到了合适的插入位置
                }
            }

            arr[j + 1] = key; // 插入元素
        }

        return comparisonCount;
    }

    public static void main(String[] args) {
        // 示例输入序列
        int[] arr1 = {32,4,9,3,1};
        int comparisons1 = insertionSortWithComparisonCount(arr1);

        System.out.println("排序后的数组:");
        for (int num : arr1) {
            System.out.print(num + " ");
        }
        System.out.println("\n比较次数: " + comparisons1);

        // 测试更多样例
        int[] arr2 = {1, 2, 3, 4, 5}; // 已排序的情况
        int comparisons2 = insertionSortWithComparisonCount(arr2);
        System.out.println("\n已排序数组的比较次数: " + comparisons2);

        int[] arr3 = {5, 4, 3, 2, 1}; // 逆序的情况
        int comparisons3 = insertionSortWithComparisonCount(arr3);
        System.out.println("逆序数组的比较次数: " + comparisons3);
    }
}