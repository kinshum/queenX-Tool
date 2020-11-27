package com.queen.core.secure.provider;

/**
 * HttpMethod枚举类
 *
 */
public enum HttpMethod {

	/**
	 * 请求方法集合
	 */
	GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE, ALL;

	/**
	 * 匹配枚举
	 */
	public static HttpMethod of(String method) {
		try {
			return valueOf(method);
		} catch (Exception exception) {
			return null;
		}
	}

}
