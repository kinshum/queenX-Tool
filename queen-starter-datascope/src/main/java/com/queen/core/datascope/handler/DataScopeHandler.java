package com.queen.core.datascope.handler;

import com.queen.core.datascope.model.DataScopeModel;
import com.queen.core.secure.QueenUser;

/**
 * 数据权限规则
 *
 */
public interface DataScopeHandler {

	/**
	 * 获取过滤sql
	 *
	 * @param mapperId    数据查询类
	 * @param dataScope   数据权限类
	 * @param queenUser   当前用户信息
	 * @param originalSql 原始Sql
	 * @return sql
	 */
	String sqlCondition(String mapperId, DataScopeModel dataScope, QueenUser queenUser, String originalSql);

}
