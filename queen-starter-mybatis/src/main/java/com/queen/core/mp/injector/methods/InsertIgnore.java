package com.queen.core.mp.injector.methods;


import com.queen.core.mp.injector.QueenSqlMethod;

/**
 * 插入一条数据（选择字段插入）插入如果中已经存在相同的记录，则忽略当前新数据
 *
 */
public class InsertIgnore extends AbstractInsertMethod {

	public InsertIgnore() {
		super(QueenSqlMethod.INSERT_IGNORE_ONE);
	}
}
