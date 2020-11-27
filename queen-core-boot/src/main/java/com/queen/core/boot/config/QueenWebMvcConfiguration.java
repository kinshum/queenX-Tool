package com.queen.core.boot.config;

import com.queen.core.boot.props.QueenUploadProperties;
import com.queen.core.boot.resolver.TokenArgumentResolver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * WEB配置
 *
 * @author jensen
 */
@Slf4j
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@AllArgsConstructor
@EnableConfigurationProperties({
	QueenUploadProperties.class, QueenUploadProperties.class
})
public class QueenWebMvcConfiguration implements WebMvcConfigurer {

	private final QueenUploadProperties queenUploadProperties;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String path = queenUploadProperties.getSavePath();
		registry.addResourceHandler("/upload/**")
			.addResourceLocations("file:" + path + "/upload/");
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new TokenArgumentResolver());
	}

}
