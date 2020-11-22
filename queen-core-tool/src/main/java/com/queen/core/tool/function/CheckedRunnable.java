
package com.queen.core.tool.function;

/**
 * 受检的 runnable
 *
 * @author jensen
 */
@FunctionalInterface
public interface CheckedRunnable {

	/**
	 * Run this runnable.
	 *
	 * @throws Throwable CheckedException
	 */
	void run() throws Throwable;

}
