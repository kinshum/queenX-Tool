package com.queen.core.tool.constant;

/**
 * 系统默认角色
 *
 * @author jensen
 */
public class RoleConstant {

	public static final String ADMINISTRATOR = "administrator";

	public static final String HAS_ROLE_ADMINISTRATOR = "hasRole('" + ADMINISTRATOR + "')";

	public static final String ADMIN = "admin";

	public static final String HAS_ROLE_ADMIN = "hasAnyRole('" + ADMINISTRATOR + "', '" + ADMIN + "')";

	public static final String USER = "user";

	public static final String HAS_ROLE_USER = "hasRole('" + USER + "')";

	public static final String TEST = "test";

	public static final String HAS_ROLE_TEST = "hasRole('" + TEST + "')";

}
