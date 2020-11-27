package com.queen.core.secure.handler;

import com.queen.core.secure.interceptor.AuthInterceptor;
import com.queen.core.secure.interceptor.BasicInterceptor;
import com.queen.core.secure.interceptor.ClientInterceptor;
import com.queen.core.secure.interceptor.TokenInterceptor;
import com.queen.core.secure.props.AuthSecure;
import com.queen.core.secure.props.BasicSecure;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.List;

/**
 * Secure处理器
 *
 * @author jensen
 */
public class SecureHandlerHandler implements ISecureHandler {

	@Override
	public HandlerInterceptorAdapter tokenInterceptor() {
		return new TokenInterceptor();
	}

	@Override
	public HandlerInterceptorAdapter authInterceptor(List<AuthSecure> authSecures) {
		return new AuthInterceptor(authSecures);
	}

	@Override
	public HandlerInterceptorAdapter basicInterceptor(List<BasicSecure> basicSecures) {
		return new BasicInterceptor(basicSecures);
	}

	@Override
	public HandlerInterceptorAdapter clientInterceptor(String clientId) {
		return new ClientInterceptor(clientId);
	}

}
