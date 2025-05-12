package com.wilkinszhang;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

public class RandomIdGenerator implements IdGenerator{
    private static final Logger logger= LoggerFactory.getLogger(RandomIdGenerator.class);

    @Override
    public String generate() throws Exception {
        String substrOfHostName=getLastfieldOfHostName();
        if(substrOfHostName ==null || substrOfHostName.isEmpty()){
            throw new Exception("host name is empty");
        }
        long currTimeMillis=System.currentTimeMillis();
        String randomString=generateRandomAlphameric(8);
        String id=String.format("%s-%d-%s",substrOfHostName,currTimeMillis,randomString);
        return id;
    }

    @VisibleForTesting
    protected String generateRandomAlphameric(int length) {
        char[]randomChars=new char[length];
        int cnt=0;
        Random random=new Random();
        while(cnt<length){
            int maxAscii='z';
            int randomAscii=random.nextInt(maxAscii);
            boolean isDigit=randomAscii>='0' && randomAscii<='9';
            boolean isUppercase=randomAscii>='A' && randomAscii<='Z';
            boolean isLowercase=randomAscii>='a' && randomAscii<='z';
            if(isDigit || isUppercase || isLowercase){
                randomChars[cnt]=(char)(randomAscii);
                cnt++;
            }
        }
        return new String(randomChars);
    }

    private String getLastfieldOfHostName() {
        String substrOfHostName=null;
        try{
            String hostName=InetAddress.getLocalHost().getHostName();
            substrOfHostName=getLastSubstrSplittedByDot(hostName);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        return substrOfHostName;
    }

    @VisibleForTesting
    protected String getLastSubstrSplittedByDot(String hostName){
        String[]tokens=hostName.split("\\.");
        String substrOfHostName=tokens[tokens.length-1];
        return substrOfHostName;
    }
}
