package com.queen.core.log.event;

import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * 系统日志事件
 *
 * @author jensen
 */
public class ApiLogEvent extends ApplicationEvent {

	public ApiLogEvent(Map<String, Object> source) {
		super(source);
	}

}
