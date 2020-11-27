package com.queen.core.tenant;

/**
 * 租户id生成器
 *
 */
public interface TenantId {

	/**
	 * 生成自定义租户id
	 *
	 * @return tenantId
	 */
	String generate();

}
