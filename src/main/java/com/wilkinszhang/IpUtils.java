package com.wilkinszhang;

// 将IP地址转化为整数
public class IpUtils {
    public static long ipToLong(String ipAddr){
        if(ipAddr==null || ipAddr.isEmpty()){
            return -1;
        }
        String []parts=ipAddr.split("\\.");
        if(parts.length!=4){
            return -1;
        }
        long res=0;
        for(int i=0;i<parts.length;i++){
            String part=parts[i];
            int byteValue;
            try{
                byteValue=Integer.parseInt(part);
            }catch(NumberFormatException e){
                throw new IllegalArgumentException();
            }
            if(byteValue<0 || byteValue>255){
                throw new IllegalArgumentException();
            }
            res|=(long)byteValue<<((3-i)*8);
        }
        return res;
    }

    public static void main(String []args){
        String ip="192.168.0.1";
        System.out.println(ipToLong(ip));
    }
}
