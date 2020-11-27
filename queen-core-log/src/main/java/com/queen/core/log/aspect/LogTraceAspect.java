package com.queen.core.log.aspect;

import com.queen.core.log.utils.LogTraceUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 为异步方法添加traceId
 *
 */
@Aspect
public class LogTraceAspect {

	@Pointcut("@annotation(org.springframework.scheduling.annotation.Async)")
	public void logPointCut() {
	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		try {
			LogTraceUtil.insert();
			return point.proceed();
		} finally {
			LogTraceUtil.remove();
		}
	}
}
