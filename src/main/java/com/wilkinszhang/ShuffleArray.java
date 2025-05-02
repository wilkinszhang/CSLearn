package com.wilkinszhang;

import java.util.Random;

//数组乱序
public class ShuffleArray {
    private static final Random rnd=new Random();

    public static void shuffle(int[]arr){
        int n=arr.length;
        for(int i=n-1;i>0;i--){
            int j=rnd.nextInt(i+1);
            int temp=arr[i];
            arr[i]=arr[j];
            arr[j]=temp;
        }
    }

    public static void main(String[]args){
        int[]data={1,2,3,4,5};
        shuffle(data);
        for(int i=0;i<data.length;i++){
            System.out.print(data[i]+ " ");
        }
    }
}
