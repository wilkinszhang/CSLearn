class Process {
    int id; // 进程ID
    int arrivalTime; // 到达时间
    int burstTime; // 运行时间（服务时间）
    int remainingTime; // 剩余运行时间
    int completionTime; // 完成时间
    int turnAroundTime; // 周转时间
    double weightedTurnAroundTime; // 带权周转时间

    public Process(int id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.completionTime = 0;
    }
}