import java.util.ArrayList;
import java.util.List;

public class MaxHeap {
    private List<Integer> heap;

    public MaxHeap() {
        this.heap = new ArrayList<>();
    }

    // 使用一个列表初始化大根堆
    public MaxHeap(List<Integer> elements) {
        this.heap = new ArrayList<>(elements);
        buildHeap();
    }

    // 建立大根堆
    private void buildHeap() {
        for (int i = (heap.size() / 2) - 1; i >= 0; i--) {
            heapifyDown(i);
        }
    }

    // 插入元素
    public void insert(int element) {
        heap.add(element);
        heapifyUp(heap.size() - 1);
    }

    // 从下往上调整
    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap.get(index) > heap.get(parentIndex)) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    // 从上往下调整
    private void heapifyDown(int index) {
        int size = heap.size();
        int largest = index;

        while (true) {
            int left = 2 * index + 1; // 左子节点的索引
            int right = 2 * index + 2; // 右子节点的索引

            if (left < size && heap.get(left) > heap.get(largest)) {
                largest = left;
            }

            if (right < size && heap.get(right) > heap.get(largest)) {
                largest = right;
            }

            if (largest != index) {
                swap(largest, index);
                index = largest;
            } else {
                break;
            }
        }
    }

    // 交换堆中的两个元素
    private void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    // 获取堆顶元素
    public int getMax() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        return heap.get(0);
    }

    @Override
    public String toString() {
        return heap.toString();
    }

    // 主方法进行测试
    public static void main(String[] args) {
        List<Integer> elements = new ArrayList<>();
        elements.add(36);
        elements.add(16);
        elements.add(20);
        elements.add(8);
        elements.add(40);

        MaxHeap maxHeap = new MaxHeap(elements);
        System.out.println("Initial Heap: " + maxHeap);
        System.out.println("Maximum element: " + maxHeap.getMax());
    }
}