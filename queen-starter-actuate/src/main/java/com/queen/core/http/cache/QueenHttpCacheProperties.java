package com.queen.core.http.cache;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Http Cache 配置
 *
 */
@ConfigurationProperties("queen.http.cache")
public class QueenHttpCacheProperties {
	/**
	 * Http-cache 的 spring cache名，默认：queenHttpCache
	 */
	@Getter
	@Setter
	private String cacheName = "queenHttpCache";
	/**
	 * 默认拦截/**
	 */
	@Getter
	private final List<String> includePatterns = new ArrayList<String>() {{
		add("/**");
	}};
	/**
	 * 默认排除静态文件目录
	 */
	@Getter
	private final List<String> excludePatterns = new ArrayList<>();
}
