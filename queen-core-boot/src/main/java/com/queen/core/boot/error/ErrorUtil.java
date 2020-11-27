package com.queen.core.boot.error;

import com.queen.core.log.model.LogError;
import com.queen.core.tool.utils.DateUtil;
import com.queen.core.tool.utils.Exceptions;
import com.queen.core.tool.utils.ObjectUtil;

/**
 * 异常工具类
 *
 */
public class ErrorUtil {

	/**
	 * 初始化异常信息
	 *
	 * @param error 异常
	 * @param event 异常事件封装
	 */
	public static void initErrorInfo(Throwable error, LogError event) {
		// 堆栈信息
		event.setStackTrace(Exceptions.getStackTraceAsString(error));
		event.setExceptionName(error.getClass().getName());
		event.setMessage(error.getMessage());
		event.setCreateTime(DateUtil.now());
		StackTraceElement[] elements = error.getStackTrace();
		if (ObjectUtil.isNotEmpty(elements)) {
			// 报错的类信息
			StackTraceElement element = elements[0];
			event.setMethodClass(element.getClassName());
			event.setFileName(element.getFileName());
			event.setMethodName(element.getMethodName());
			event.setLineNumber(element.getLineNumber());
		}
	}
}
