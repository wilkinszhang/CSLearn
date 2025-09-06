package com.wilkinszhang;

public class SimulateCAS {
    private volatile int count;
    public void addOne(){
        int newValue;
        do{
            newValue=count+1;
        }while(count!=cas(count,newValue));
    }
    private synchronized int cas(int expect,int newValue){
        int currValue=count;
        if(currValue==expect){
            count=newValue;
        }
        return currValue;
    }
}
