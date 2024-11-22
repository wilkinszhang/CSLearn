import java.util.*;

public class PreemptiveSJF {
    public static void main(String[] args) {
        List<Process> processes = new ArrayList<>();
        // 假设进程到达时间和运行时间如下：
        processes.add(new Process(1, 0, 7));
        processes.add(new Process(2, 2, 4));
        processes.add(new Process(3, 4, 1));
        processes.add(new Process(4, 5, 4));

        int currentTime = 0;
        int completedProcesses = 0;
        int n = processes.size();
        Process currentProcess = null;

        while (completedProcesses < n) {
            // 找到当前最短剩余时间的进程
            for (Process p : processes) {
                if (p.arrivalTime <= currentTime && p.remainingTime > 0) {
                    if (currentProcess == null || p.remainingTime < currentProcess.remainingTime) {
                        currentProcess = p;
                    }
                }
            }

            if (currentProcess != null) {
                // 执行一个时间单位
                currentProcess.remainingTime--;
                currentTime++;
                // 检查进程是否完成
                if (currentProcess.remainingTime == 0) {
                    currentProcess.completionTime = currentTime;
                    currentProcess.turnAroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
                    currentProcess.weightedTurnAroundTime = (double)currentProcess.turnAroundTime / currentProcess.burstTime;
                    completedProcesses++;
                    currentProcess = null; // 重置当前进程
                }
            } else {
                // 如果没有找到可运行的进程，则时间推进
                currentTime++;
            }
        }

        // 计算平均周转时间和平均带权周转时间
        double avgTurnAroundTime = 0;
        double avgWeightedTurnAroundTime = 0;

        System.out.println("进程ID\t完成时间\t周转时间\t带权周转时间");

        for (Process p : processes) {
            System.out.printf("%d\t%.2f\t%.2f\t%.2f\n",
                    p.id,
                    (double)p.completionTime,
                    (double)p.turnAroundTime,
                    p.weightedTurnAroundTime);

            avgTurnAroundTime += p.turnAroundTime;
            avgWeightedTurnAroundTime += p.weightedTurnAroundTime;
        }

        avgTurnAroundTime /= n;
        avgWeightedTurnAroundTime /= n;

        System.out.printf("\n平均周转时间: %.2f", avgTurnAroundTime);
        System.out.printf("\n平均带权周转时间: %.2f\n", avgWeightedTurnAroundTime);
    }
}