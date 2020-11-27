package com.queen.core.tenant.dynamic;

import lombok.Data;

/**
 * 租户数据源
 *
 */
@Data
public class TenantDataSource {

	/**
	 * 租户ID
	 */
	private String tenantId;
	/**
	 * 数据源ID
	 */
	private String datasourceId;
	/**
	 * 驱动类
	 */
	private String driverClass;
	/**
	 * 数据库链接
	 */
	private String url;
	/**
	 * 数据库账号名
	 */
	private String username;
	/**
	 * 数据库密码
	 */
	private String password;

}
