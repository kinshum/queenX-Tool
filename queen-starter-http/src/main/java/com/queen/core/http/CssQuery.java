package com.queen.core.http;

import java.lang.annotation.*;

/**
 * xml CssQuery
 *
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CssQuery {

	/**
	 * CssQuery
	 *
	 * @return CssQuery
	 */
	String value();

	/**
	 * 读取的 dom attr
	 *
	 * <p>
	 * attr：元素对于的 attr 的值
	 * html：整个元素的html
	 * text：元素内文本
	 * allText：多个元素的文本值
	 * </p>
	 *
	 * @return attr
	 */
	String attr() default "";

	/**
	 * 正则，用于对 attr value 处理
	 *
	 * @return regex
	 */
	String regex() default "";

	/**
	 * 默认的正则 group
	 */
	int DEFAULT_REGEX_GROUP = 0;

	/**
	 * 正则 group，默认为 0
	 *
	 * @return regexGroup
	 */
	int regexGroup() default DEFAULT_REGEX_GROUP;

	/**
	 * 嵌套的内部模型：默认 false
	 *
	 * @return 是否为内部模型
	 */
	boolean inner() default false;
}
