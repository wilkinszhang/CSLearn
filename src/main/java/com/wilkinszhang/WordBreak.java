package com.wilkinszhang;

// 力扣，单词拆分，每个单词只能用一次
/*
给你一个字符串s和一个字符串列表wordDict作为字典，如果可以用字典中出现的一个或多个单词拼接处s则返回true。
不要求字典中单词全部都使用，且字典中单词只能用一次
*/
public class WordBreak {
    public static boolean canForm(String s,String[]dict){
        int n=dict.length;
        int totalLen=0;
        for(String w:dict){
            totalLen+=w.length();
        }
        if(totalLen<s.length()){
            return false;
        }
        boolean[]used=new boolean[n];
        return dfs(s,dict,0,used);
    }

    private static boolean dfs(String s,String[]dict,int pos,boolean[]used){
        if(pos==s.length()){
            return true;
        }
        for(int i=0;i<dict.length;i++){
            if(!used[i]){
                String w=dict[i];
                if(pos+w.length()<=s.length()&& s.startsWith(w,pos)){
                    return true;
                }
                used[i]=false;
            }
        }
        return false;
    }

    public static void main(String[]args){
        String[]dict={"apple","pen"};
        System.out.println(canForm("applepenapple",dict));
    }
}
