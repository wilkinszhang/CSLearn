package com.wilkinszhang;

//36进制加法 字节
public class Base36Addition {
    public static String addBase36(String a,String b){
        int i=a.length()-1;
        int j=b.length()-1;
        int carry=0;
        StringBuilder sb=new StringBuilder();
        while(i>=0 || j>=0 || carry!=0){
            int va=(i>=0 ? charToValue(a.charAt(i)): 0);
            int vb=(j>=0 ? charToValue(b.charAt(j)): 0);
            int sum=va+vb+carry;
            carry=sum/36;
            int digit=sum%36;
            sb.append(valueToChar(digit));
            i--;
            j--;
        }
        while(sb.length()>1 && sb.charAt(sb.length()-1)=='0'){
            sb.setLength(sb.length()-1);
        }
        return sb.reverse().toString();
    }

    private static int charToValue(char c){
        if(c>='0' && c<='9'){
            return c-'0';
        }else if(c>='a' && c<='z'){
            return c-'a'+10;
        }
        return -1;
    }

    private static char valueToChar(int v){
        if(v>=0 && v<=9){
            return (char)('0'+v);
        }else if(v>=10 && v<36){
            return (char)('a'+v-10);
        }
        return '0';
    }

    public static void main(String[] args){
        String a="1b";
        String b="2x";
        String sum=addBase36(a,b);
        System.out.println(sum);
    }
}
