package com.queen.core.redis.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * redis 配置
 */
@Getter
@Setter
@ConfigurationProperties("queen.redis")
public class QueenRedisProperties {

    /**
     * 序列化方式
     */
    private SerializerType serializerType = SerializerType.ProtoStuff;

    public enum SerializerType {
        /**
         * 默认:ProtoStuff 序列化
         */
        ProtoStuff,
        /**
         * json 序列化
         */
        JSON,
        /**
         * jdk 序列化
         */
        JDK
    }
}
