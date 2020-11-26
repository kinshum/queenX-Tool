package com.queen.core.cloud.feign;

import com.queen.core.tool.constant.QueenConstant;
import com.queen.core.tool.utils.ThreadLocalUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.http.HttpHeaders;

/**
 * feign 传递Request header
 *
 * <p>
 *     https://blog.csdn.net/u014519194/article/details/77160958
 *     http://tietang.wang/2016/02/25/hystrix/Hystrix%E5%8F%82%E6%95%B0%E8%AF%A6%E8%A7%A3/
 *     https://github.com/Netflix/Hystrix/issues/92#issuecomment-260548068
 * </p>
 *
 */
public class QueenFeignRequestHeaderInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate requestTemplate) {
		// 默认都使用 hystrix
		HttpHeaders headers = ThreadLocalUtil.get(QueenConstant.CONTEXT_KEY);
		if (headers != null && !headers.isEmpty()) {
			headers.forEach((key, values) ->
				values.forEach(value -> requestTemplate.header(key, value))
			);
		}
	}

}
