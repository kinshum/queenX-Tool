package com.queen.core.log.utils;

import com.queen.core.tool.utils.StringUtil;
import org.slf4j.MDC;

/**
 * 日志追踪工具类
 *
 */
public class LogTraceUtil {
	private static final String UNIQUE_ID = "traceId";

	/**
	 * 获取日志追踪id格式
	 */
	public static String getTraceId() {
		return StringUtil.randomUUID();
	}

	/**
	 * 插入traceId
	 */
	public static boolean insert() {
		MDC.put(UNIQUE_ID, getTraceId());
		return true;
	}

	/**
	 * 移除traceId
	 */
	public static boolean remove() {
		MDC.remove(UNIQUE_ID);
		return true;
	}

}
