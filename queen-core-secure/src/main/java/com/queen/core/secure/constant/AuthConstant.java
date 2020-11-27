package com.queen.core.secure.constant;

/**
 * PreAuth权限表达式
 *
 * @author jensen
 */
public interface AuthConstant {

	/**
	 * 超管别名
	 */
	String ADMINISTRATOR = "administrator";

	/**
	 * 是有超管角色
	 */
	String HAS_ROLE_ADMINISTRATOR = "hasRole('" + ADMINISTRATOR + "')";

	/**
	 * 管理员别名
	 */
	String ADMIN = "admin";

	/**
	 * 是否有管理员角色
	 */
	String HAS_ROLE_ADMIN = "hasAnyRole('" + ADMINISTRATOR + "', '" + ADMIN + "')";

	/**
	 * 用户别名
	 */
	String USER = "user";

	/**
	 * 是否有用户角色
	 */
	String HAS_ROLE_USER = "hasRole('" + USER + "')";

	/**
	 * 测试别名
	 */
	String TEST = "test";

	/**
	 * 是否有测试角色
	 */
	String HAS_ROLE_TEST = "hasRole('" + TEST + "')";

	/**
	 * 只有超管才能访问
	 */
	String DENY_ALL = "denyAll()";

	/**
	 * 对所有请求进行接口权限校验
	 */
	String PERMISSION_ALL = "permissionAll()";

}
