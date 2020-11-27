package com.queen.core.secure.interceptor;

import com.queen.core.secure.props.BasicSecure;
import com.queen.core.secure.provider.HttpMethod;
import com.queen.core.secure.provider.ResponseProvider;
import com.queen.core.secure.utils.SecureUtil;
import com.queen.core.tool.jackson.JsonUtil;
import com.queen.core.tool.utils.WebUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.lang.NonNull;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.queen.core.secure.constant.SecureConstant.BASIC_REALM_HEADER_KEY;
import static com.queen.core.secure.constant.SecureConstant.BASIC_REALM_HEADER_VALUE;


/**
 * 基础认证拦截器校验
 *
 * @author jensen
 */
@Slf4j
@AllArgsConstructor
public class BasicInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 授权集合
	 */
	private final List<BasicSecure> basicSecures;

	@Override
	public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
		boolean check = basicSecures.stream().filter(basicSecure -> checkAuth(request, basicSecure)).findFirst().map(
			authSecure -> checkBasic(authSecure.getUsername(), authSecure.getPassword())
		).orElse(Boolean.TRUE);
		if (!check) {
			log.warn("授权认证失败，请求接口：{}，请求IP：{}，请求参数：{}", request.getRequestURI(), WebUtil.getIP(request), JsonUtil.toJson(request.getParameterMap()));
			response.setHeader(BASIC_REALM_HEADER_KEY, BASIC_REALM_HEADER_VALUE);
			ResponseProvider.write(response);
			return false;
		}
		return true;
	}

	/**
	 * 检测授权
	 */
	private boolean checkAuth(HttpServletRequest request, BasicSecure basicSecure) {
		return checkMethod(request, basicSecure.getMethod()) && checkPath(request, basicSecure.getPattern());
	}

	/**
	 * 检测请求方法
	 */
	private boolean checkMethod(HttpServletRequest request, HttpMethod method) {
		return method == HttpMethod.ALL || (
			method != null && method == HttpMethod.of(request.getMethod())
		);
	}

	/**
	 * 检测路径匹配
	 */
	private boolean checkPath(HttpServletRequest request, String pattern) {
		String servletPath = request.getServletPath();
		String pathInfo = request.getPathInfo();
		if (pathInfo != null && pathInfo.length() > 0) {
			servletPath = servletPath + pathInfo;
		}
		return PatternMatchUtils.simpleMatch(pattern, servletPath);
	}

	/**
	 * 检测表达式
	 */
	private boolean checkBasic(String username, String password) {
		try {
			String[] tokens = SecureUtil.extractAndDecodeHeader();
			return username.equals(tokens[0]) && password.equals(tokens[1]);
		} catch (Exception e) {
			log.warn("授权认证失败，错误信息：{}", e.getMessage());
			return false;
		}
	}


}
