package com.wilkinszhang;

import java.util.Objects;

//重写了 equals 为什么还要重写 hashcode 方法
public class EqualsHashcodeTest {
    private int value1;
    private int value2;
    public EqualsHashcodeTest(int value1,int value2){
        this.value1=value1;
        this.value2=value2;
    }
    @Override
    public boolean equals(Object obj){
        if(this==obj)return true;
        if(obj==null || getClass()!=obj.getClass())return false;
        EqualsHashcodeTest my=(EqualsHashcodeTest) obj;
        return value1==my.value1 && value2==my.value2;
    }
    @Override
    public int hashCode(){
        return Objects.hash(value1,value2);
    }
}
