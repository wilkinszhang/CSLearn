package com.wilkinszhang;

public class BigNumberSubtraction {
    public static String subtract(String a,String b){
        a=stripZero(a);
        b=stripZero(b);
        if(a.equals(b)){
            return "0";
        }
        boolean negative=false;
        if(a.length()<b.length() || (a.length()==b.length() && a.compareTo(b)<0)){
            negative=true;
            String temp=a;
            a=b;
            b=temp;
        }
        int n=a.length();
        b=String.format("%0"+n+"d",new java.math.BigInteger(b));
        StringBuilder sb=new StringBuilder(n);
        int borrow=0;
        for(int i=n-1;i>=0;i--){
            int da=a.charAt(i)-'0';
            int db=b.charAt(i)-'0';
            int diff=da-db-borrow;
            if(diff<0){
                diff+=10;
                borrow=1;
            }else{
                borrow=0;
            }
            sb.append(diff);
        }
        String res=stripZero(sb.reverse().toString());
        return negative?"-"+res:res;
    }
    private static String stripZero(String s){
        int i=0;
        while(i<s.length()-1 && s.charAt(i)=='0'){
            i++;
        }
        return s.substring(i);
    }
    public static void main(String[]args){
        String a="1234567890";
        String b="9876543210";
        System.out.println(subtract(a,b));
    }
}
