package com.queen.core.cloud.annotation;

import java.lang.annotation.*;

/**
 * header 版本 处理
 *
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ApiVersion {

	/**
	 * header 路径中的版本
	 *
	 * @return 版本号
	 */
	String value() default "";

}
