package com.queen.core.log.publisher;


import com.queen.core.log.annotation.ApiLog;
import com.queen.core.log.constant.EventConstant;
import com.queen.core.log.event.ApiLogEvent;
import com.queen.core.log.model.LogApi;
import com.queen.core.log.utils.LogAbstractUtil;
import com.queen.core.tool.constant.QueenConstant;
import com.queen.core.tool.utils.SpringUtil;
import com.queen.core.tool.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * API日志信息事件发送
 *
 */
public class ApiLogPublisher {

	public static void publishEvent(String methodName, String methodClass, ApiLog apiLog, long time) {
		HttpServletRequest request = WebUtil.getRequest();
		LogApi logApi = new LogApi();
		logApi.setType(QueenConstant.LOG_NORMAL_TYPE);
		logApi.setTitle(apiLog.value());
		logApi.setTime(String.valueOf(time));
		logApi.setMethodClass(methodClass);
		logApi.setMethodName(methodName);
		LogAbstractUtil.addRequestInfoToLog(request, logApi);
		Map<String, Object> event = new HashMap<>(16);
		event.put(EventConstant.EVENT_LOG, logApi);
		SpringUtil.publishEvent(new ApiLogEvent(event));
	}

}
