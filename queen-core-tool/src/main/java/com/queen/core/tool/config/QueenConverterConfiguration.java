package com.queen.core.tool.config;

import com.queen.core.tool.convert.EnumToStringConverter;
import com.queen.core.tool.convert.StringToEnumConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * queen enum 《-》 String 转换配置
 *
 * @author jensen
 */
@Configuration
public class QueenConverterConfiguration implements WebMvcConfigurer {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new EnumToStringConverter());
		registry.addConverter(new StringToEnumConverter());
	}

}
