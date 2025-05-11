package com.wilkinszhang;

public class ClientV1 {
    private Calculator calculator;

    public ClientV1(Calculator calc){
        this.calculator=calc;
    }

    public void doWork(){
        int sum=calculator.calculate(2,3,Operation.ADD);
    }
}
