package com.queen.core.context;

import com.queen.core.context.props.QueenContextProperties;
import com.queen.core.tool.utils.StringUtil;
import com.queen.core.tool.utils.WebUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

/**
 * HttpHeaders 获取器
 *
 */
@RequiredArgsConstructor
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ServletHttpHeadersGetter implements QueenHttpHeadersGetter {
	private final QueenContextProperties properties;

	@Nullable
	@Override
	public HttpHeaders get() {
		HttpServletRequest request = WebUtil.getRequest();
		if (request == null) {
			return null;
		}
		return get(request);
	}

	@Nullable
	@Override
	public HttpHeaders get(HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();
		List<String> crossHeaders = properties.getCrossHeaders();
		// 传递请求头
		Enumeration<String> headerNames = request.getHeaderNames();
		if (headerNames != null) {
			List<String> allowed = properties.getHeaders().getAllowed();
			while (headerNames.hasMoreElements()) {
				String key = headerNames.nextElement();
				// 只支持配置的 header
				if (crossHeaders.contains(key) || allowed.contains(key)) {
					String values = request.getHeader(key);
					// header value 不为空的 传递
					if (StringUtil.isNotBlank(values)) {
						headers.add(key, values);
					}
				}
			}
		}
		return headers;
	}

}
