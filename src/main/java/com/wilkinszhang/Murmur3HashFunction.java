package com.wilkinszhang;

import com.google.common.hash.Hashing;

public class Murmur3HashFunction implements HashFunction {
    public int hash(Object key) {
        return Hashing.murmur3_32().hashBytes(key.toString().getBytes()).asInt();
    }
}