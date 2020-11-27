package com.queen.core.log.utils;


import com.queen.core.tool.utils.StringPool;

import java.util.Properties;

/**
 * Elk配置工具
 *
 */
public class ElkPropsUtil {

	/**
	 * 获取elk服务地址
	 *
	 * @return 服务地址
	 */
	public static String getDestination() {
		Properties props = System.getProperties();
		return props.getProperty("queen.log.elk.destination", StringPool.EMPTY);
	}

}
