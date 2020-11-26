package com.queen.core.cloud.http;

import com.queen.core.launch.log.QueenLogLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.concurrent.TimeUnit;

/**
 * http 配置
 *
 */
@Getter
@Setter
@RefreshScope
@ConfigurationProperties("queen.http")
public class QueenHttpProperties {
	/**
	 * 最大连接数，默认：200
	 */
	private int maxConnections = 200;
	/**
	 * 连接存活时间，默认：900L
	 */
	private long timeToLive = 900L;
	/**
	 * 连接池存活时间单位，默认：秒
	 */
	private TimeUnit timeUnit = TimeUnit.SECONDS;
	/**
	 * 链接超时，默认：2000毫秒
	 */
	private int connectionTimeout = 2000;
	/**
	 * 是否支持重定向，默认：true
	 */
	private boolean followRedirects = true;
	/**
	 * 关闭证书校验
	 */
	private boolean disableSslValidation = true;
	/**
	 * 日志级别
	 */
	private QueenLogLevel level = QueenLogLevel.NONE;
}
