package com.queen.core.api.crypto.core;

import com.queen.core.api.crypto.annotation.decrypt.ApiDecrypt;
import com.queen.core.api.crypto.bean.CryptoInfoBean;
import com.queen.core.api.crypto.config.ApiCryptoProperties;
import com.queen.core.api.crypto.util.ApiCryptoUtil;
import com.queen.core.tool.jackson.JsonUtil;
import com.queen.core.tool.utils.Charsets;
import com.queen.core.tool.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Parameter;

/**
 * param 参数 解析
 *
 */
@RequiredArgsConstructor
public class ApiDecryptParamResolver implements HandlerMethodArgumentResolver {
	private final ApiCryptoProperties properties;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return AnnotatedElementUtils.hasAnnotation(parameter.getParameter(), ApiDecrypt.class);
	}

	@Nullable
	@Override
	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		Parameter parameter = methodParameter.getParameter();
		ApiDecrypt apiDecrypt = AnnotatedElementUtils.getMergedAnnotation(parameter, ApiDecrypt.class);
		String text = webRequest.getParameter(properties.getParamName());
		if (StringUtil.isBlank(text)) {
			return null;
		}
		CryptoInfoBean infoBean = new CryptoInfoBean(apiDecrypt.value(), apiDecrypt.secretKey());
		byte[] textBytes = text.getBytes(Charsets.UTF_8);
		byte[] decryptData = ApiCryptoUtil.decryptData(properties, textBytes, infoBean);
		return JsonUtil.readValue(decryptData, parameter.getType());
	}
}
