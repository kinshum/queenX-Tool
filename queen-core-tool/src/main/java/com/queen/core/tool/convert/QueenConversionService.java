package com.queen.core.tool.convert;

import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.lang.Nullable;
import org.springframework.util.StringValueResolver;

/**
 * 类型 转换 服务，添加了 IEnum 转换
 *
 * @author jensen
 */
public class QueenConversionService extends ApplicationConversionService {
	@Nullable
	private static volatile QueenConversionService SHARED_INSTANCE;

	public QueenConversionService() {
		this(null);
	}

	public QueenConversionService(@Nullable StringValueResolver embeddedValueResolver) {
		super(embeddedValueResolver);
		super.addConverter(new EnumToStringConverter());
		super.addConverter(new StringToEnumConverter());
	}

	/**
	 * Return a shared default application {@code ConversionService} instance, lazily
	 * building it once needed.
	 * <p>
	 * Note: This method actually returns an {@link QueenConversionService}
	 * instance. However, the {@code ConversionService} signature has been preserved for
	 * binary compatibility.
	 * @return the shared {@code QueenConversionService} instance (never{@code null})
	 */
	public static GenericConversionService getInstance() {
		QueenConversionService sharedInstance = QueenConversionService.SHARED_INSTANCE;
		if (sharedInstance == null) {
			synchronized (QueenConversionService.class) {
				sharedInstance = QueenConversionService.SHARED_INSTANCE;
				if (sharedInstance == null) {
					sharedInstance = new QueenConversionService();
					QueenConversionService.SHARED_INSTANCE = sharedInstance;
				}
			}
		}
		return sharedInstance;
	}

}
