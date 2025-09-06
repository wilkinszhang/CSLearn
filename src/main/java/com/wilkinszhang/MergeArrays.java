package com.wilkinszhang;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

//两个数组合并得到去重后的数组
public class MergeArrays {
    public static int[]mergeUnique(int[]a,int[]b){
        Set<Integer> set=new LinkedHashSet<>();
        for(int x:a){
            set.add(x);
        }
        for(int y:b){
            set.add(y);
        }
        int[]res=new int[set.size()];
        int i=0;
        for(int x:set){
            res[i++]=x;
        }
        return res;
    }

    public static void main(String[]args){
        int[]a={1,3,2,4,3};
        int[]b={3,5,6,2};
        int[]merged=mergeUnique(a,b);
        System.out.println(Arrays.toString(merged));
    }
}
