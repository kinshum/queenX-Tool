package com.queen.core.api.crypto.bean;

import com.queen.core.api.crypto.enums.CryptoType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * <p>加密注解信息</p>
 *
 */
@Getter
@RequiredArgsConstructor
public class CryptoInfoBean {

	/**
	 * 加密类型
	 */
	private final CryptoType type;
	/**
	 * 私钥
	 */
	private final String secretKey;

}
