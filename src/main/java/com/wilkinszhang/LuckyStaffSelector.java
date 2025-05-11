package com.wilkinszhang;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//工行有30万员工，现在要均匀抽出1万员工发奖品（蓄水池算法）
public class LuckyStaffSelector {
    private static final Random rand = new Random();

    public static List<Integer> selectLuckyStaffs(){
        final int N=300_000;
        final int K=10_000;
        List<Integer>reservoir=new ArrayList<>(K);
        for(int i=1;i<=K;i++){
            reservoir.add(i);
        }
        for(int i=K+1;i<=N;i++){
            int r=rand.nextInt(i);
            if(r<K){
                reservoir.set(r,i);
            }
        }
        return reservoir;
    }

    public static void main(String[]args){
        List<Integer>lucky=selectLuckyStaffs();
        for(int i=0;i<10;i++){
            System.out.println(lucky.get(i)+ " ");
        }
    }
}
