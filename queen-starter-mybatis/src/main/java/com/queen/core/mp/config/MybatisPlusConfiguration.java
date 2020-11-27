package com.queen.core.mp.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.queen.core.launch.props.QueenPropertySource;
import com.queen.core.mp.injector.QueenSqlInjector;
import com.queen.core.mp.intercept.QueryInterceptor;
import com.queen.core.mp.plugins.QueenPaginationInterceptor;
import com.queen.core.mp.plugins.SqlLogInterceptor;
import com.queen.core.mp.props.MybatisPlusProperties;
import com.queen.core.mp.resolver.PageArgumentResolver;
import com.queen.core.secure.utils.AuthUtil;
import com.queen.core.tool.constant.QueenConstant;
import com.queen.core.tool.utils.Func;
import com.queen.core.tool.utils.ObjectUtil;
import lombok.AllArgsConstructor;
import net.sf.jsqlparser.expression.StringValue;
import org.mybatis.spring.annotation.MapperScan;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * mybatis-plus 配置
 *
 */
@Configuration
@AllArgsConstructor
@MapperScan("com.queen.**.mapper.**")
@EnableConfigurationProperties(MybatisPlusProperties.class)
@QueenPropertySource(value = "classpath:/queen-mybatis.yml")
public class MybatisPlusConfiguration implements WebMvcConfigurer {

	/**
	 * 租户处理器
	 */
	@Bean
	@ConditionalOnMissingBean(TenantLineHandler.class)
	public TenantLineHandler tenantLineHandler() {
		return () -> new StringValue(Func.toStr(AuthUtil.getTenantId(), QueenConstant.ADMIN_TENANT_ID));
	}

	/**
	 * 租户拦截器
	 */
	@Bean
	@ConditionalOnMissingBean(TenantLineInnerInterceptor.class)
	public TenantLineInnerInterceptor tenantLineInnerInterceptor(TenantLineHandler tenantHandler) {
		return new TenantLineInnerInterceptor(tenantHandler);
	}

	/**
	 * mybatis-plus 拦截器集合
	 */
	@Bean
	@ConditionalOnMissingBean(MybatisPlusInterceptor.class)
	public MybatisPlusInterceptor mybatisPlusInterceptor(ObjectProvider<QueryInterceptor[]> queryInterceptors,
														 TenantLineInnerInterceptor tenantLineInnerInterceptor,
														 MybatisPlusProperties mybatisPlusProperties) {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		// 配置租户拦截器
		interceptor.addInnerInterceptor(tenantLineInnerInterceptor);
		// 配置分页拦截器
		QueenPaginationInterceptor paginationInterceptor = new QueenPaginationInterceptor();
		// 配置自定义查询拦截器
		QueryInterceptor[] queryInterceptorArray = queryInterceptors.getIfAvailable();
		if (ObjectUtil.isNotEmpty(queryInterceptorArray)) {
			AnnotationAwareOrderComparator.sort(queryInterceptorArray);
			paginationInterceptor.setQueryInterceptors(queryInterceptorArray);
		}
		paginationInterceptor.setMaxLimit(mybatisPlusProperties.getPageLimit());
		paginationInterceptor.setOverflow(mybatisPlusProperties.getOverflow());
		interceptor.addInnerInterceptor(paginationInterceptor);
		return interceptor;
	}

	/**
	 * sql 日志
	 */
	@Bean
	@ConditionalOnProperty(value = "queen.mybatis-plus.sql-log", matchIfMissing = true)
	public SqlLogInterceptor sqlLogInterceptor() {
		return new SqlLogInterceptor();
	}

	/**
	 * sql 注入
	 */
	@Bean
	public ISqlInjector sqlInjector() {
		return new QueenSqlInjector();
	}

	/**
	 * page 解析器
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new PageArgumentResolver());
	}

}

