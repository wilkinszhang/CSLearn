package com.wilkinszhang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 用Java实现类似python中的string分割的功能
public class StringSplitter {
    public static String[]split(String s){
        return split(s,null,-1);
    }

    public static String[]split(String s,String sep){
        return split(s,sep,-1);
    }

    public static String[]split(String s,String sep,int maxSplit){
        List<String> parts=new ArrayList<>();
        int len=s.length();
        int i=0,splits=0;
        if(sep==null || sep.isEmpty()){
            while(i<len){
                while(i<len && Character.isWhitespace(s.charAt(i))){
                    i++;
                }
                if(i>=len)break;
                if(maxSplit>=0 && splits>=maxSplit){
                    parts.add(s.substring(i));
                    return parts.toArray(new String[0]);
                }
                int j=i;
                while(j<len && !Character.isWhitespace(s.charAt(j))){
                    j++;
                }
                parts.add(s.substring(i,j));
                splits++;
                i=j;
            }
        }else{
            int sepLen=sep.length();
            while(i<=len){
                if(maxSplit>=0 && splits>=maxSplit){
                    parts.add(s.substring(i));
                    break;
                }
                int idx=s.indexOf(sep,i);
                if(idx<0){
                    parts.add(s.substring(i));
                    break;
                }
                parts.add(s.substring(i,idx));
                splits++;
                i=idx+sepLen;
            }
        }
        return parts.toArray(new String[0]);
    }

    public static void main(String[]args){
        String text=" Hello    world     this is     Java";
        System.out.println(Arrays.toString(split(text)));
        System.out.println(Arrays.toString(split("a--b--c--d","--")));
        System.out.println(Arrays.toString(split("a--b--c--d","--",2)));
    }
}
