package com.queen.core.secure.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户端校验配置
 *
 */
@Data
@ConfigurationProperties("queen.secure")
public class QueenSecureProperties {

	/**
	 * 开启鉴权规则
	 */
	private Boolean enabled = false;

	/**
	 * 鉴权放行请求
	 */
	private final List<String> skipUrl = new ArrayList<>();

	/**
	 * 开启授权规则
	 */
	private Boolean authEnabled = true;

	/**
	 * 授权配置
	 */
	private final List<AuthSecure> auth = new ArrayList<>();

	/**
	 * 开启基础认证规则
	 */
	private Boolean basicEnabled = true;

	/**
	 * 基础认证配置
	 */
	private final List<BasicSecure> basic = new ArrayList<>();

	/**
	 * 开启客户端规则
	 */
	private Boolean clientEnabled = true;

	/**
	 * 客户端配置
	 */
	private final List<ClientSecure> client = new ArrayList<>();

}
