package com.queen.core.context;

import com.queen.core.context.props.QueenContextProperties;
import com.queen.core.tool.utils.StringUtil;
import com.queen.core.tool.utils.ThreadLocalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;

import java.util.function.Function;

import static com.queen.core.tool.constant.QueenConstant.CONTEXT_KEY;

/**
 * queen servlet 上下文，跨线程失效
 */
@RequiredArgsConstructor
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class QueenServletContext implements QueenContext {
	private final QueenContextProperties contextProperties;
	private final QueenHttpHeadersGetter httpHeadersGetter;

	@Nullable
	@Override
	public String getRequestId() {
		return get(contextProperties.getHeaders().getRequestId());
	}

	@Nullable
	@Override
	public String getAccountId() {
		return get(contextProperties.getHeaders().getAccountId());
	}

	@Nullable
	@Override
	public String getTenantId() {
		return get(contextProperties.getHeaders().getTenantId());
	}

	@Nullable
	@Override
	public String get(String ctxKey) {
		HttpHeaders headers = ThreadLocalUtil.getIfAbsent(CONTEXT_KEY, httpHeadersGetter::get);
		if (headers == null || headers.isEmpty()) {
			return null;
		}
		return headers.getFirst(ctxKey);
	}

	@Nullable
	@Override
	public <T> T get(String ctxKey, Function<String, T> function) {
		String ctxValue = get(ctxKey);
		if (StringUtil.isBlank(ctxValue)) {
			return null;
		}
		return function.apply(ctxKey);
	}

}
