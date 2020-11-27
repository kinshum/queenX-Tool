package com.queen.core.mp.service.impl;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.queen.core.mp.base.BaseEntity;
import com.queen.core.mp.base.BaseServiceImpl;
import com.queen.core.mp.injector.QueenSqlMethod;
import com.queen.core.mp.mapper.QueenMapper;
import com.queen.core.mp.service.QueenService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;

/**
 * QueenService 实现类（ 泛型：M 是 mapper 对象，T 是实体 ， PK 是主键泛型 ）
 *
 */
@Validated
public class QueenServiceImpl<M extends QueenMapper<T>, T extends BaseEntity> extends BaseServiceImpl<M, T> implements QueenService<T> {

	@Override
	public boolean saveIgnore(T entity) {
		return SqlHelper.retBool(baseMapper.insertIgnore(entity));
	}

	@Override
	public boolean saveReplace(T entity) {
		return SqlHelper.retBool(baseMapper.replace(entity));
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean saveIgnoreBatch(Collection<T> entityList, int batchSize) {
		return saveBatch(entityList, batchSize, QueenSqlMethod.INSERT_IGNORE_ONE);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean saveReplaceBatch(Collection<T> entityList, int batchSize) {
		return saveBatch(entityList, batchSize, QueenSqlMethod.REPLACE_ONE);
	}

	private boolean saveBatch(Collection<T> entityList, int batchSize, QueenSqlMethod sqlMethod) {
		String sqlStatement = queenSqlStatement(sqlMethod);
		executeBatch(entityList, batchSize, (sqlSession, entity) -> sqlSession.insert(sqlStatement, entity));
		return true;
	}

	/**
	 * 获取 queenSqlStatement
	 *
	 * @param sqlMethod ignore
	 * @return sql
	 */
	protected String queenSqlStatement(QueenSqlMethod sqlMethod) {
		return SqlHelper.table(currentModelClass()).getSqlStatement(sqlMethod.getMethod());
	}
}
