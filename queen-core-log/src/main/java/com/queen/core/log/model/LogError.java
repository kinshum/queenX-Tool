package com.queen.core.log.model;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 服务 异常
 *
 */
@Data
@TableName("queen_log_error")
@EqualsAndHashCode(callSuper = true)
public class LogError extends LogAbstract {

	private static final long serialVersionUID = 1L;

	/**
	 * 堆栈信息
	 */
	private String stackTrace;
	/**
	 * 异常名
	 */
	private String exceptionName;
	/**
	 * 异常消息
	 */
	private String message;

	/**
	 * 文件名
	 */
	private String fileName;

	/**
	 * 代码行数
	 */
	private Integer lineNumber;
}
