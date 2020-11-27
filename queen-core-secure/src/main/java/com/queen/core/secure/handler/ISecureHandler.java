package com.queen.core.secure.handler;

import com.queen.core.secure.props.AuthSecure;
import com.queen.core.secure.props.BasicSecure;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.List;

/**
 * secure 拦截器集合
 *
 */
public interface ISecureHandler {

	/**
	 * token拦截器
	 *
	 * @return tokenInterceptor
	 */
	HandlerInterceptorAdapter tokenInterceptor();

	/**
	 * auth拦截器
	 *
	 * @param authSecures 授权集合
	 * @return HandlerInterceptorAdapter
	 */
	HandlerInterceptorAdapter authInterceptor(List<AuthSecure> authSecures);

	/**
	 * basic拦截器
	 *
	 * @param basicSecures 基础认证集合
	 * @return HandlerInterceptorAdapter
	 */
	HandlerInterceptorAdapter basicInterceptor(List<BasicSecure> basicSecures);

	/**
	 * client拦截器
	 *
	 * @param clientId 客户端id
	 * @return clientInterceptor
	 */
	HandlerInterceptorAdapter clientInterceptor(String clientId);

}
