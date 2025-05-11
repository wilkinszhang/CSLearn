package com.wilkinszhang;

//不修改老接口Adder，不动原来调用代码，只新增一个Adapter，让他同时实现新旧两个接口。
public class AdderAdapter implements Calculator{
    private final Adder legacyAdder;

    public AdderAdapter(Adder legacyAdder){
        this.legacyAdder=legacyAdder;
    }

    @Override
    public int calculate(int a, int b, Operation op) {
        if(op==Operation.ADD){
            return legacyAdder.add(a,b);
        }
        throw new UnsupportedOperationException("not supported");
    }
}
