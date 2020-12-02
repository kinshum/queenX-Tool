package com.queen.core.redis.config;

import com.queen.core.redis.serializer.ProtoStuffSerializer;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * ProtoStuff 序列化配置
 */
@Configuration
@AutoConfigureBefore(RedisTemplateConfiguration.class)
@ConditionalOnClass(name = "io.protostuff.Schema")
public class ProtoStuffSerializerConfiguration implements QueenRedisSerializerConfigAble {

    @Bean
    @ConditionalOnMissingBean
    @Override
    public RedisSerializer<Object> redisSerializer(QueenRedisProperties properties) {
        if (QueenRedisProperties.SerializerType.ProtoStuff == properties.getSerializerType()) {
            return new ProtoStuffSerializer();
        }
        return defaultRedisSerializer(properties);
    }

}
