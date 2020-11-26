package com.queen.core.cloud.feign;

import com.netflix.hystrix.HystrixCommand;
import com.queen.core.tool.convert.EnumToStringConverter;
import com.queen.core.tool.convert.StringToEnumConverter;
import feign.Contract;
import feign.Feign;
import feign.RequestInterceptor;
import feign.hystrix.HystrixFeign;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.QueenFeignClientsRegistrar;
import org.springframework.cloud.openfeign.QueenHystrixTargeter;
import org.springframework.cloud.openfeign.Targeter;
import org.springframework.context.annotation.*;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.core.convert.support.DefaultConversionService;

import java.util.ArrayList;


/**
 * queen feign 增强配置
 *
 */
@Configuration
@ConditionalOnClass(Feign.class)
@Import(QueenFeignClientsRegistrar.class)
@AutoConfigureAfter(EnableFeignClients.class)
public class QueenFeignAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(Targeter.class)
	public QueenHystrixTargeter queenHystrixTargeter() {
		return new QueenHystrixTargeter();
	}

	@Configuration("hystrixFeignConfiguration")
	@ConditionalOnClass({HystrixCommand.class, HystrixFeign.class})
	protected static class HystrixFeignConfiguration {
		@Bean
		@Primary
		@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
		@ConditionalOnProperty(value = "feign.hystrix.enabled")
		public Feign.Builder feignHystrixBuilder(
                RequestInterceptor requestInterceptor, Contract feignContract) {
			return HystrixFeign.builder()
				.contract(feignContract)
				.decode404()
				.requestInterceptor(requestInterceptor);
		}

		@Bean
		@ConditionalOnMissingBean
		public RequestInterceptor requestInterceptor() {
			return new QueenFeignRequestHeaderInterceptor();
		}
	}

	/**
	 * queen enum 《-》 String 转换配置
	 *
	 * @param objectProvider ObjectProvider
	 * @return SpringMvcContract
	 */
	@Bean
	public Contract feignContract(@Qualifier("mvcConversionService") ObjectProvider<ConversionService> objectProvider) {
		ConversionService conversionService = objectProvider.getIfAvailable(DefaultConversionService::new);
		ConverterRegistry converterRegistry = ((ConverterRegistry) conversionService);
		converterRegistry.addConverter(new EnumToStringConverter());
		converterRegistry.addConverter(new StringToEnumConverter());
		return new QueenSpringMvcContract(new ArrayList<>(), conversionService);
	}
}
