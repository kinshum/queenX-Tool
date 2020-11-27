package com.queen.core.http;

import com.queen.core.tool.jackson.JsonUtil;
import com.queen.core.tool.ssl.DisableValidationTrustManager;
import com.queen.core.tool.ssl.TrustAllHostNames;
import com.queen.core.tool.utils.Exceptions;
import com.queen.core.tool.utils.Holder;
import com.queen.core.tool.utils.StringPool;
import okhttp3.*;
import okhttp3.internal.Util;
import okhttp3.internal.http.HttpMethod;
import okhttp3.logging.HttpLoggingInterceptor;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.net.ssl.*;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/**
 * ok http 封装，请求结构体
 *
 */
public class HttpRequest {
	private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36";
	private static final MediaType APPLICATION_JSON = MediaType.parse("application/json;charset=UTF-8");
	private static volatile OkHttpClient httpClient = new OkHttpClient();
	@Nullable
	private static HttpLoggingInterceptor globalLoggingInterceptor = null;
	private final Request.Builder requestBuilder;
	private final HttpUrl.Builder uriBuilder;
	private final String httpMethod;
	private String userAgent;
	@Nullable
	private RequestBody requestBody;
	@Nullable
	private Boolean followRedirects;
	@Nullable
	private Boolean followSslRedirects;
	@Nullable
	private HttpLoggingInterceptor.Level level;
	@Nullable
	private CookieJar cookieJar;
	@Nullable
	private EventListener eventListener;
	private final List<Interceptor> interceptors = new ArrayList<>();
	@Nullable
	private Authenticator authenticator;
	@Nullable
	private Duration connectTimeout;
	@Nullable
	private Duration readTimeout;
	@Nullable
	private Duration writeTimeout;
	@Nullable
	private Proxy proxy;
	@Nullable
	private ProxySelector proxySelector;
	@Nullable
	private Authenticator proxyAuthenticator;
	@Nullable
	private RetryPolicy retryPolicy;
	@Nullable
	private Boolean disableSslValidation;
	@Nullable
	private HostnameVerifier hostnameVerifier;
	@Nullable
	private SSLSocketFactory sslSocketFactory;
	@Nullable
	private X509TrustManager trustManager;

	public static HttpRequest get(final String url) {
		return new HttpRequest(new Request.Builder(), url, Method.GET);
	}

	public static HttpRequest get(final URI uri) {
		return get(uri.toString());
	}

	public static HttpRequest post(final String url) {
		return new HttpRequest(new Request.Builder(), url, Method.POST);
	}

	public static HttpRequest post(final URI uri) {
		return post(uri.toString());
	}

	public static HttpRequest patch(final String url) {
		return new HttpRequest(new Request.Builder(), url, Method.PATCH);
	}

	public static HttpRequest patch(final URI uri) {
		return patch(uri.toString());
	}

	public static HttpRequest put(final String url) {
		return new HttpRequest(new Request.Builder(), url, Method.PUT);
	}

	public static HttpRequest put(final URI uri) {
		return put(uri.toString());
	}

	public static HttpRequest delete(final String url) {
		return new HttpRequest(new Request.Builder(), url, Method.DELETE);
	}

	public static HttpRequest delete(final URI uri) {
		return delete(uri.toString());
	}

	public HttpRequest query(String query) {
		this.uriBuilder.query(query);
		return this;
	}

	public HttpRequest queryEncoded(String encodedQuery) {
		this.uriBuilder.encodedQuery(encodedQuery);
		return this;
	}

	public HttpRequest queryMap(@Nullable Map<String, Object> queryMap) {
		if (queryMap != null && !queryMap.isEmpty()) {
			queryMap.forEach(this::query);
		}
		return this;
	}

	public HttpRequest query(String name, @Nullable Object value) {
		this.uriBuilder.addQueryParameter(name, value == null ? null : String.valueOf(value));
		return this;
	}

	public HttpRequest queryEncoded(String encodedName, @Nullable Object encodedValue) {
		this.uriBuilder.addEncodedQueryParameter(encodedName, encodedValue == null ? null : String.valueOf(encodedValue));
		return this;
	}

	HttpRequest form(FormBody formBody) {
		this.requestBody = formBody;
		return this;
	}

	HttpRequest multipartForm(MultipartBody multipartBody) {
		this.requestBody = multipartBody;
		return this;
	}

	public FormBuilder formBuilder() {
		return new FormBuilder(this);
	}

	public MultipartFormBuilder multipartFormBuilder() {
		return new MultipartFormBuilder(this);
	}

	public HttpRequest body(RequestBody requestBody) {
		this.requestBody = requestBody;
		return this;
	}

	public HttpRequest bodyString(String body) {
		this.requestBody = RequestBody.create(APPLICATION_JSON, body);
		return this;
	}

	public HttpRequest bodyString(MediaType contentType, String body) {
		this.requestBody = RequestBody.create(contentType, body);
		return this;
	}

	public HttpRequest bodyJson(@Nonnull Object body) {
		return bodyString(JsonUtil.toJson(body));
	}

	private HttpRequest(final Request.Builder requestBuilder, String url, String httpMethod) {
		HttpUrl httpUrl = HttpUrl.parse(url);
		if (httpUrl == null) {
			throw new IllegalArgumentException(String.format("Url 不能解析: %s: [%s]。", httpMethod.toLowerCase(), url));
		}
		this.requestBuilder = requestBuilder;
		this.uriBuilder = httpUrl.newBuilder();
		this.httpMethod = httpMethod;
		this.userAgent = DEFAULT_USER_AGENT;
	}

	private Call internalCall(final OkHttpClient client) {
		OkHttpClient.Builder builder = client.newBuilder();
		if (connectTimeout != null) {
			builder.connectTimeout(connectTimeout.toMillis(), TimeUnit.MILLISECONDS);
		}
		if (readTimeout != null) {
			builder.readTimeout(readTimeout.toMillis(), TimeUnit.MILLISECONDS);
		}
		if (writeTimeout != null) {
			builder.writeTimeout(writeTimeout.toMillis(), TimeUnit.MILLISECONDS);
		}
		if (proxy != null) {
			builder.proxy(proxy);
		}
		if (proxySelector != null) {
			builder.proxySelector(proxySelector);
		}
		if (proxyAuthenticator != null) {
			builder.proxyAuthenticator(proxyAuthenticator);
		}
		if (hostnameVerifier != null) {
			builder.hostnameVerifier(hostnameVerifier);
		}
		if (sslSocketFactory != null && trustManager != null) {
			builder.sslSocketFactory(sslSocketFactory, trustManager);
		}
		if (Boolean.TRUE.equals(disableSslValidation)) {
			disableSslValidation(builder);
		}
		if (authenticator != null) {
			builder.authenticator(authenticator);
		}
		if (!interceptors.isEmpty()) {
			builder.interceptors().addAll(interceptors);
		}
		if (cookieJar != null) {
			builder.cookieJar(cookieJar);
		}
		if (eventListener != null) {
			builder.eventListener(eventListener);
		}
		if (followRedirects != null) {
			builder.followRedirects(followRedirects);
		}
		if (followSslRedirects != null) {
			builder.followSslRedirects(followSslRedirects);
		}
		if (retryPolicy != null) {
			builder.addInterceptor(new RetryInterceptor(retryPolicy));
		}
		if (level != null && HttpLoggingInterceptor.Level.NONE != level) {
			builder.addInterceptor(getLoggingInterceptor(level));
		} else if (globalLoggingInterceptor != null) {
			builder.addInterceptor(globalLoggingInterceptor);
		}
		// 设置 User-Agent
		requestBuilder.header("User-Agent", userAgent);
		// url
		requestBuilder.url(uriBuilder.build());
		String method = httpMethod;
		Request request;
		if (HttpMethod.requiresRequestBody(method) && requestBody == null) {
			request = requestBuilder.method(method, Util.EMPTY_REQUEST).build();
		} else {
			request = requestBuilder.method(method, requestBody).build();
		}
		return builder.build().newCall(request);
	}

	public Exchange execute() {
		return new Exchange(internalCall(httpClient));
	}

	public AsyncCall async() {
		return new AsyncCall(internalCall(httpClient));
	}

	public HttpRequest baseAuth(String userName, String password) {
		this.authenticator = new BaseAuthenticator(userName, password);
		return this;
	}

	//// HTTP header operations
	public HttpRequest addHeader(final Map<String, String> headers) {
		this.requestBuilder.headers(Headers.of(headers));
		return this;
	}

	public HttpRequest addHeader(final String... namesAndValues) {
		Headers headers = Headers.of(namesAndValues);
		this.requestBuilder.headers(headers);
		return this;
	}

	public HttpRequest addHeader(final String name, final String value) {
		this.requestBuilder.addHeader(name, value);
		return this;
	}

	public HttpRequest setHeader(final String name, final String value) {
		this.requestBuilder.header(name, value);
		return this;
	}

	public HttpRequest removeHeader(final String name) {
		this.requestBuilder.removeHeader(name);
		return this;
	}

	public HttpRequest addCookie(final Cookie cookie) {
		this.addHeader("Cookie", cookie.toString());
		return this;
	}

	public HttpRequest cacheControl(final CacheControl cacheControl) {
		this.requestBuilder.cacheControl(cacheControl);
		return this;
	}

	public HttpRequest userAgent(final String userAgent) {
		this.userAgent = userAgent;
		return this;
	}

	public HttpRequest followRedirects(boolean followRedirects) {
		this.followRedirects = followRedirects;
		return this;
	}

	public HttpRequest followSslRedirects(boolean followSslRedirects) {
		this.followSslRedirects = followSslRedirects;
		return this;
	}

	private static HttpLoggingInterceptor getLoggingInterceptor(HttpLoggingInterceptor.Level level) {
		HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(Slf4jLogger.INSTANCE);
		loggingInterceptor.setLevel(level);
		return loggingInterceptor;
	}

	public HttpRequest log() {
		this.level = HttpLoggingInterceptor.Level.BODY;
		return this;
	}

	public HttpRequest log(LogLevel logLevel) {
		this.level = logLevel.getLevel();
		return this;
	}

	public HttpRequest authenticator(Authenticator authenticator) {
		this.authenticator = authenticator;
		return this;
	}

	public HttpRequest interceptor(Interceptor interceptor) {
		this.interceptors.add(interceptor);
		return this;
	}

	public HttpRequest cookieManager(CookieJar cookieJar) {
		this.cookieJar = cookieJar;
		return this;
	}

	public HttpRequest eventListener(EventListener eventListener) {
		this.eventListener = eventListener;
		return this;
	}

	//// HTTP connection parameter operations
	public HttpRequest connectTimeout(final Duration timeout) {
		this.connectTimeout = timeout;
		return this;
	}

	public HttpRequest readTimeout(Duration readTimeout) {
		this.readTimeout = readTimeout;
		return this;
	}

	public HttpRequest writeTimeout(Duration writeTimeout) {
		this.writeTimeout = writeTimeout;
		return this;
	}

	public HttpRequest proxy(final InetSocketAddress address) {
		this.proxy = new Proxy(Proxy.Type.HTTP, address);
		return this;
	}

	public HttpRequest proxySelector(final ProxySelector proxySelector) {
		this.proxySelector = proxySelector;
		return this;
	}

	public HttpRequest proxyAuthenticator(final Authenticator proxyAuthenticator) {
		this.proxyAuthenticator = proxyAuthenticator;
		return this;
	}

	public HttpRequest retry() {
		this.retryPolicy = RetryPolicy.INSTANCE;
		return this;
	}

	public HttpRequest retryOn(Predicate<ResponseSpec> respPredicate) {
		this.retryPolicy = new RetryPolicy(respPredicate);
		return this;
	}

	public HttpRequest retry(int maxAttempts, long sleepMillis) {
		this.retryPolicy = new RetryPolicy(maxAttempts, sleepMillis);
		return this;
	}

	public HttpRequest retry(int maxAttempts, long sleepMillis, Predicate<ResponseSpec> respPredicate) {
		this.retryPolicy = new RetryPolicy(maxAttempts, sleepMillis);
		return this;
	}

	/**
	 * 关闭 ssl 校验
	 *
	 * @return HttpRequest
	 */
	public HttpRequest disableSslValidation() {
		this.disableSslValidation = Boolean.TRUE;
		return this;
	}

	public HttpRequest hostnameVerifier(HostnameVerifier hostnameVerifier) {
		this.hostnameVerifier = hostnameVerifier;
		return this;
	}

	public HttpRequest sslSocketFactory(SSLSocketFactory sslSocketFactory, X509TrustManager trustManager) {
		this.sslSocketFactory = sslSocketFactory;
		this.trustManager = trustManager;
		return this;
	}

	@Override
	public String toString() {
		return requestBuilder.toString();
	}

	public static void setHttpClient(OkHttpClient httpClient) {
		HttpRequest.httpClient = httpClient;
	}

	public static void setGlobalLog(LogLevel logLevel) {
		HttpRequest.globalLoggingInterceptor = getLoggingInterceptor(logLevel.getLevel());
	}

	static String handleValue(@Nullable Object value) {
		if (value == null) {
			return StringPool.EMPTY;
		}
		if (value instanceof String) {
			return (String) value;
		}
		return String.valueOf(value);
	}

	private static void disableSslValidation(OkHttpClient.Builder builder) {
		try {
			X509TrustManager disabledTrustManager = DisableValidationTrustManager.INSTANCE;
			TrustManager[] trustManagers = new TrustManager[]{disabledTrustManager};
			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, trustManagers, Holder.SECURE_RANDOM);
			SSLSocketFactory disabledSslSocketFactory = sslContext.getSocketFactory();
			builder.sslSocketFactory(disabledSslSocketFactory, disabledTrustManager);
			builder.hostnameVerifier(TrustAllHostNames.INSTANCE);
		} catch (NoSuchAlgorithmException | KeyManagementException e) {
			throw Exceptions.unchecked(e);
		}
	}
}
