package com.queen.core.tool.function;

import org.springframework.lang.Nullable;

/**
 * 受检的 Callable
 *
 * @author jensen
 */
@FunctionalInterface
public interface CheckedCallable<T> {

	/**
	 * Run this callable.
	 *
	 * @return result
	 * @throws Throwable CheckedException
	 */
	@Nullable
	T call() throws Throwable;
}
