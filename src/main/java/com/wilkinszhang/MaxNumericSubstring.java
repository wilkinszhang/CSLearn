package com.wilkinszhang;

//给一个由数字和字母组成的字符串，如”135abf4646dsgk2"，找最大的数字(4646)
public class MaxNumericSubstring {
    public static String getMaxNumber(String s){
        String maxNum="";
        StringBuilder curr=new StringBuilder();
        for(int i=0;i<s.length();i++){
            char c=s.charAt(i);
            if(Character.isDigit(c)){
                curr.append(c);
            }else{
                maxNum=compareAndUpdate(maxNum,curr.toString());
                curr.setLength(0);
            }
        }
        maxNum=compareAndUpdate(maxNum,curr.toString());
        return maxNum;
    }

    private static String compareAndUpdate(String a, String b){
        if(b==null || b.isEmpty()){
            return a;
        }
        if(a==null || a.isEmpty()){
            return b;
        }
        if(b.length() > a.length()){
            return b;
        }else if(b.length() < a.length()){
            return a;
        }else{
            return b.compareTo(a)>0 ? b:a;
        }
    }

    public static void main(String[]args){
        String input="123abf4645dsgk2";
        String res=getMaxNumber(input);
        System.out.println(res);
    }

}
