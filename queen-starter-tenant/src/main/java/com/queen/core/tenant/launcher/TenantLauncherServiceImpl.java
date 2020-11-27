package com.queen.core.tenant.launcher;

import com.queen.core.auto.service.AutoService;
import com.queen.core.launch.service.LauncherService;
import com.queen.core.launch.utils.PropsUtil;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.Ordered;

import java.util.Properties;

/**
 * 初始化租户配置
 *
 */
@AutoService(LauncherService.class)
public class TenantLauncherServiceImpl implements LauncherService {
	@Override
	public void launcher(SpringApplicationBuilder builder, String appName, String profile, boolean isLocalDev) {
		Properties props = System.getProperties();
		//默认关闭mybatis-plus多数据源自动装配
		PropsUtil.setProperty(props, "spring.datasource.dynamic.enabled", "false");
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}
}
