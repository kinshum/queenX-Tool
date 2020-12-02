package com.queen.core.redis.ratelimiter;

import lombok.Getter;

import java.util.concurrent.TimeUnit;

/**
 * 限流异常
 */
@Getter
public class RateLimiterException extends RuntimeException {
    private final String key;
    private final long max;
    private final long ttl;
    private final TimeUnit timeUnit;

    public RateLimiterException(String key, long max, long ttl, TimeUnit timeUnit) {
        super(String.format("您的访问次数已超限：%s，速率：%d/%ds", key, max, timeUnit.toSeconds(ttl)));
        this.key = key;
        this.max = max;
        this.ttl = ttl;
        this.timeUnit = timeUnit;
    }
}
