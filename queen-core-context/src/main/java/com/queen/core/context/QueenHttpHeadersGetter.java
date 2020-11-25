package com.queen.core.context;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;

/**
 * HttpHeaders 获取器，用于跨服务和线程的传递，
 * <p>
 * 暂时不支持 webflux。
 */
public interface QueenHttpHeadersGetter {

	/**
	 * 获取 HttpHeaders
	 *
	 * @return HttpHeaders
	 */
	@Nullable
    HttpHeaders get();

	/**
	 * 获取 HttpHeaders
	 *
	 * @param request 请求
	 * @return HttpHeaders
	 */
	@Nullable
    HttpHeaders get(HttpServletRequest request);

}
