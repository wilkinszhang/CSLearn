package com.wilkinszhang;

import java.util.HashMap;
import java.util.Map;

// key value解析器，输入aa=bdd&c12=b，输出{aa=bdd, c12=b}，输入&&&==a=b&x=1&y=&=z&&k=v==extra，输出{x=1, k=v==extra}
public class KeyValueParser {
    public static Map<String,String> parseToMap(String input){
        Map<String,String>map=new HashMap<>();
        if(input==null || input.isEmpty()){
            return map;
        }
        String[]tokens=input.split("&");
        for(String token:tokens){
            if(token==null||token.isEmpty()){
                continue;
            }
            int idx=token.indexOf('=');
            if(idx<=0 || idx==token.length()-1){
                continue;
            }
            String key=token.substring(0,idx).trim();
            String value=token.substring(idx+1).trim();
            if(key.isEmpty() || value.isEmpty()){
                continue;
            }
            map.put(key,value);
        }
        return map;
    }

    public static void main(String[]args){
        String s1="aa=bdd&c12=b";
        System.out.println(parseToMap(s1));
        String s2="&&&==a=b&x=1&y=&=z&&k=v==extra";
        System.out.println(parseToMap(s2));
    }
}
