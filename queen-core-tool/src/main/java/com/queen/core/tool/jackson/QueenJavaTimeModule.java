package com.queen.core.tool.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.queen.core.tool.utils.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * java 8 时间默认序列化
 *
 * @author jensen
 */
public class QueenJavaTimeModule extends SimpleModule {
	public static final QueenJavaTimeModule INSTANCE = new QueenJavaTimeModule();

	public QueenJavaTimeModule() {
		super(PackageVersion.VERSION);
		this.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeUtil.DATETIME_FORMAT));
		this.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeUtil.DATE_FORMAT));
		this.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeUtil.TIME_FORMAT));
		this.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeUtil.DATETIME_FORMAT));
		this.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeUtil.DATE_FORMAT));
		this.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeUtil.TIME_FORMAT));
	}

}
