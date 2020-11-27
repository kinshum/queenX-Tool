package com.queen.core.mp.support;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页模型
 *
 */
@Data
public class QueenPage<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 查询数据列表
	 */
	private List<T> records = Collections.emptyList();

	/**
	 * 总数
	 */
	private long total = 0;
	/**
	 * 每页显示条数，默认 10
	 */
	private long size = 10;

	/**
	 * 当前页
	 */
	private long current = 1;

	/**
	 * mybatis-plus分页模型转化
	 *
	 * @param page 分页实体类
	 * @return QueenPage<T>
	 */
	public static <T> QueenPage<T> of(IPage<T> page) {
		QueenPage<T> queenPage = new QueenPage<>();
		queenPage.setRecords(page.getRecords());
		queenPage.setTotal(page.getTotal());
		queenPage.setSize(page.getSize());
		queenPage.setCurrent(page.getCurrent());
		return queenPage;
	}

}
