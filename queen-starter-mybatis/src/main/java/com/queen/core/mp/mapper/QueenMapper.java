package com.queen.core.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 自定义的 Mapper
 *
 */
public interface QueenMapper<T> extends BaseMapper<T> {

	/**
	 * 插入如果中已经存在相同的记录，则忽略当前新数据
	 *
	 * @param entity 实体对象
	 * @return 更改的条数
	 */
	int insertIgnore(T entity);

	/**
	 * 表示插入替换数据，需求表中有PrimaryKey，或者unique索引，如果数据库已经存在数据，则用新数据替换，如果没有数据效果则和insert into一样；
	 *
	 * @param entity 实体对象
	 * @return 更改的条数
	 */
	int replace(T entity);

	/**
	 * 插入（批量）
	 *
	 * @param entityList 实体对象集合
	 * @return 成功行数
	 */
	int insertBatchSomeColumn(List<T> entityList);
}
