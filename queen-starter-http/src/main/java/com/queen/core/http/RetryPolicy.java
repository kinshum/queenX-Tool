package com.queen.core.http;

import lombok.Getter;
import lombok.ToString;
import org.springframework.retry.policy.SimpleRetryPolicy;

import javax.annotation.Nullable;
import java.util.function.Predicate;

/**
 * 重试策略
 *
 */
@Getter
@ToString
public class RetryPolicy {
	public static final RetryPolicy INSTANCE = new RetryPolicy();

	private final int maxAttempts;
	private final long sleepMillis;
	@Nullable
	private final Predicate<ResponseSpec> respPredicate;

	public RetryPolicy() {
		this(null);
	}

	public RetryPolicy(int maxAttempts, long sleepMillis) {
		this(maxAttempts, sleepMillis, null);
	}

	public RetryPolicy(@Nullable Predicate<ResponseSpec> respPredicate) {
		this(SimpleRetryPolicy.DEFAULT_MAX_ATTEMPTS, 0L, respPredicate);
	}

	public RetryPolicy(int maxAttempts, long sleepMillis, @Nullable Predicate<ResponseSpec> respPredicate) {
		this.maxAttempts = maxAttempts;
		this.sleepMillis = sleepMillis;
		this.respPredicate = respPredicate;
	}
}
