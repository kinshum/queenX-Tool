package com.queen.core.test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 简化 测试
 *
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootTest
public @interface QueenBootTest {
	/**
	 * 服务名：appName
	 * @return appName
	 */
	@AliasFor("appName")
	String value() default "queen-test";
	/**
	 * 服务名：appName
	 * @return appName
	 */
	@AliasFor("value")
	String appName() default "queen-test";
	/**
	 * profile
	 * @return profile
	 */
	String profile() default "dev";
	/**
	 * 启用 ServiceLoader 加载 launcherService
	 * @return 是否启用
	 */
	boolean enableLoader() default false;
}
