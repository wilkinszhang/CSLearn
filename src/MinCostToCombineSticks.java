import java.util.PriorityQueue;

public class MinCostToCombineSticks {
    /**
     * 计算将木棒合并成一根所需的最少体力
     * @param lengths 各个木棒的长度数组
     * @return 最少体力
     */
    public static int minCost(int[] lengths) {
        // 使用优先队列（最小堆）存储木棒长度
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        // 将所有木棒长度加入优先队列
        for (int length : lengths) {
            pq.offer(length);
        }

        int totalCost = 0;

        // 直到只剩下一根木棒
        while (pq.size() > 1) {
            // 取出最短的两根木棒
            int first = pq.poll();
            int second = pq.poll();

            // 合并后的新木棒长度
            int newStick = first + second;

            // 记录此次合并的代价
            totalCost += newStick;

            // 将新木棒放回优先队列
            pq.offer(newStick);
        }

        return totalCost;
    }

    public static void main(String[] args) {
        // 示例木棒长度数组
        int[] lengths = {8, 8, 3, 5, 7, 2, 10};

        // 计算最少体力
        int minCost = minCost(lengths);

        System.out.println("将七根木棒拼接成一根所需的最少体力为: " + minCost);
    }
}
