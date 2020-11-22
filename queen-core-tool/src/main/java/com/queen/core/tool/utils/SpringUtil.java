
package com.queen.core.tool.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.lang.Nullable;

/**
 * spring 工具类
 *
 * @author jensen
 */
@Slf4j
public class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext context;

	@Override
	public void setApplicationContext(@Nullable ApplicationContext context) throws BeansException {
		SpringUtil.context = context;
	}

	/**
	 * 获取bean
	 *
	 * @param clazz class类
	 * @param <T>   泛型
	 * @return T
	 */
	public static <T> T getBean(Class<T> clazz) {
		if (clazz == null) {
			return null;
		}
		return context.getBean(clazz);
	}

	/**
	 * 获取bean
	 *
	 * @param beanId beanId
	 * @param <T>    泛型
	 * @return T
	 */
	public static <T> T getBean(String beanId) {
		if (beanId == null) {
			return null;
		}
		return (T) context.getBean(beanId);
	}

	/**
	 * 获取bean
	 *
	 * @param beanName bean名称
	 * @param clazz    class类
	 * @param <T>      泛型
	 * @return T
	 */
	public static <T> T getBean(String beanName, Class<T> clazz) {
		if (null == beanName || "".equals(beanName.trim())) {
			return null;
		}
		if (clazz == null) {
			return null;
		}
		return (T) context.getBean(beanName, clazz);
	}

	/**
	 * 获取 ApplicationContext
	 *
	 * @return ApplicationContext
	 */
	public static ApplicationContext getContext() {
		if (context == null) {
			return null;
		}
		return context;
	}

	/**
	 * 发布事件
	 *
	 * @param event 事件
	 */
	public static void publishEvent(ApplicationEvent event) {
		if (context == null) {
			return;
		}
		try {
			context.publishEvent(event);
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
	}

}
