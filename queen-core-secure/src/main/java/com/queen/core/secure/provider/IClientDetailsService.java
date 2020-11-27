package com.queen.core.secure.provider;

/**
 * 多终端注册接口
 *
 */
public interface IClientDetailsService {

	/**
	 * 根据clientId获取Client详情
	 *
	 * @param clientId 客户端id
	 * @return
	 */
	IClientDetails loadClientByClientId(String clientId);

}
