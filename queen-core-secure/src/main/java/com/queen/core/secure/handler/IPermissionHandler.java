package com.queen.core.secure.handler;

/**
 * 权限校验通用接口
 *
 */
public interface IPermissionHandler {

	/**
	 * 判断角色是否具有接口权限
	 *
	 * @return {boolean}
	 */
	boolean permissionAll();

	/**
	 * 判断角色是否具有接口权限
	 *
	 * @param permission 权限编号
	 * @return {boolean}
	 */
	boolean hasPermission(String permission);

}
