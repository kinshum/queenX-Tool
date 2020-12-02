package com.queen.core.redis.config;

import com.queen.core.tool.utils.StringPool;
import com.queen.core.tool.utils.StringUtil;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * redis cache 扩展cache name自动化配置
 */
public class RedisAutoCacheManager extends RedisCacheManager {

    public RedisAutoCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration,
                                 Map<String, RedisCacheConfiguration> initialCacheConfigurations, boolean allowInFlightCacheCreation) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations, allowInFlightCacheCreation);
    }

    @NonNull
    @Override
    protected RedisCache createRedisCache(@NonNull String name, @Nullable RedisCacheConfiguration cacheConfig) {
        if (StringUtil.isBlank(name) || !name.contains(StringPool.HASH)) {
            return super.createRedisCache(name, cacheConfig);
        }
        String[] cacheArray = name.split(StringPool.HASH);
        if (cacheArray.length < 2) {
            return super.createRedisCache(name, cacheConfig);
        }
        String cacheName = cacheArray[0];
        if (cacheConfig != null) {
            Duration cacheAge = DurationStyle.detectAndParse(cacheArray[1], ChronoUnit.SECONDS);
            ;
            cacheConfig = cacheConfig.entryTtl(cacheAge);
        }
        return super.createRedisCache(cacheName, cacheConfig);
    }

}
