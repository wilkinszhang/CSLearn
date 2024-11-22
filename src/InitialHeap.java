import java.util.Arrays;

public class InitialHeap {
    /**
     * 建立最大堆
     * @param arr 待排序列数组
     */
    public static void buildMaxHeap(int[] arr) {
        int n = arr.length;

        // 从最后一个非叶子节点开始，依次进行下滤操作
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
    }

    /**
     * 下滤操作，维护最大堆的性质
     * @param arr 数组
     * @param n 数组的长度
     * @param i 当前节点的索引
     */
    private static void heapify(int[] arr, int n, int i) {
        int largest = i; // 先假设当前节点是最大值
        int left = 2 * i + 1; // 左子节点
        int right = 2 * i + 2; // 右子节点

        // 如果左子节点存在且大于当前节点，则更新最大值
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        // 如果右子节点存在且大于当前最大值，则更新最大值
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        // 如果最大值不是当前节点，则进行交换，并继续调整子树
        if (largest != i) {
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;

            // 递归地对交换位置的子树进行下滤
            heapify(arr, n, largest);
        }
    }

    public static void main(String[] args) {
        // 示例数组
        int[] arr = {32, 14, 51, 28, 37, 49, 72};

        // 建立初始堆
        buildMaxHeap(arr);

        System.out.println("初始最大堆为: " + Arrays.toString(arr));
    }
}
