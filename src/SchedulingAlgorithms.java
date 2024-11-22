import java.util.*;

class Job {
    String name;
    int burstTime;
    int priority;

    public Job(String name, int burstTime, int priority) {
        this.name = name;
        this.burstTime = burstTime;
        this.priority = priority;
    }
}

public class SchedulingAlgorithms {

    public static void main(String[] args) {
        List<Job> jobs = Arrays.asList(
                new Job("A", 10, 3),
                new Job("B", 6, 5),
                new Job("C", 2, 2),
                new Job("D", 4, 1),
                new Job("E", 8, 4)
        );

        System.out.println("Average Turnaround Time for FCFS: " + fcfs(new ArrayList<>(jobs)));
        System.out.println("Average Turnaround Time for SJF: " + sjf(new ArrayList<>(jobs)));
        System.out.println("Average Turnaround Time for Priority Scheduling: " + priorityScheduling(new ArrayList<>(jobs)));
        System.out.println("Average Turnaround Time for Round Robin (Quantum = 2): " + roundRobin(new ArrayList<>(jobs), 1));
    }

    public static double fcfs(List<Job> jobs) {
        int time = 0;
        int totalTurnaround = 0;
        for (Job job : jobs) {
            time += job.burstTime;
            totalTurnaround += time;
        }
        return (double) totalTurnaround / jobs.size();
    }

    public static double sjf(List<Job> jobs) {
        jobs.sort(Comparator.comparingInt(j -> j.burstTime));
        int time = 0;
        int totalTurnaround = 0;
        for (Job job : jobs) {
            time += job.burstTime;
            totalTurnaround += time;
        }
        return (double) totalTurnaround / jobs.size();
    }

    public static double priorityScheduling(List<Job> jobs) {
        jobs.sort(Comparator.comparingInt(j -> -j.priority));
        int time = 0;
        int totalTurnaround = 0;
        for (Job job : jobs) {
            time += job.burstTime;
            totalTurnaround += time;
        }
        return (double) totalTurnaround / jobs.size();
    }

    public static double roundRobin(List<Job> jobs, int quantum) {
        int time = 0;
        int totalTurnaround = 0;
        boolean done;

        do {
            done = true;
            for (Job job : jobs) {
                if (job.burstTime > 0) {
                    done = false;
                    int timeProcessed = Math.min(job.burstTime, quantum);
                    job.burstTime -= timeProcessed;
                    time += timeProcessed;

                    if (job.burstTime == 0) {
                        totalTurnaround += time;
                    }
                }
            }
        } while (!done);

        return (double) totalTurnaround / jobs.size();
    }
}
