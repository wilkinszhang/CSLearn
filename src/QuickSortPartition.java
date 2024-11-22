public class QuickSortPartition {
    public static void main(String[] args) {
        int[] array = {56, 12, 34, 33, 50, 46, 39, 79,1}; // 示例数组
        System.out.println("Original array:");
        printArray(array);

        // 进行一次划分
        int index = partition(array, 0, array.length - 1);
        System.out.println("Array after one partition around pivot at index 0:");
        printArray(array);
        System.out.println("New pivot position is at index: " + index);
    }

    private static int partition(int[] array, int low, int high) {
        int pivot = array[low];  // 使用第一个元素作为基准
        int i = low;  // 从第一个元素后开始考虑
        int j = high;

        while (i < j) {
            // 从左向右找到第一个大于等于基准的数
            while (i < high && array[i] <= pivot) {
                i++;
            }
            // 从右向左找到第一个小于等于基准的数
            while (array[j] > pivot) {
                j--;
            }
            if (i < j) {
                swap(array, i, j);  // 交换这两个元素
            }
        }

        // 把基准放到正确的位置（j）
        swap(array, low, j);
        return j;  // 返回基准现在的位置
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private static void printArray(int[] array) {
        for (int value : array) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
}
