package com.wilkinszhang;

public class ClientNew {
    private Calculator calculator=new AdderAdapter(new SimpleAdder());

    public void doMore(){
        int diff=calculator.calculate(5,2,Operation.SUBTRACT);
    }
}
