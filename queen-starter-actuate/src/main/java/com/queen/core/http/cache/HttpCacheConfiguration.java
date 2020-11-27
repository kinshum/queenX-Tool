package com.queen.core.http.cache;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashSet;
import java.util.Set;

/**
 * Http Cache 配置
 *
 */
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(QueenHttpCacheProperties.class)
@ConditionalOnProperty(value = "queen.http.cache.enabled", havingValue = "true")
public class HttpCacheConfiguration implements WebMvcConfigurer {
	private static final String DEFAULT_STATIC_PATH_PATTERN = "/**";
	private final WebMvcProperties webMvcProperties;
	private final QueenHttpCacheProperties properties;
	private final CacheManager cacheManager;

	@Bean
	public HttpCacheService httpCacheService() {
		return new HttpCacheService(properties, cacheManager);
	}

	@Override
	public void addInterceptors(@NonNull InterceptorRegistry registry) {
		Set<String> excludePatterns = new HashSet<>(properties.getExcludePatterns());
		String staticPathPattern = webMvcProperties.getStaticPathPattern();
		// 如果静态 目录 不为 /**
		if (!DEFAULT_STATIC_PATH_PATTERN.equals(staticPathPattern.trim())) {
			excludePatterns.add(staticPathPattern);
		}
		HttpCacheInterceptor httpCacheInterceptor = new HttpCacheInterceptor(httpCacheService());
		registry.addInterceptor(httpCacheInterceptor)
			.addPathPatterns(properties.getIncludePatterns().toArray(new String[0]))
			.excludePathPatterns(excludePatterns.toArray(new String[0]));
	}
}
