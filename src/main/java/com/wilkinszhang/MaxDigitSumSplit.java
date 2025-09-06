package com.wilkinszhang;

//求一个数的两个加数，需要满足两个加数的各个位的数字和最大
public class MaxDigitSumSplit {
    public static void main(String[]args){
        long N=123456;
        long []ans=splitMaxDigitSum(N);
        System.out.println(ans[0]+" "+ans[1]);
    }

    public static long[]splitMaxDigitSum(long N){
        String s=Long.toString(N);
        int L=s.length();
        int[]n=new int[L];
        for(int i=0;i<L;i++){
            n[i]=s.charAt(L-1-i)-'0';
        }
        long[][]dp=new long[L+1][2];
        int[][]choiceA=new int[L][2],choiceB=new int[L][2];
        final long NEG_INF=Long.MIN_VALUE/4;
        dp[L][0]=0;
        dp[L][1]=NEG_INF;
        for(int i=L-1;i>=0;i--){
            for(int carryIn=0;carryIn<=1;carryIn++){
                long best=NEG_INF;
                int bestA=0,bestB=0;
                for(int a=0;a<=9;a++){
                    for(int b=0;b<=9;b++){
                        int sum=a+b+carryIn;
                        if(sum%10==n[i]){
                            int carryOut=sum/10;
                            long val=a+b+dp[i+1][carryOut];
                            if(val>best){
                                best=val;
                                bestA=a;
                                bestB=b;
                            }
                        }
                    }
                }
                dp[i][carryIn]=best;
                choiceA[i][carryIn]=bestA;
                choiceB[i][carryIn]=bestB;
            }
        }
        StringBuilder A=new StringBuilder();
        StringBuilder B=new StringBuilder();
        int carry=0;
        for(int i=0;i<L;i++){
            int a=choiceA[i][carry],b=choiceB[i][carry];
            int sum=a+b+carry;
            carry=sum/10;
            A.append(a);
            B.append(b);
        }
        if(carry==1){
            A.append(1);
            B.append(0);
        }
        A.reverse();
        B.reverse();
        long aVal=Long.parseLong(A.toString());
        long bVal=Long.parseLong(B.toString());
        return new long[]{aVal,bVal};
    }
}
