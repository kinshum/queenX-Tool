package com.queen.core.test;

import com.queen.core.launch.QueenApplication;
import com.queen.core.launch.constant.AppConstant;
import com.queen.core.launch.constant.NacosConstant;
import com.queen.core.launch.constant.SentinelConstant;
import com.queen.core.launch.service.LauncherService;
import org.junit.runners.model.InitializationError;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 设置启动参数
 *
 */
public class QueenSpringRunner extends SpringJUnit4ClassRunner {

	public QueenSpringRunner(Class<?> clazz) throws InitializationError {
		super(clazz);
		setUpTestClass(clazz);
	}

	private void setUpTestClass(Class<?> clazz) {
		QueenBootTest queenBootTest = AnnotationUtils.getAnnotation(clazz, QueenBootTest.class);
		if (queenBootTest == null) {
			throw new QueenBootTestException(String.format("%s must be @QueenBootTest .", clazz));
		}
		String appName = queenBootTest.appName();
		String profile = queenBootTest.profile();
		boolean isLocalDev = QueenApplication.isLocalDev();
		Properties props = System.getProperties();
		props.setProperty("queen.env", profile);
		props.setProperty("queen.name", appName);
		props.setProperty("queen.is-local", String.valueOf(isLocalDev));
		props.setProperty("queen.dev-mode", profile.equals(AppConstant.PROD_CODE) ? "false" : "true");
		props.setProperty("queen.service.version", AppConstant.APPLICATION_VERSION);
		props.setProperty("spring.application.name", appName);
		props.setProperty("spring.profiles.active", profile);
		props.setProperty("info.version", AppConstant.APPLICATION_VERSION);
		props.setProperty("info.desc", appName);
		props.setProperty("spring.cloud.nacos.discovery.server-addr", NacosConstant.NACOS_ADDR);
		props.setProperty("spring.cloud.nacos.config.server-addr", NacosConstant.NACOS_ADDR);
		props.setProperty("spring.cloud.nacos.config.prefix", NacosConstant.NACOS_CONFIG_PREFIX);
		props.setProperty("spring.cloud.nacos.config.file-extension", NacosConstant.NACOS_CONFIG_FORMAT);
		props.setProperty("spring.cloud.sentinel.transport.dashboard", SentinelConstant.SENTINEL_ADDR);
		props.setProperty("spring.main.allow-bean-definition-overriding", "true");
		// 加载自定义组件
		if (queenBootTest.enableLoader()) {
			SpringApplicationBuilder builder = new SpringApplicationBuilder(clazz);
			List<LauncherService> launcherList = new ArrayList<>();
			ServiceLoader.load(LauncherService.class).forEach(launcherList::add);
			launcherList.stream().sorted(Comparator.comparing(LauncherService::getOrder)).collect(Collectors.toList())
				.forEach(launcherService -> launcherService.launcher(builder, appName, profile, isLocalDev));
		}
		System.err.println(String.format("---[junit.test]:[%s]---启动中，读取到的环境变量:[%s]", appName, profile));
	}

}
