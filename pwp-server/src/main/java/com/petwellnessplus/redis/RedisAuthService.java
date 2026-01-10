package com.petwellnessplus.redis;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisAuthService {

    private final StringRedisTemplate redisTemplate;

    public RedisAuthService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void store(String key, String value, Duration ttl) {
        redisTemplate.opsForValue().set(key, value, ttl);
    }
    
    public String getValue(String key) {
    	return redisTemplate.opsForValue().get(key);
    }

    public boolean exists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
