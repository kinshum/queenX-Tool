package com.queen.core.boot.config;

import com.queen.core.boot.request.QueenRequestFilter;
import com.queen.core.boot.request.XssProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.servlet.DispatcherType;

/**
 * 过滤器配置类
 *
 */
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties({XssProperties.class})
public class RequestConfiguration {

	private final XssProperties xssProperties;

	/**
	 * 全局过滤器
	 */
	@Bean
	public FilterRegistrationBean<QueenRequestFilter> queenFilterRegistration() {
		FilterRegistrationBean<QueenRequestFilter> registration = new FilterRegistrationBean<>();
		registration.setDispatcherTypes(DispatcherType.REQUEST);
		registration.setFilter(new QueenRequestFilter(xssProperties));
		registration.addUrlPatterns("/*");
		registration.setName("queenRequestFilter");
		registration.setOrder(Ordered.LOWEST_PRECEDENCE);
		return registration;
	}
}
