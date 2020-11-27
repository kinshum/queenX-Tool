package com.queen.core.log.launch;

import com.queen.core.auto.service.AutoService;
import com.queen.core.launch.service.LauncherService;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.Ordered;

import java.util.Properties;

/**
 * LogLauncherServiceImpl
 *
 */
@AutoService(LauncherService.class)
public class LogLauncherServiceImpl implements LauncherService {
	@Override
	public void launcher(SpringApplicationBuilder builder, String appName, String profile, boolean isLocalDev) {
		Properties props = System.getProperties();
		props.setProperty("logging.config", "classpath:log/logback-" + profile + ".xml");
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}
}
