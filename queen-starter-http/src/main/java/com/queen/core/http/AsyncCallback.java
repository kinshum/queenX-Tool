package com.queen.core.http;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;

/**
 * 异步处理
 *
 */
@ParametersAreNonnullByDefault
public class AsyncCallback implements Callback {
	private final AsyncCall asyncCall;

	AsyncCallback(AsyncCall asyncCall) {
		this.asyncCall = asyncCall;
	}

	@Override
	public void onFailure(Call call, IOException e) {
		asyncCall.onFailure(call.request(), e);
	}

	@Override
	public void onResponse(Call call, Response response) throws IOException {
		try (HttpResponse httpResponse = new HttpResponse(response)) {
			asyncCall.onResponse(httpResponse);
			if (response.isSuccessful()) {
				asyncCall.onSuccessful(httpResponse);
			} else {
				asyncCall.onFailure(call.request(), new IOException(httpResponse.message()));
			}
		}
	}

}
