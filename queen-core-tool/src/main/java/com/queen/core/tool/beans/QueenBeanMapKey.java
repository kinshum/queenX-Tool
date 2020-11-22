package com.queen.core.tool.beans;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * bean map key，提高性能
 *
 * @author jensen
 */
@EqualsAndHashCode
@AllArgsConstructor
public class QueenBeanMapKey {
	private final Class type;
	private final int require;
}
