public class RoundRobinScheduler {
    /**
     * 计算时间片轮转调度策略中的额外开销百分比
     * @param n 进程数
     * @param x 每个时间片的持续时间（毫秒）
     * @param t 每次CPU切换的开销时间（毫秒）
     * @return 额外开销的百分比
     */
    public static double calculateOverheadPercentage(int n, int x, int t) {
        // 总开销
        double totalOverhead = n * t;

        // 总运行时间
        double totalRuntime = n * x;

        // 开销百分比
        double overheadPercentage = (totalOverhead / (totalRuntime + totalOverhead)) * 100;

        return overheadPercentage;
    }

    public static void main(String[] args) {
        int n = 40; // 进程数
        int x = 300; // 每个时间片的时间（毫秒）
        int t = 15;  // CPU切换时间（毫秒）

        double overheadPercentage = calculateOverheadPercentage(n, x, t);
        System.out.printf("额外开销所占的百分比为: %.2f%%\n", overheadPercentage);
    }
}
