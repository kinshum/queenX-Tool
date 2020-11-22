package com.queen.core.tool.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 大整数序列化为 String 字符串，避免浏览器丢失精度
 *
 * <p>
 * 前端建议采用：
 * bignumber 库： https://github.com/MikeMcl/bignumber.js
 * decimal.js 库： https://github.com/MikeMcl/decimal.js
 * </p>
 *
 * @author jensen
 */
public class QueenNumberModule extends SimpleModule {
	public static final QueenNumberModule INSTANCE = new QueenNumberModule();

	public QueenNumberModule() {
		super(QueenNumberModule.class.getName());
		// Long 和 BigInteger 采用定制的逻辑序列化，避免超过js的精度
		this.addSerializer(Long.class, BigNumberSerializer.instance);
		this.addSerializer(Long.TYPE, BigNumberSerializer.instance);
		this.addSerializer(BigInteger.class, BigNumberSerializer.instance);
		// BigDecimal 采用 toString 避免精度丢失，前端采用 decimal.js 来计算。
		this.addSerializer(BigDecimal.class, ToStringSerializer.instance);
	}
}
