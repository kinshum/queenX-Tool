package com.queen.core.context.config;

import com.queen.core.context.QueenHttpHeadersGetter;
import com.queen.core.context.listener.QueenServletRequestListener;
import com.queen.core.context.props.QueenContextProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Servlet 监听器自动配置
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class QueenServletListenerConfiguration {

	@Bean
	public ServletListenerRegistrationBean<?> registerCustomListener(QueenContextProperties properties,
                                                                     QueenHttpHeadersGetter httpHeadersGetter) {
		return new ServletListenerRegistrationBean<>(new QueenServletRequestListener(properties, httpHeadersGetter));
	}

}
