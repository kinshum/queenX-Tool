package com.queen.core.cloud.http;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * http 配置
 *
 */
@Configuration
@EnableConfigurationProperties(QueenHttpProperties.class)
public class QueenHttpConfiguration {
}
