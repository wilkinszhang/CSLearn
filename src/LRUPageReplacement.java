import java.util.LinkedList;
import java.util.HashSet;

public class LRUPageReplacement {
    // 页面访问序列和内存大小作为参数
    public static int countPageFaults(int[] arr, int n) {
        // 使用 LinkedList 来存储当前在内存中的页面
        LinkedList<Integer> memory = new LinkedList<>();
        // 使用 HashSet 来快速查找页面是否存在于内存中
        HashSet<Integer> set = new HashSet<>();
        // 缺页次数
        int pageFaults = 0;

        for (int page : arr) {
            if (!set.contains(page)) {
                // 页面不在内存中，发生缺页
                pageFaults++;
                // 检查内存是否已满
                if (memory.size() == n) {
                    // 内存已满，移除最久未使用的页面
                    int oldest = memory.removeFirst();
                    set.remove(oldest);
                }
                // 添加新页面到内存
                memory.addLast(page);
                set.add(page);
            } else {
                // 页面已在内存中，移动此页面到队列末尾
                memory.remove((Integer) page);
                memory.addLast(page);
            }
        }
        return pageFaults;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 5, 3, 4, 6, 1, 4, 3, 9, 6, 7, 8, 3 , 4};  // 页面访问序列
        int n = 5;  // 内存大小
        System.out.println("缺页次数: " + countPageFaults(arr, n));
    }
}
