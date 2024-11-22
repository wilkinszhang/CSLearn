import java.util.*;

class MemoryPartition {
    int start;
    int size;

    public MemoryPartition(int start, int size) {
        this.start = start;
        this.size = size;
    }
}

public class BestFitMemoryAllocation {

    private List<MemoryPartition> freePartitions;  // 用于存储空闲分区
    private List<MemoryPartition> allocatedPartitions;  // 用于存储已分配的分区
    private int totalMemory;

    public BestFitMemoryAllocation(int totalMemory) {
        this.totalMemory = totalMemory;
        freePartitions = new ArrayList<>();
        allocatedPartitions = new ArrayList<>();
        // 初始时整个内存是一个大的空闲分区
        freePartitions.add(new MemoryPartition(0, totalMemory));
    }

    // 分配内存的方法
    public boolean allocateMemory(int size) {
        int bestIndex = -1;
        int bestFitSize = Integer.MAX_VALUE;

        // 查找最合适的空闲分区
        for (int i = 0; i < freePartitions.size(); i++) {
            MemoryPartition partition = freePartitions.get(i);
            if (partition.size >= size && partition.size < bestFitSize) {
                bestFitSize = partition.size;
                bestIndex = i;
            }
        }

        // 如果找到了合适的分区
        if (bestIndex != -1) {
            MemoryPartition bestPartition = freePartitions.get(bestIndex);

            // 记录分配的起始地址
            int allocatedStart = bestPartition.start;
            allocatedPartitions.add(new MemoryPartition(allocatedStart, size));

            // 如果分配后仍有剩余空闲空间，创建新的空闲分区
            if (bestPartition.size > size) {
                freePartitions.set(bestIndex, new MemoryPartition(bestPartition.start + size, bestPartition.size - size));
            } else {
                // 如果正好分配掉整个空闲分区，直接移除该分区
                freePartitions.remove(bestIndex);
            }

            System.out.println("分配了 " + size + "MB 内存");
            return true;
        }

        // 没有足够的空闲空间
        System.out.println("无法分配 " + size + "MB 内存");
        return false;
    }

    // 释放内存的方法（指定大小，无需指定地址）
    public void freeMemory(int size) {
        MemoryPartition partitionToFree = null;

        // 找到第一个大小匹配的已分配分区
        for (MemoryPartition allocated : allocatedPartitions) {
            if (allocated.size == size) {
                partitionToFree = allocated;
                break;
            }
        }

        if (partitionToFree == null) {
            System.out.println("没有找到大小为 " + size + "MB 的分区来释放");
            return;
        }

        // 从已分配列表中移除该分区
        allocatedPartitions.remove(partitionToFree);

        // 将该分区加回空闲分区列表
        freePartitions.add(new MemoryPartition(partitionToFree.start, partitionToFree.size));

        // 合并相邻的空闲分区
        mergeFreePartitions();

        System.out.println("释放了 " + size + "MB 内存");
    }

    // 合并相邻的空闲分区
    private void mergeFreePartitions() {
        freePartitions.sort((a, b) -> a.start - b.start);

        for (int i = 0; i < freePartitions.size() - 1; i++) {
            MemoryPartition current = freePartitions.get(i);
            MemoryPartition next = freePartitions.get(i + 1);

            if (current.start + current.size == next.start) {
                // 合并两个相邻的分区
                current.size += next.size;
                freePartitions.remove(i + 1);
                i--;
            }
        }
    }

    // 找到当前最大的空闲分区
    public int findMaxFreePartition() {
        int maxFreeSize = 0;
        for (MemoryPartition partition : freePartitions) {
            if (partition.size > maxFreeSize) {
                maxFreeSize = partition.size;
            }
        }
        return maxFreeSize;
    }

    public static void main(String[] args) {
        BestFitMemoryAllocation memory = new BestFitMemoryAllocation(185);  // 100MB 主存

        memory.allocateMemory(60);  // 分配 20MB
        memory.allocateMemory(40);  // 分配 30MB
//        memory.allocateMemory(5);  // 分配 10MB
        memory.freeMemory(60);      // 释放 20MB
        memory.allocateMemory(20);  // 分配 25MB
//        memory.allocateMemory(11);
        memory.freeMemory(40);      // 释放 30MB
        memory.allocateMemory(50);
        int maxFreePartition = memory.findMaxFreePartition();
        System.out.println("当前最大空闲分区大小为: " + maxFreePartition + "MB");
    }
}
