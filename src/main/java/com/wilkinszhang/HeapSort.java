package com.wilkinszhang;

//堆排序
public class HeapSort {
    public static void heapSort(int[]arr){
        int n=arr.length;
        buildMaxHeap(arr,n);
        for(int size=n;size>1;size--){
            swap(arr,0,size-1);
            heapify(arr,0,size-1);
        }
    }

    private static void buildMaxHeap(int[]arr,int n){
        for(int i=(n/2)-1;i>=0;i--){
            heapify(arr,i,n);
        }
    }

    private static void heapify(int[]arr,int i,int size){
        int largest=i;
        int left=2*i+1;
        int right=2*i+2;
        if(left<size && arr[left]>arr[largest]){
            largest=left;
        }
        if(right<size && arr[right]>arr[largest]){
            largest=right;
        }
        if(largest!=i){
            swap(arr,i,largest);
            heapify(arr,largest,size);
        }
    }

    private static void swap(int[]arr,int i,int j){
        int temp=arr[i];
        arr[i]=arr[j];
        arr[j]=temp;
    }

    public static void main(String[]args){
        int[]data={12,11,13,5,6,7};
        heapSort(data);
        for(int i=0;i<data.length;i++){
            System.out.println(data[i]);
        }
    }

}
