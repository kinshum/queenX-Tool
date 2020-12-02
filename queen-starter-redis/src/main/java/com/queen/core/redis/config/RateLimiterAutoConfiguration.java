package com.queen.core.redis.config;

import com.queen.core.redis.ratelimiter.RedisRateLimiterAspect;
import com.queen.core.redis.ratelimiter.RedisRateLimiterClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.List;

/**
 * 基于 redis 的分布式限流自动配置
 */
@Configuration
@ConditionalOnProperty(value = "queen.redis.rate-limiter.enabled", havingValue = "true")
public class RateLimiterAutoConfiguration {

    @SuppressWarnings("unchecked")
    private RedisScript<List<Long>> redisRateLimiterScript() {
        DefaultRedisScript redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("META-INF/scripts/queen_rate_limiter.lua")));
        redisScript.setResultType(List.class);
        return redisScript;
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisRateLimiterClient redisRateLimiter(StringRedisTemplate redisTemplate, Environment environment) {
        RedisScript<List<Long>> redisRateLimiterScript = redisRateLimiterScript();
        return new RedisRateLimiterClient(redisTemplate, redisRateLimiterScript, environment);
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisRateLimiterAspect redisRateLimiterAspect(RedisRateLimiterClient rateLimiterClient) {
        return new RedisRateLimiterAspect(rateLimiterClient);
    }
}
