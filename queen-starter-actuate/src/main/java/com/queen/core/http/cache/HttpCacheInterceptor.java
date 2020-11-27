package com.queen.core.http.cache;

import com.queen.core.tool.utils.ClassUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Clock;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Http cache拦截器
 *
 */
@Slf4j
@AllArgsConstructor
public class HttpCacheInterceptor extends HandlerInterceptorAdapter {
	private final HttpCacheService httpCacheService;

	@Override
	public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
		// 非控制器请求直接跳出
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		// http cache 针对 HEAD 和 GET 请求
		String method = request.getMethod();
		HttpMethod httpMethod = HttpMethod.resolve(method);
		if (httpMethod == null) {
			return true;
		}
		List<HttpMethod> allowList = Arrays.asList(HttpMethod.HEAD, HttpMethod.GET);
		if (!allowList.contains(httpMethod)) {
			return true;
		}
		// 处理HttpCacheAble
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		HttpCacheAble cacheAble = ClassUtil.getAnnotation(handlerMethod, HttpCacheAble.class);
		if (cacheAble == null) {
			return true;
		}

		// 最后修改时间
		long ims = request.getDateHeader(HttpHeaders.IF_MODIFIED_SINCE);
		long now = Clock.systemUTC().millis();
		// 缓存时间,秒
		long maxAge = cacheAble.maxAge();
		// 缓存时间,毫秒
		long maxAgeMicros = TimeUnit.SECONDS.toMillis(maxAge);
		String cacheKey = request.getRequestURI() + "?" + request.getQueryString();
		// 后端可控制http-cache超时
		boolean hasCache = httpCacheService.get(cacheKey);
		// 如果header头没有过期
		if (hasCache && ims + maxAgeMicros > now) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			response.setHeader(HttpHeaders.CACHE_CONTROL, "max-age=" + maxAge);
			response.addDateHeader(HttpHeaders.EXPIRES, ims + maxAgeMicros);
			response.addDateHeader(HttpHeaders.LAST_MODIFIED, ims);
			log.info("{} 304 {}", method, request.getRequestURI());
			return false;
		}
		response.setHeader(HttpHeaders.CACHE_CONTROL, "max-age=" + maxAge);
		response.addDateHeader(HttpHeaders.EXPIRES, now + maxAgeMicros);
		response.addDateHeader(HttpHeaders.LAST_MODIFIED, now);
		httpCacheService.set(cacheKey);
		return super.preHandle(request, response, handler);
	}

}
