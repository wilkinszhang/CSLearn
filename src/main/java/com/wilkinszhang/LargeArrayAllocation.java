package com.wilkinszhang;

// 函数中存放一个10GB大小的数组
// -Xmx12g
public class LargeArrayAllocation {
    public static void main(String []args){
        try{
            int[]hugeArr=new int[Integer.MAX_VALUE];// 4B*21亿=8GB，Java中数组的下标是int类型，最大值为Integer.MAX_VALUE。
            hugeArr[0]=1;
            hugeArr[hugeArr.length-1]=2;
        }catch (OutOfMemoryError e){
            System.out.println("OOM");
        }
    }
}
