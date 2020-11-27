package com.queen.core.log.model;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 实体类
 *
 */
@Data
@TableName("queen_log_api")
@EqualsAndHashCode(callSuper = true)
public class LogApi extends LogAbstract {

	private static final long serialVersionUID = 1L;

	/**
	 * 日志类型
	 */
	private String type;
	/**
	 * 日志标题
	 */
	private String title;
	/**
	 * 执行时间
	 */
	private String time;


}
