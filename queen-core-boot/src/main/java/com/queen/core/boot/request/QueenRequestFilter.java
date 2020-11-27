package com.queen.core.boot.request;

import com.queen.core.tool.utils.StringPool;
import lombok.AllArgsConstructor;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Request全局过滤
 *
 */
@AllArgsConstructor
public class QueenRequestFilter implements Filter {

	private final XssProperties xssProperties;

	@Override
	public void init(FilterConfig config) {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String path = ((HttpServletRequest) request).getServletPath();
		if (!xssProperties.getEnabled() || isSkip(path)) {
			QueenHttpServletRequestWrapper queenRequest = new QueenHttpServletRequestWrapper((HttpServletRequest) request);
			chain.doFilter(queenRequest, response);
		} else {
			XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
			chain.doFilter(xssRequest, response);
		}
	}

	private boolean isSkip(String path) {
		return xssProperties.getSkipUrl().stream().map(url -> url.replace("/**", StringPool.EMPTY)).anyMatch(path::startsWith);
	}

	@Override
	public void destroy() {

	}

}
