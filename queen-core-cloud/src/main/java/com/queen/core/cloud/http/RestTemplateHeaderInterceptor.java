package com.queen.core.cloud.http;

import com.queen.core.tool.constant.QueenConstant;
import com.queen.core.tool.utils.ThreadLocalUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;

import java.io.IOException;

/**
 * RestTemplateHeaderInterceptor 传递Request header
 *
 */
@AllArgsConstructor
public class RestTemplateHeaderInterceptor implements ClientHttpRequestInterceptor {
	@NonNull
	@Override
	public ClientHttpResponse intercept(@NonNull HttpRequest request, @NonNull byte[] bytes, @NonNull ClientHttpRequestExecution execution) throws IOException {
		HttpHeaders headers = ThreadLocalUtil.get(QueenConstant.CONTEXT_KEY);
		if (headers != null && !headers.isEmpty()) {
			HttpHeaders httpHeaders = request.getHeaders();
			headers.forEach((key, values) -> values.forEach(value -> httpHeaders.add(key, value)));
		}
		return execution.execute(request, bytes);
	}
}
