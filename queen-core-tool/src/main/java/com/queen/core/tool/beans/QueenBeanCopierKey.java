package com.queen.core.tool.beans;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * copy key
 *
 * @author jensen
 */
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class QueenBeanCopierKey {
	private final Class<?> source;
	private final Class<?> target;
	private final boolean useConverter;
	private final boolean nonNull;
}
