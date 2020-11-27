package com.queen.core.tenant.dynamic;

import com.baomidou.dynamic.datasource.aop.DynamicDataSourceAnnotationInterceptor;
import com.queen.core.secure.utils.AuthUtil;
import com.queen.core.tenant.exception.TenantDataSourceException;
import lombok.Setter;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 租户数据源切换拦截器
 *
 */
public class TenantDataSourceAnnotationInterceptor extends DynamicDataSourceAnnotationInterceptor {

	@Setter
	private TenantDataSourceHolder holder;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		try {
			holder.handleDataSource(AuthUtil.getTenantId());
			return super.invoke(invocation);
		} catch (Exception exception) {
			throw new TenantDataSourceException(exception.getMessage());
		}
	}

}
