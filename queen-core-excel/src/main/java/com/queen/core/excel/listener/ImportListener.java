package com.queen.core.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.queen.core.excel.support.ExcelImporter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Excel监听器
 *
 */
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ImportListener<T> extends AnalysisEventListener<T> {

	/**
	 * 默认每隔3000条存储数据库
	 */
	private int batchCount = 3000;
	/**
	 * 缓存的数据列表
	 */
	private List<T> list = new ArrayList<>();
	/**
	 * 数据导入类
	 */
	private final ExcelImporter<T> importer;

	@Override
	public void invoke(T data, AnalysisContext analysisContext) {
		list.add(data);
		// 达到BATCH_COUNT，则调用importer方法入库，防止数据几万条数据在内存，容易OOM
		if (list.size() >= batchCount) {
			// 调用importer方法
			importer.save(list);
			// 存储完成清理list
			list.clear();
		}
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext analysisContext) {
		// 调用importer方法
		importer.save(list);
		// 存储完成清理list
		list.clear();
	}

}
