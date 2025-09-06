package com.wilkinszhang;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class CacheDeleteConsumer {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    private static final int MAX_RETRY=5;

    @RabbitListener(queues = "cache-delete-queue")
    public void handleDelete(String key) throws InterruptedException{
        int retries=0;
        while(retries< MAX_RETRY){
            try {
                redisTemplate.delete(key);
                break;
            } catch (Exception e){
                retries++;
                Thread.sleep(100*(1<<retries));
            }
        }
    }
}
