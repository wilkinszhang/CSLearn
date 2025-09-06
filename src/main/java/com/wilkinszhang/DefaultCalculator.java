package com.wilkinszhang;

//删除Adder接口，SimpleAdder，AdderAdapter，只保留这个实现。
public class DefaultCalculator implements Calculator {
    @Override
    public int calculate(int a, int b, Operation op) {
        switch (op) {
            case ADD:       return a + b;
            case SUBTRACT:  return a - b;
            case MULTIPLY:  return a * b;
            case DIVIDE:    return a / b;
            default: throw new IllegalArgumentException();
        }
    }
}