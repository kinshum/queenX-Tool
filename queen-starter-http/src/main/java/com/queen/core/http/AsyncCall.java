package com.queen.core.http;

import okhttp3.Call;
import okhttp3.Request;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 异步执行器
 *
 */
public class AsyncCall {
	private final static Consumer<ResponseSpec> DEFAULT_CONSUMER = (r) -> {};
	private final static BiConsumer<Request, IOException> DEFAULT_FAIL_CONSUMER = (r, e) -> {};
	private final Call call;
	private Consumer<ResponseSpec> successConsumer;
	private Consumer<ResponseSpec> responseConsumer;
	private BiConsumer<Request, IOException> failedBiConsumer;

	AsyncCall(Call call) {
		this.call = call;
		this.successConsumer = DEFAULT_CONSUMER;
		this.responseConsumer = DEFAULT_CONSUMER;
		this.failedBiConsumer = DEFAULT_FAIL_CONSUMER;
	}

	public void onSuccessful(Consumer<ResponseSpec> consumer) {
		this.successConsumer = consumer;
		this.execute();
	}

	public void onResponse(Consumer<ResponseSpec> consumer) {
		this.responseConsumer = consumer;
		this.execute();
	}

	public AsyncCall onFailed(BiConsumer<Request, IOException> biConsumer) {
		this.failedBiConsumer = biConsumer;
		return this;
	}

	private void execute() {
		call.enqueue(new AsyncCallback(this));
	}

	void onResponse(HttpResponse httpResponse) {
		responseConsumer.accept(httpResponse);
	}

	void onSuccessful(HttpResponse httpResponse) {
		successConsumer.accept(httpResponse);
	}

	void onFailure(Request request, IOException e) {
		failedBiConsumer.accept(request, e);
	}
}
