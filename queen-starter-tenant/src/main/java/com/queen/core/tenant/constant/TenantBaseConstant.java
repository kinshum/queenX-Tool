package com.queen.core.tenant.constant;

/**
 * 租户常量.
 *
 */
public interface TenantBaseConstant {

	/**
	 * 租户数据源缓存名
	 */
	String TENANT_DATASOURCE_CACHE = "queen:datasource";

	/**
	 * 租户数据源缓存键
	 */
	String TENANT_DATASOURCE_KEY = "tenant:id:";

	/**
	 * 租户数据源缓存键
	 */
	String TENANT_DATASOURCE_EXIST_KEY = "tenant:exist:";

	/**
	 * 租户动态数据源键
	 */
	String TENANT_DYNAMIC_DATASOURCE_PROP = "queen.tenant.dynamic-datasource";

	/**
	 * 租户全局动态数据源切面键
	 */
	String TENANT_DYNAMIC_GLOBAL_PROP = "queen.tenant.dynamic-global";

	/**
	 * 租户是否存在数据源
	 */
	String TENANT_DATASOURCE_EXIST_STATEMENT = "select datasource_id from queen_tenant WHERE is_deleted = 0 AND tenant_id = ?";

	/**
	 * 租户数据源基础SQL
	 */
	String TENANT_DATASOURCE_BASE_STATEMENT = "SELECT tenant_id as tenantId, driver_class as driverClass, url, username, password from queen_tenant tenant LEFT JOIN queen_datasource datasource ON tenant.datasource_id = datasource.id ";

	/**
	 * 租户单数据源SQL
	 */
	String TENANT_DATASOURCE_SINGLE_STATEMENT = TENANT_DATASOURCE_BASE_STATEMENT + "WHERE tenant.is_deleted = 0 AND tenant.tenant_id = ?";

	/**
	 * 租户集动态数据源SQL
	 */
	String TENANT_DATASOURCE_GROUP_STATEMENT = TENANT_DATASOURCE_BASE_STATEMENT + "WHERE tenant.is_deleted = 0";

	/**
	 * 租户未找到返回信息
	 */
	String TENANT_DATASOURCE_NOT_FOUND = "未找到租户信息,数据源加载失败!";

}
