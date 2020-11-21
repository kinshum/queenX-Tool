package com.queen.core.auto.common;

import com.queen.core.auto.annotation.AutoContextInitializer;
import com.queen.core.auto.annotation.AutoFailureAnalyzer;
import com.queen.core.auto.annotation.AutoListener;

/**
 * 注解类型
 *
 * @author jensen
 */
public enum BootAutoType {
	/**
	 * 注解处理的类型
	 */
	CONTEXT_INITIALIZER(AutoContextInitializer.class.getName(), "org.springframework.context.ApplicationContextInitializer"),
	LISTENER(AutoListener.class.getName(), "org.springframework.context.ApplicationListener"),
	FAILURE_ANALYZER(AutoFailureAnalyzer.class.getName(), "org.springframework.boot.diagnostics.FailureAnalyzer"),
	COMPONENT("org.springframework.stereotype.Component", "org.springframework.boot.autoconfigure.EnableAutoConfiguration");

	private final String annotationName;
	private final String configureKey;

	BootAutoType(String annotationName, String configureKey) {
		this.annotationName = annotationName;
		this.configureKey = configureKey;
	}

	public final String getAnnotationName() {
		return annotationName;
	}

	public final String getConfigureKey() {
		return configureKey;
	}

}
