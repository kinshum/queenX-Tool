package com.queen.core.ribbon.support;

import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.queen.core.ribbon.rule.DiscoveryEnabledRule;
import com.queen.core.ribbon.rule.MetadataAwareRule;
import com.queen.core.ribbon.utils.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Queen ribbon rule auto configuration.
 *
 */
@Configuration
@RequiredArgsConstructor
@ConditionalOnClass(NacosServer.class)
@AutoConfigureBefore(RibbonClientConfiguration.class)
@EnableConfigurationProperties(QueenRibbonRuleProperties.class)
@ConditionalOnProperty(value = QueenRibbonRuleProperties.PROPERTIES_PREFIX + ".enabled", havingValue = "true")
public class QueenRibbonRuleAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public DiscoveryEnabledRule metadataAwareRule() {
		return new MetadataAwareRule();
	}

	@Bean
	public BeanUtil beanUtil(){
		return new BeanUtil();
	}

}
