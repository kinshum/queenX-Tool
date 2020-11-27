package com.queen.core.log.error;

import com.queen.core.log.publisher.ErrorLogPublisher;
import com.queen.core.tool.api.R;
import com.queen.core.tool.api.ResultCode;
import com.queen.core.tool.utils.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * 全局异常处理
 *
 */
@Slf4j
public class QueenErrorAttributes extends DefaultErrorAttributes {

	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
		String requestUri = this.getAttr(webRequest, "javax.servlet.error.request_uri");
		Integer status = this.getAttr(webRequest, "javax.servlet.error.status_code");
		Throwable error = getError(webRequest);
		R result;
		if (error == null) {
			log.error("URL:{} error status:{}", requestUri, status);
			result = R.fail(ResultCode.FAILURE, "系统未知异常[HttpStatus]:" + status);
		} else {
			log.error(String.format("URL:%s error status:%d", requestUri, status), error);
			result = R.fail(status, error.getMessage());
		}
		//发送服务异常事件
		ErrorLogPublisher.publishEvent(error, requestUri);
		return BeanUtil.toMap(result);
	}

	@Nullable
	private <T> T getAttr(WebRequest webRequest, String name) {
		return (T) webRequest.getAttribute(name, RequestAttributes.SCOPE_REQUEST);
	}

}
