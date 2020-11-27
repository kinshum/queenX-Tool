package com.queen.core.secure.provider;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 客户端详情
 *
 */
@Data
public class ClientDetails implements IClientDetails {

	/**
	 * 客户端id
	 */
	@ApiModelProperty(value = "客户端id")
	private String clientId;
	/**
	 * 客户端密钥
	 */
	@ApiModelProperty(value = "客户端密钥")
	private String clientSecret;

	/**
	 * 令牌过期秒数
	 */
	@ApiModelProperty(value = "令牌过期秒数")
	private Integer accessTokenValidity;
	/**
	 * 刷新令牌过期秒数
	 */
	@ApiModelProperty(value = "刷新令牌过期秒数")
	private Integer refreshTokenValidity;

}
