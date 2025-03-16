package com.wilkinszhang;

//import redis.clients.jedis.Jedis;
public class SlidingWindowRateLimiter {
//    private Jedis jedis;
    private String key;
    private int windowSizeInSeconds;
    private int maxRequests;

//    public com.wilkinszhang.SlidingWindowRateLimiter(Jedis jedis, String key, int windowSizeInSeconds, int maxRequests) {
//        this.jedis = jedis;
//        this.key = key;
//        this.windowSizeInSeconds = windowSizeInSeconds;
//        this.maxRequests = maxRequests;
//    }
//
//    public boolean isAllowed() {
//        long currentTime = System.currentTimeMillis() / 1000; // 当前时间戳（秒）
//        long windowStart = currentTime - windowSizeInSeconds; // 窗口开始时间
//
//        // 删除过期的请求记录
//        jedis.zremrangeByScore(key, 0, windowStart);
//
//        // 统计当前窗口内的请求次数
//        long requestCount = jedis.zcard(key);
//
//        if (requestCount < maxRequests) {
//            // 允许请求，记录当前请求时间
//            jedis.zadd(key, currentTime, String.valueOf(currentTime));
//            return true;
//        } else {
//            // 请求被限流
//            return false;
//        }
//    }
}