package com.wilkinszhang;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
public class RedisMySQLDataConsistency {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private CacheDeleteProducer cacheDeleteProducer;
    @Autowired
    private RedissonClient redissonClient;

    @Transactional
    public void updateUser(User user){
        String key="user: "+user.getId();
        RLock lock= redissonClient.getLock(key+":lock");
        boolean locked=false;
        try{
            locked=lock.tryLock(500,10, TimeUnit.SECONDS);
            if(!locked){
                throw new Exception("can not get lock");
            }
            userRepo.update(user);
            try{
                redisTemplate.delete(key);
            }catch (Exception ex){
                cacheDeleteProducer.sendDeleteKeyMessage(key);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if(locked && lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }
}
