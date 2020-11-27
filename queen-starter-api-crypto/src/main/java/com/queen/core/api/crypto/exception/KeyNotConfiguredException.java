package com.queen.core.api.crypto.exception;


/**
 * <p>未配置KEY运行时异常</p>
 *
 */
public class KeyNotConfiguredException extends RuntimeException {

	public KeyNotConfiguredException(String message) {
		super(message);
	}
}
