package com.queen.core.oss.config;

import com.queen.core.oss.props.OssProperties;
import com.queen.core.oss.rule.OssRule;
import com.queen.core.oss.rule.QueenOssRule;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Oss配置类
 *
 */
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(OssProperties.class)
public class OssConfiguration {

	private final OssProperties ossProperties;

	@Bean
	@ConditionalOnMissingBean(OssRule.class)
	public OssRule ossRule() {
		return new QueenOssRule(ossProperties.getTenantMode());
	}

}
