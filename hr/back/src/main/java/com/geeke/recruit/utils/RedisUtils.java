package com.geeke.recruit.utils;

import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author yi.luo
 * @date 2023-04-17 10:14
 */
@Component
@RequiredArgsConstructor
public class RedisUtils {
    private final RedisTemplate redisTemplate;

    private static String LOCK_SUFFIX = "_LOCK";

    public boolean lock(String key, int lockTime) {
        Boolean flg = redisTemplate.opsForValue().setIfAbsent(key, "lock");
        redisTemplate.expire(key+LOCK_SUFFIX, lockTime, TimeUnit.MILLISECONDS);
        return ObjectUtil.isNull(flg) ? false : flg;
    }

    public boolean unLock(String key) {
        Boolean flg = redisTemplate.delete(key+LOCK_SUFFIX);
        return ObjectUtil.isNull(flg) ? false : flg;
    }

    public List getList(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }
}
