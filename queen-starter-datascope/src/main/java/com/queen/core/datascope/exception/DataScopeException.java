package com.queen.core.datascope.exception;

/**
 * 数据权限异常
 *
 */
public class DataScopeException extends RuntimeException {

	public DataScopeException() {
	}

	public DataScopeException(String message) {
		super(message);
	}

	public DataScopeException(Throwable cause) {
		super(cause);
	}
}
