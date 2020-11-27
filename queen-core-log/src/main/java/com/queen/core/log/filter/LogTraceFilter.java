package com.queen.core.log.filter;


import com.queen.core.log.utils.LogTraceUtil;

import javax.servlet.*;
import java.io.IOException;

/**
 * 日志追踪过滤器
 *
 */
public class LogTraceFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		boolean flag = LogTraceUtil.insert();
		try {
			chain.doFilter(request, response);
		} finally {
			if (flag) {
				LogTraceUtil.remove();
			}
		}
	}

	@Override
	public void destroy() {
	}

}
