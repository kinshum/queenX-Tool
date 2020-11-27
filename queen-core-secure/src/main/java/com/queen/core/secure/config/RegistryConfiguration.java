package com.queen.core.secure.config;

import com.queen.core.secure.handler.IPermissionHandler;
import com.queen.core.secure.handler.ISecureHandler;
import com.queen.core.secure.handler.QueenPermissionHandler;
import com.queen.core.secure.handler.SecureHandlerHandler;
import com.queen.core.secure.registry.SecureRegistry;
import lombok.AllArgsConstructor;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * secure注册默认配置
 *
 * @author jensen
 */
@Order
@Configuration
@AllArgsConstructor
@AutoConfigureBefore(SecureConfiguration.class)
public class RegistryConfiguration {

	private final JdbcTemplate jdbcTemplate;

	@Bean
	@ConditionalOnMissingBean(SecureRegistry.class)
	public SecureRegistry secureRegistry() {
		return new SecureRegistry();
	}

	@Bean
	@ConditionalOnMissingBean(ISecureHandler.class)
	public ISecureHandler secureHandler() {
		return new SecureHandlerHandler();
	}

	@Bean
	@ConditionalOnMissingBean(IPermissionHandler.class)
	public IPermissionHandler permissionHandler() {
		return new QueenPermissionHandler(jdbcTemplate);
	}

}
