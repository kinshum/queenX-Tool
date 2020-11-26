package org.springframework.cloud.openfeign;

import feign.Feign;
import feign.Target;

/**
 *
 */
public interface Targeter {
	/**
	 * target
	 *
	 * @param factory
	 * @param feign
	 * @param context
	 * @param target
	 * @param <T>
	 * @return T
	 */
	<T> T target(FeignClientFactoryBean factory, Feign.Builder feign, FeignContext context,
                 Target.HardCodedTarget<T> target);
}
