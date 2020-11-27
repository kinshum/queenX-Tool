package com.queen.core.log.config;


import com.queen.core.launch.props.QueenProperties;
import com.queen.core.launch.props.QueenPropertySource;
import com.queen.core.launch.server.ServerInfo;
import com.queen.core.log.aspect.ApiLogAspect;
import com.queen.core.log.aspect.LogTraceAspect;
import com.queen.core.log.event.ApiLogListener;
import com.queen.core.log.event.ErrorLogListener;
import com.queen.core.log.event.UsualLogListener;
import com.queen.core.log.feign.ILogClient;
import com.queen.core.log.filter.LogTraceFilter;
import com.queen.core.log.logger.QueenLogger;
import com.queen.core.log.props.QueenRequestLogProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.servlet.DispatcherType;

/**
 * 日志工具自动配置
 *
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(QueenRequestLogProperties.class)
@QueenPropertySource(value = "classpath:/queen-log.yml")
public class QueenLogToolAutoConfiguration {

	@Bean
	public ApiLogAspect apiLogAspect() {
		return new ApiLogAspect();
	}

	@Bean
	public LogTraceAspect logTraceAspect() {
		return new LogTraceAspect();
	}

	@Bean
	public QueenLogger queenLogger() {
		return new QueenLogger();
	}

	@Bean
	public FilterRegistrationBean<LogTraceFilter> logTraceFilterRegistration() {
		FilterRegistrationBean<LogTraceFilter> registration = new FilterRegistrationBean<>();
		registration.setDispatcherTypes(DispatcherType.REQUEST);
		registration.setFilter(new LogTraceFilter());
		registration.addUrlPatterns("/*");
		registration.setName("LogTraceFilter");
		registration.setOrder(Ordered.LOWEST_PRECEDENCE);
		return registration;
	}

	@Bean
	@ConditionalOnMissingBean(name = "apiLogListener")
	public ApiLogListener apiLogListener(ILogClient logService, ServerInfo serverInfo, QueenProperties queenProperties) {
		return new ApiLogListener(logService, serverInfo, queenProperties);
	}

	@Bean
	@ConditionalOnMissingBean(name = "errorEventListener")
	public ErrorLogListener errorEventListener(ILogClient logService, ServerInfo serverInfo, QueenProperties queenProperties) {
		return new ErrorLogListener(logService, serverInfo, queenProperties);
	}

	@Bean
	@ConditionalOnMissingBean(name = "usualEventListener")
	public UsualLogListener usualEventListener(ILogClient logService, ServerInfo serverInfo, QueenProperties queenProperties) {
		return new UsualLogListener(logService, serverInfo, queenProperties);
	}

}
