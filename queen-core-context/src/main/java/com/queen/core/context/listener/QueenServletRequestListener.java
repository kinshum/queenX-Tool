package com.queen.core.context.listener;

import com.queen.core.context.QueenHttpHeadersGetter;
import com.queen.core.context.props.QueenContextProperties;
import com.queen.core.tool.constant.QueenConstant;
import com.queen.core.tool.utils.StringUtil;
import com.queen.core.tool.utils.ThreadLocalUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet 请求监听器
 */
@RequiredArgsConstructor
public class QueenServletRequestListener implements ServletRequestListener {
	private final QueenContextProperties contextProperties;
	private final QueenHttpHeadersGetter httpHeadersGetter;

	@Override
	public void requestInitialized(ServletRequestEvent event) {
		HttpServletRequest request = (HttpServletRequest) event.getServletRequest();
		// MDC 获取透传的 变量
		QueenContextProperties.Headers headers = contextProperties.getHeaders();
		String requestId = request.getHeader(headers.getRequestId());
		if (StringUtil.isNotBlank(requestId)) {
			MDC.put(QueenConstant.MDC_REQUEST_ID_KEY, requestId);
		}
		String accountId = request.getHeader(headers.getAccountId());
		if (StringUtil.isNotBlank(accountId)) {
			MDC.put(QueenConstant.MDC_ACCOUNT_ID_KEY, accountId);
		}
		String tenantId = request.getHeader(headers.getTenantId());
		if (StringUtil.isNotBlank(tenantId)) {
			MDC.put(QueenConstant.MDC_TENANT_ID_KEY, tenantId);
		}
		// 处理 context，直接传递 request，因为 spring 中的尚未初始化完成
		HttpHeaders httpHeaders = httpHeadersGetter.get(request);
		ThreadLocalUtil.put(QueenConstant.CONTEXT_KEY, httpHeaders);
	}

	@Override
	public void requestDestroyed(ServletRequestEvent event) {
		// 会话销毁时，清除上下文
		ThreadLocalUtil.clear();
		// 会话销毁时，清除 mdc
		MDC.remove(QueenConstant.MDC_REQUEST_ID_KEY);
		MDC.remove(QueenConstant.MDC_ACCOUNT_ID_KEY);
		MDC.remove(QueenConstant.MDC_TENANT_ID_KEY);
	}

}
