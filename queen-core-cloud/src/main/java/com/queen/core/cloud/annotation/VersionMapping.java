package com.queen.core.cloud.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.*;

/**
 * 版本号处理
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping
@UrlVersion
@ApiVersion
@Validated
public @interface VersionMapping {
	/**
	 * Alias for {@link RequestMapping#name}.
	 * @return {String[]}
	 */
	@AliasFor(annotation = RequestMapping.class)
	String name() default "";

	/**
	 * Alias for {@link RequestMapping#value}.
	 * @return {String[]}
	 */
	@AliasFor(annotation = RequestMapping.class)
	String[] value() default {};

	/**
	 * Alias for {@link RequestMapping#path}.
	 * @return {String[]}
	 */
	@AliasFor(annotation = RequestMapping.class)
	String[] path() default {};

	/**
	 * Alias for {@link RequestMapping#params}.
	 * @return {String[]}
	 */
	@AliasFor(annotation = RequestMapping.class)
	String[] params() default {};

	/**
	 * Alias for {@link RequestMapping#headers}.
	 * @return {String[]}
	 */
	@AliasFor(annotation = RequestMapping.class)
	String[] headers() default {};

	/**
	 * Alias for {@link RequestMapping#consumes}.
	 * @return {String[]}
	 */
	@AliasFor(annotation = RequestMapping.class)
	String[] consumes() default {};

	/**
	 * Alias for {@link RequestMapping#produces}.
	 * default json utf-8
	 * @return {String[]}
	 */
	@AliasFor(annotation = RequestMapping.class)
	String[] produces() default {};

	/**
	 * Alias for {@link UrlVersion#value}.
	 * @return {String}
	 */
	@AliasFor(annotation = UrlVersion.class, attribute = "value")
	String urlVersion() default "";

	/**
	 * Alias for {@link ApiVersion#value}.
	 * @return {String}
	 */
	@AliasFor(annotation = ApiVersion.class, attribute = "value")
	String apiVersion() default "";

}
