package com.queen.core.boot.config;

import com.queen.core.launch.props.QueenPropertySource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * queen自动配置类
 *
 */
@Slf4j
@Configuration
@AllArgsConstructor
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@QueenPropertySource(value = "classpath:/queen-boot.yml")
public class QueenBootAutoConfiguration {

}
