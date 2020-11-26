package com.queen.core.cloud.annotation;

import java.lang.annotation.*;

/**
 * 注解用于生成 requestMappingInfo 时候直接拼接路径规则，自动放置于方法路径开始部分
 *
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface UrlVersion {

	/**
	 * url 路径中的版本
	 *
	 * @return 版本号
	 */
	String value() default "";
}
