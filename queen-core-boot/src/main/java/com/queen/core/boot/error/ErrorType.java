package com.queen.core.boot.error;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

/**
 * 异常类型
 *
 */
@Getter
@RequiredArgsConstructor
public enum ErrorType {
	/**
	 * 异常类型
	 */
	REQUEST("request"),
	ASYNC("async"),
	SCHEDULER("scheduler"),
	WEB_SOCKET("websocket"),
	OTHER("other");

	@JsonValue
	private final String type;

	@Nullable
	@JsonCreator
	public static ErrorType of(String type) {
		ErrorType[] values = ErrorType.values();
		for (ErrorType errorType : values) {
			if (errorType.type.equals(type)) {
				return errorType;
			}
		}
		return null;
	}

}
