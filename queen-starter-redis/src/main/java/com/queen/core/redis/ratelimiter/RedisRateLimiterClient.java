package com.queen.core.redis.ratelimiter;

import com.queen.core.tool.utils.CharPool;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * redis 限流服务
 */
@RequiredArgsConstructor
public class RedisRateLimiterClient implements RateLimiterClient {
    /**
     * redis 限流 key 前缀
     */
    private static final String REDIS_KEY_PREFIX = "limiter:";
    /**
     * 失败的默认返回值
     */
    private static final long FAIL_CODE = 0;
    /**
     * redisTemplate
     */
    private final StringRedisTemplate redisTemplate;
    /**
     * redisScript
     */
    private final RedisScript<List<Long>> script;
    /**
     * env
     */
    private final Environment environment;

    @Override
    public boolean isAllowed(String key, long max, long ttl, TimeUnit timeUnit) {
        // redis key
        String redisKeyBuilder = REDIS_KEY_PREFIX +
                getApplicationName(environment) + CharPool.COLON + key;
        List<String> keys = Collections.singletonList(redisKeyBuilder);
        // 毫秒，考虑主从策略和脚本回放机制，这个time由客户端获取传入
        long now = System.currentTimeMillis();
        // 转为毫秒，pexpire
        long ttlMillis = timeUnit.toMillis(ttl);
        // 执行命令
        List<Long> results = this.redisTemplate.execute(this.script, keys, max + "", ttlMillis + "", now + "");
        // 结果为空返回失败
        if (results == null || results.isEmpty()) {
            return false;
        }
        // 判断返回成功
        Long result = results.get(0);
        return result != FAIL_CODE;
    }

    private static String getApplicationName(Environment environment) {
        return environment.getProperty("spring.application.name", "");
    }

}
