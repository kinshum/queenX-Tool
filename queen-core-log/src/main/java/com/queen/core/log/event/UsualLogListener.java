package com.queen.core.log.event;

import com.queen.core.launch.props.QueenProperties;
import com.queen.core.launch.server.ServerInfo;
import com.queen.core.log.constant.EventConstant;
import com.queen.core.log.feign.ILogClient;
import com.queen.core.log.model.LogUsual;
import com.queen.core.log.utils.LogAbstractUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;

/**
 * 异步监听日志事件
 *
 */
@Slf4j
@AllArgsConstructor
public class UsualLogListener {

	private final ILogClient logService;
	private final ServerInfo serverInfo;
	private final QueenProperties queenProperties;

	@Async
	@Order
	@EventListener(UsualLogEvent.class)
	public void saveUsualLog(UsualLogEvent event) {
		Map<String, Object> source = (Map<String, Object>) event.getSource();
		LogUsual logUsual = (LogUsual) source.get(EventConstant.EVENT_LOG);
		LogAbstractUtil.addOtherInfoToLog(logUsual, queenProperties, serverInfo);
		logService.saveUsualLog(logUsual);
	}

}
