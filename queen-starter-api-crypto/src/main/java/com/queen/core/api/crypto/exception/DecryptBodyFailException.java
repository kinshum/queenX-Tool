package com.queen.core.api.crypto.exception;

/**
 * <p>解密数据失败异常</p>
 *
 */
public class DecryptBodyFailException extends RuntimeException {

	public DecryptBodyFailException(String message) {
		super(message);
	}
}
