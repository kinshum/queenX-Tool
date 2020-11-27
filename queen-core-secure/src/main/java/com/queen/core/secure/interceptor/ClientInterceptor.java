package com.queen.core.secure.interceptor;

import com.queen.core.secure.QueenUser;
import com.queen.core.secure.provider.ResponseProvider;
import com.queen.core.secure.utils.AuthUtil;
import com.queen.core.secure.utils.SecureUtil;
import com.queen.core.tool.jackson.JsonUtil;
import com.queen.core.tool.utils.StringUtil;
import com.queen.core.tool.utils.WebUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 客户端校验拦截器
 *
 */
@Slf4j
@AllArgsConstructor
public class ClientInterceptor extends HandlerInterceptorAdapter {

	private final String clientId;

	@Override
	public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
		QueenUser user = AuthUtil.getUser();
		boolean check = (
			user != null &&
				StringUtil.equals(clientId, SecureUtil.getClientIdFromHeader()) &&
				StringUtil.equals(clientId, user.getClientId())
		);
		if (!check) {
			log.warn("客户端认证失败，请求接口：{}，请求IP：{}，请求参数：{}", request.getRequestURI(), WebUtil.getIP(request), JsonUtil.toJson(request.getParameterMap()));
			ResponseProvider.write(response);
			return false;
		}
		return true;
	}

}
