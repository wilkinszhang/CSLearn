package com.wilkinszhang;

import java.util.ArrayList;
import java.util.List;

public class MinHeap {
    private List<Integer> heap;
    private int swapCount = 0; // 用来记录交换次数

    public MinHeap(List<Integer> elements) {
        this.heap = new ArrayList<>(elements);
        buildMinHeap();
    }

    private void buildMinHeap() {
        for (int i = (heap.size() / 2) - 1; i >= 0; i--) {
            heapifyDownMin(i);
        }
    }

    private void heapifyDownMin(int index) {
        int size = heap.size();
        int smallest = index;

        while (true) {
            int left = 2 * index + 1; // 左子节点的索引
            int right = 2 * index + 2; // 右子节点的索引

            if (left < size && heap.get(left) < heap.get(smallest)) {
                smallest = left;
            }

            if (right < size && heap.get(right) < heap.get(smallest)) {
                smallest = right;
            }

            if (smallest != index) {
                swap(smallest, index);
                index = smallest;
            } else {
                break;
            }
        }
    }

    public void convertToMaxHeap() {
        for (int i = (heap.size() / 2) - 1; i >= 0; i--) {
            heapifyDownMax(i);
        }
    }

    private void heapifyDownMax(int index) {
        int size = heap.size();
        int largest = index;

        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;

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

    private void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
        swapCount++; // 每次交换时增加计数
    }

    public int getSwapCount() {
        return swapCount;
    }

    @Override
    public String toString() {
        return heap.toString();
    }

    public static void main(String[] args) {
        List<Integer> elements = new ArrayList<>();
        elements.add(36);
        elements.add(16);
        elements.add(20);
        elements.add(8);
        elements.add(40);
        MinHeap minHeap = new MinHeap(elements);

        System.out.println("Initial com.wilkinszhang.MinHeap: " + minHeap);
        minHeap.convertToMaxHeap();
        System.out.println("Converted to com.wilkinszhang.MaxHeap: " + minHeap);
        System.out.println("Total swaps made: " + minHeap.getSwapCount());
    }
}
