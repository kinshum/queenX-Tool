package com.queen.core.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Excel监听器
 *
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class DataListener<T> extends AnalysisEventListener<T> {

	/**
	 * 缓存的数据列表
	 */
	private final List<T> dataList = new ArrayList<>();

	@Override
	public void invoke(T data, AnalysisContext analysisContext) {
		dataList.add(data);
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext analysisContext) {

	}

}
