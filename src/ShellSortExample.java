public class ShellSortExample {
    public static void main(String[] args) {
        int[] array = {16,25,12,30,47,11,23,36,9,18,31};  // 示例数组
        int n = array.length;
        int gap = 5;  // 设定增量d为5

        // 进行一趟希尔排序
        for (int i = gap; i < n; i++) {
            int temp = array[i];
            int j;
            for (j = i; j >= gap && array[j - gap] > temp; j -= gap) {
                array[j] = array[j - gap];
            }
            array[j] = temp;
        }

        // 输出排序后的数组
        for (int num : array) {
            System.out.print(num + " ");
        }
    }
}
