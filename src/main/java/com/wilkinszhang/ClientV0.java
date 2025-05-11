package com.wilkinszhang;

public class ClientV0 {
    private Adder adder=new SimpleAdder();

    public void doWork(){
        int sum=adder.add(2,3);
        System.out.println("sum= "+sum);
    }
}
