package com.queen.core.boot.config;

import com.queen.core.boot.error.ErrorType;
import com.queen.core.boot.error.ErrorUtil;
import com.queen.core.context.QueenContext;
import com.queen.core.context.QueenRunnableWrapper;
import com.queen.core.launch.props.QueenProperties;
import com.queen.core.log.constant.EventConstant;
import com.queen.core.log.event.ErrorLogEvent;
import com.queen.core.log.model.LogError;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.task.TaskExecutorCustomizer;
import org.springframework.boot.task.TaskSchedulerCustomizer;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.ErrorHandler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步处理
 *
 */
@Slf4j
@Configuration
@EnableAsync
@EnableScheduling
@AllArgsConstructor
public class QueenExecutorConfiguration extends AsyncConfigurerSupport {

	private final QueenContext queenContext;
	private final QueenProperties queenProperties;
	private final ApplicationEventPublisher publisher;

	@Bean
	public TaskExecutorCustomizer taskExecutorCustomizer() {
		return taskExecutor -> {
			taskExecutor.setThreadNamePrefix("async-task-");
			taskExecutor.setTaskDecorator(QueenRunnableWrapper::new);
			taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		};
	}

	@Bean
	public TaskSchedulerCustomizer taskSchedulerCustomizer() {
		return taskExecutor -> {
			taskExecutor.setThreadNamePrefix("async-scheduler");
			taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
			taskExecutor.setErrorHandler(new QueenErrorHandler(queenContext, queenProperties, publisher));
		};
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new QueenAsyncUncaughtExceptionHandler(queenContext, queenProperties, publisher);
	}

	@RequiredArgsConstructor
	private static class QueenAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {
		private final QueenContext queenContext;
		private final QueenProperties queenProperties;
		private final ApplicationEventPublisher eventPublisher;

		@Override
		public void handleUncaughtException(@NonNull Throwable error, @NonNull Method method, @NonNull Object... params) {
			log.error("Unexpected exception occurred invoking async method: {}", method, error);
			LogError logError = new LogError();
			// 服务信息、环境、异常类型
			logError.setParams(ErrorType.ASYNC.getType());
			logError.setEnv(queenProperties.getEnv());
			logError.setServiceId(queenProperties.getName());
			logError.setRequestUri(queenContext.getRequestId());
			// 堆栈信息
			ErrorUtil.initErrorInfo(error, logError);
			Map<String, Object> event = new HashMap<>(16);
			event.put(EventConstant.EVENT_LOG, logError);
			eventPublisher.publishEvent(new ErrorLogEvent(event));
		}
	}

	@RequiredArgsConstructor
	private static class QueenErrorHandler implements ErrorHandler {
		private final QueenContext queenContext;
		private final QueenProperties queenProperties;
		private final ApplicationEventPublisher eventPublisher;

		@Override
		public void handleError(@NonNull Throwable error) {
			log.error("Unexpected scheduler exception", error);
			LogError logError = new LogError();
			// 服务信息、环境、异常类型
			logError.setParams(ErrorType.SCHEDULER.getType());
			logError.setServiceId(queenProperties.getName());
			logError.setEnv(queenProperties.getEnv());
			logError.setRequestUri(queenContext.getRequestId());
			// 堆栈信息
			ErrorUtil.initErrorInfo(error, logError);
			Map<String, Object> event = new HashMap<>(16);
			event.put(EventConstant.EVENT_LOG, logError);
			eventPublisher.publishEvent(new ErrorLogEvent(event));
		}
	}

}
