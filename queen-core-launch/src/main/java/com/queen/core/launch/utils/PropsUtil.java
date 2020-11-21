package com.queen.core.launch.utils;

import org.springframework.util.StringUtils;

import java.util.Properties;

/**
 * 配置工具类
 *
 * @author jensen
 */
public class PropsUtil {

	/**
	 * 设置配置值，已存在则跳过
	 *
	 * @param props property
	 * @param key   key
	 * @param value value
	 */
	public static void setProperty(Properties props, String key, String value) {
		if (StringUtils.isEmpty(props.getProperty(key))) {
			props.setProperty(key, value);
		}
	}

}
