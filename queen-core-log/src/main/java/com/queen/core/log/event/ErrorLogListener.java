package com.queen.core.log.event;

import com.queen.core.launch.props.QueenProperties;
import com.queen.core.launch.server.ServerInfo;
import com.queen.core.log.constant.EventConstant;
import com.queen.core.log.feign.ILogClient;
import com.queen.core.log.model.LogError;
import com.queen.core.log.utils.LogAbstractUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;

/**
 * 异步监听错误日志事件
 *
 */
@Slf4j
@AllArgsConstructor
public class ErrorLogListener {

	private final ILogClient logService;
	private final ServerInfo serverInfo;
	private final QueenProperties queenProperties;

	@Async
	@Order
	@EventListener(ErrorLogEvent.class)
	public void saveErrorLog(ErrorLogEvent event) {
		Map<String, Object> source = (Map<String, Object>) event.getSource();
		LogError logError = (LogError) source.get(EventConstant.EVENT_LOG);
		LogAbstractUtil.addOtherInfoToLog(logError, queenProperties, serverInfo);
		logService.saveErrorLog(logError);
	}

}
