
package com.queen.core.tool.function;

import org.springframework.lang.Nullable;

/**
 * 受检的 function
 *
 * @author jensen
 */
@FunctionalInterface
public interface CheckedFunction<T, R> {

	/**
	 * Run the Function
	 *
	 * @param t T
	 * @return R R
	 * @throws Throwable CheckedException
	 */
	@Nullable
	R apply(@Nullable T t) throws Throwable;

}
