package com.wilkinszhang;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// 随机红包功能。给定一个金额和人数，生成一个数组，表示每人分得的金钱值。要求最大金额不能超过总金额的90%且全部分完。
public class RedPacket {
    private static final Random RNG=new Random();

    public static List<BigDecimal> split(BigDecimal totalMoney,int cnt){
        if(cnt<=0){
            throw new IllegalArgumentException("people must big than zero");
        }
        long totalCents=totalMoney.multiply(BigDecimal.valueOf(100)).longValueExact();
        BigDecimal threshold=totalMoney.multiply(BigDecimal.valueOf(0.9));
        while(true){
            double[]weights=new double[cnt];
            double sumW=0;
            for(int i=0;i<cnt;i++){
                weights[i]=RNG.nextDouble();
                sumW+=weights[i];
            }
            long[]centsArr=new long[cnt];
            long allocated=0;
            for(int i=0;i<cnt;i++){
                if(i==cnt-1){
                    centsArr[i]=totalCents-allocated;
                }else{
                    centsArr[i]=Math.round(weights[i]/sumW*totalCents);
                    allocated+=centsArr[i];
                }
            }
            boolean ok=true;
            for(long c:centsArr){
                BigDecimal money=BigDecimal.valueOf(c).divide(BigDecimal.valueOf(100),2, RoundingMode.DOWN);
                if(money.compareTo(threshold)>0){
                    ok=false;
                    break;
                }
            }
            if(ok){
                List<BigDecimal>res=new ArrayList<>(cnt);
                for(long c:centsArr){
                    res.add(
                            BigDecimal.valueOf(c).divide(BigDecimal.valueOf(100),2,RoundingMode.DOWN)
                    );
                }
                return res;
            }
        }
    }

    public static void main(String [] args){
        BigDecimal total=new BigDecimal("100.00");
        int people=5;
        List<BigDecimal> list=split(total,people);
        System.out.println(list);
        BigDecimal sum=list.stream().reduce(BigDecimal.ZERO,BigDecimal::add);
        System.out.println(sum);
        System.out.println(list.stream().max(BigDecimal::compareTo).get());
    }
}
