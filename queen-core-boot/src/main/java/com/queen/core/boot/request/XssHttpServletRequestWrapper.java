package com.queen.core.boot.request;

import com.queen.core.tool.utils.StringUtil;
import com.queen.core.tool.utils.WebUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * XSS过滤
 *
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

	/**
	 * 没被包装过的HttpServletRequest（特殊场景,需要自己过滤）
	 */
	private final HttpServletRequest orgRequest;
	/**
	 * 缓存报文,支持多次读取流
	 */
	private byte[] body;
	/**
	 * html过滤
	 */
	private final static XssHtmlFilter HTML_FILTER = new XssHtmlFilter();

	public XssHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		orgRequest = request;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		if (super.getHeader(HttpHeaders.CONTENT_TYPE) == null) {
			return super.getInputStream();
		}

		if (super.getHeader(HttpHeaders.CONTENT_TYPE).startsWith(MediaType.MULTIPART_FORM_DATA_VALUE)) {
			return super.getInputStream();
		}

		if (body == null) {
			body = xssEncode(WebUtil.getRequestBody(super.getInputStream())).getBytes();
		}

		final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);

		return new ServletInputStream() {

			@Override
			public int read() {
				return byteArrayInputStream.read();
			}

			@Override
			public boolean isFinished() {
				return false;
			}

			@Override
			public boolean isReady() {
				return false;
			}

			@Override
			public void setReadListener(ReadListener readListener) {
			}
		};
	}

	@Override
	public String getParameter(String name) {
		String value = super.getParameter(xssEncode(name));
		if (StringUtil.isNotBlank(value)) {
			value = xssEncode(value);
		}
		return value;
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] parameters = super.getParameterValues(name);
		if (parameters == null || parameters.length == 0) {
			return null;
		}

		for (int i = 0; i < parameters.length; i++) {
			parameters[i] = xssEncode(parameters[i]);
		}
		return parameters;
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> map = new LinkedHashMap<>();
		Map<String, String[]> parameters = super.getParameterMap();
		for (String key : parameters.keySet()) {
			String[] values = parameters.get(key);
			for (int i = 0; i < values.length; i++) {
				values[i] = xssEncode(values[i]);
			}
			map.put(key, values);
		}
		return map;
	}

	@Override
	public String getHeader(String name) {
		String value = super.getHeader(xssEncode(name));
		if (StringUtil.isNotBlank(value)) {
			value = xssEncode(value);
		}
		return value;
	}

	private String xssEncode(String input) {
		return HTML_FILTER.filter(input);
	}

	/**
	 * 获取初始request
	 *
	 * @return HttpServletRequest
	 */
	public HttpServletRequest getOrgRequest() {
		return orgRequest;
	}

	/**
	 * 获取初始request
	 *
	 * @param request request
	 * @return HttpServletRequest
	 */
	public static HttpServletRequest getOrgRequest(HttpServletRequest request) {
		if (request instanceof XssHttpServletRequestWrapper) {
			return ((XssHttpServletRequestWrapper) request).getOrgRequest();
		}
		return request;
	}

}
