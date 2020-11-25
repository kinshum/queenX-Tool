package com.queen.core.context.config;

import com.queen.core.context.QueenContext;
import com.queen.core.context.QueenHttpHeadersGetter;
import com.queen.core.context.QueenServletContext;
import com.queen.core.context.ServletHttpHeadersGetter;
import com.queen.core.context.props.QueenContextProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * Queen 服务上下文配置
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties(QueenContextProperties.class)
public class QueenContextAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public QueenHttpHeadersGetter QueenHttpHeadersGetter(QueenContextProperties contextProperties) {
		return new ServletHttpHeadersGetter(contextProperties);
	}

	@Bean
	@ConditionalOnMissingBean
	public QueenContext QueenContext(QueenContextProperties contextProperties, QueenHttpHeadersGetter httpHeadersGetter) {
		return new QueenServletContext(contextProperties, httpHeadersGetter);
	}

}
