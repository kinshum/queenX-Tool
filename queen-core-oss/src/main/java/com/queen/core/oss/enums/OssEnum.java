package com.queen.core.oss.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Oss枚举类
 *
 */
@Getter
@AllArgsConstructor
public enum OssEnum {

	/**
	 * minio
	 */
	MINIO("minio", 1),

	/**
	 * qiniu
	 */
	QINIU("qiniu", 2),

	/**
	 * ali
	 */
	ALI("ali", 3),

	/**
	 * tencent
	 */
	TENCENT("tencent", 4),
	;

	/**
	 * 名称
	 */
	final String name;
	/**
	 * 类型
	 */
	final int category;

	/**
	 * 匹配枚举值
	 *
	 * @param name 名称
	 * @return OssEnum
	 */
	public static OssEnum of(String name) {
		if (name == null) {
			return null;
		}
		OssEnum[] values = OssEnum.values();
		for (OssEnum ossEnum : values) {
			if (ossEnum.name.equals(name)) {
				return ossEnum;
			}
		}
		return null;
	}

}
