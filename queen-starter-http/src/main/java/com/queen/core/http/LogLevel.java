package com.queen.core.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 日志级别
 *
 */
@Getter
@AllArgsConstructor
public enum LogLevel {
	/**
	 * No logs.
	 */
	NONE(HttpLoggingInterceptor.Level.NONE),
	/**
	 * Logs request and response lines.
	 *
	 * <p>Example:
	 * <pre>{@code
	 * --> POST /greeting http/1.1 (3-byte body)
	 *
	 * <-- 200 OK (22ms, 6-byte body)
	 * }</pre>
	 */
	BASIC(HttpLoggingInterceptor.Level.BASIC),
	/**
	 * Logs request and response lines and their respective headers.
	 *
	 * <p>Example:
	 * <pre>{@code
	 * --> POST /greeting http/1.1
	 * Host: example.com
	 * Content-Type: plain/text
	 * Content-Length: 3
	 * --> END POST
	 *
	 * <-- 200 OK (22ms)
	 * Content-Type: plain/text
	 * Content-Length: 6
	 * <-- END HTTP
	 * }</pre>
	 */
	HEADERS(HttpLoggingInterceptor.Level.HEADERS),
	/**
	 * Logs request and response lines and their respective headers and bodies (if present).
	 *
	 * <p>Example:
	 * <pre>{@code
	 * --> POST /greeting http/1.1
	 * Host: example.com
	 * Content-Type: plain/text
	 * Content-Length: 3
	 *
	 * Hi?
	 * --> END POST
	 *
	 * <-- 200 OK (22ms)
	 * Content-Type: plain/text
	 * Content-Length: 6
	 *
	 * Hello!
	 * <-- END HTTP
	 * }</pre>
	 */
	BODY(HttpLoggingInterceptor.Level.BODY);

	private final HttpLoggingInterceptor.Level level;
}
