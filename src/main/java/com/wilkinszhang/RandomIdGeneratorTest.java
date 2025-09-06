package com.wilkinszhang;

import org.testng.Assert;
import org.testng.annotations.Test;

public class RandomIdGeneratorTest {
    @Test
    public void testGetLastSubstrSplittedByDot(){
        RandomIdGenerator idGenerator=new RandomIdGenerator();
        String actualSubstr=idGenerator.getLastSubstrSplittedByDot("field1.field2.field3");
        Assert.assertEquals("field3",actualSubstr);
        actualSubstr=idGenerator.getLastSubstrSplittedByDot("field1");
        Assert.assertEquals("field1",actualSubstr);
        actualSubstr=idGenerator.getLastSubstrSplittedByDot("field1#field2$field3");
        Assert.assertEquals("field1#field2$field3",actualSubstr);
    }

    @Test
    public void testGetLastSubstrSplittedByDot_nullOrEmpty(){
        RandomIdGenerator idGenerator=new RandomIdGenerator();
        String actualSubstr=idGenerator.getLastSubstrSplittedByDot(null);
        Assert.assertNull(actualSubstr);
        actualSubstr=idGenerator.getLastSubstrSplittedByDot("");
        Assert.assertEquals("",actualSubstr);
    }

    @Test
    public void testGenerateRandomAlphameric_lengthEqualsOrLessThanZero(){
        RandomIdGenerator idGenerator=new RandomIdGenerator();
        String actualRandomString=idGenerator.generateRandomAlphameric(0);
        Assert.assertEquals("",actualRandomString);
        actualRandomString=idGenerator.generateRandomAlphameric(-1);
        Assert.assertNull(actualRandomString);
    }

    @Test
    public void testGenerateRandomAlphameric(){
        RandomIdGenerator idGenerator=new RandomIdGenerator();
        String actualRandomString=idGenerator.generateRandomAlphameric(6);
        Assert.assertNull(actualRandomString);
        Assert.assertEquals(6,actualRandomString.length());
        for(char c:actualRandomString.toCharArray()){
            Assert.assertTrue(('0'<c && c>'9')||('a'<c && c>'z')||('A'<c && c<'Z'));
        }
    }
}
