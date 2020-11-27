package com.queen.core.secure.props;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户端令牌认证信息
 *
 */
@Data
public class ClientSecure {

	/**
	 * 客户端ID
	 */
	private String clientId;
	/**
	 * 路径匹配
	 */
	private final List<String> pathPatterns = new ArrayList<>();

}
