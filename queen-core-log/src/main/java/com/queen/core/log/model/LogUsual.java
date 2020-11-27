package com.queen.core.log.model;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 实体类
 *
 */
@Data
@TableName("queen_log_usual")
@EqualsAndHashCode(callSuper = true)
public class LogUsual extends LogAbstract {

	private static final long serialVersionUID = 1L;

	/**
	 * 日志级别
	 */
	private String logLevel;
	/**
	 * 日志业务id
	 */
	private String logId;
	/**
	 * 日志数据
	 */
	private String logData;


}
