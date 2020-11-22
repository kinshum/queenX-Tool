package com.queen.core.tool.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Bean属性
 *
 * @author jensen
 */
@Getter
@AllArgsConstructor
public class BeanProperty {
	private final String name;
	private final Class<?> type;
}
