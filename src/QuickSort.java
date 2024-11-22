public class QuickSort {
    // 用于调用快速排序的公共方法
    public static void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    // 实现快速排序的主要方法，使用递归
    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            // partitionIndex是分割后基准元素的正确位置
            int partitionIndex = partition(arr, low, high);

            // 递归调用快速排序，对低子表进行快速排序
            quickSort(arr, low, partitionIndex - 1);
            // 递归调用快速排序，对高子表进行快速排序
            quickSort(arr, partitionIndex + 1, high);
        }
    }

    // 分区函数，选取基准元素，进行分区
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[low];
        while (low < high) {
            while (low < high && arr[high] >= pivot) {
                high--;
            }
            arr[low] = arr[high];
            while (low < high && arr[low] <= pivot) {
                low++;
            }
            arr[high] = arr[low];
        }
        arr[low] = pivot;
        return low;
    }

    // 主函数，用于测试快速排序
    public static void main(String[] args) {
        int[] arr = {3, 4, 86, 29, 74, 61,68,27,31};
        quickSort(arr);
        System.out.println("Sorted array: ");
        for (int value : arr) {
            System.out.print(value + " ");
        }
    }
}
