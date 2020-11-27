package com.queen.core.secure.interceptor;

import com.queen.core.secure.auth.AuthFun;
import com.queen.core.secure.props.AuthSecure;
import com.queen.core.secure.provider.HttpMethod;
import com.queen.core.secure.provider.ResponseProvider;
import com.queen.core.tool.jackson.JsonUtil;
import com.queen.core.tool.utils.WebUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 自定义授权拦截器校验
 *
 * @author jensen
 */
@Slf4j
@AllArgsConstructor
public class AuthInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 表达式处理
	 */
	private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();
	private static final EvaluationContext EVALUATION_CONTEXT = new StandardEvaluationContext(new AuthFun());

	/**
	 * 授权集合
	 */
	private final List<AuthSecure> authSecures;

	@Override
	public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
		boolean check = authSecures.stream().filter(authSecure -> checkAuth(request, authSecure)).findFirst().map(
			authSecure -> checkExpression(authSecure.getExpression())
		).orElse(Boolean.TRUE);
		if (!check) {
			log.warn("授权认证失败，请求接口：{}，请求IP：{}，请求参数：{}", request.getRequestURI(), WebUtil.getIP(request), JsonUtil.toJson(request.getParameterMap()));
			ResponseProvider.write(response);
			return false;
		}
		return true;
	}

	/**
	 * 检测授权
	 */
	private boolean checkAuth(HttpServletRequest request, AuthSecure authSecure) {
		return checkMethod(request, authSecure.getMethod()) && checkPath(request, authSecure.getPattern());
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
	private boolean checkExpression(String expression) {
		Boolean result = EXPRESSION_PARSER.parseExpression(expression).getValue(EVALUATION_CONTEXT, Boolean.class);
		return result != null ? result : false;
	}

}
