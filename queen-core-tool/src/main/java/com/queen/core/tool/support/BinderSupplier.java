
package com.queen.core.tool.support;

import java.util.function.Supplier;

/**
 * 解决 no binder available 问题
 *
 * @author jensen
 */
public class BinderSupplier implements Supplier<Object> {

	@Override
	public Object get() {
		return null;
	}
}
