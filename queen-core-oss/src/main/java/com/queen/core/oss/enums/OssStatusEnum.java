package com.queen.core.oss.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Oss类型枚举
 *
 */
@Getter
@AllArgsConstructor
public enum OssStatusEnum {

	/**
	 * 关闭
	 */
	DISABLE(1),
	/**
	 * 启用
	 */
	ENABLE(2),
	;

	/**
	 * 类型编号
	 */
	final int num;

}
