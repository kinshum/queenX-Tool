package com.queen.core.secure.provider;

import com.queen.core.tool.api.R;
import com.queen.core.tool.api.ResultCode;
import com.queen.core.tool.constant.QueenConstant;
import com.queen.core.tool.jackson.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * ResponseProvider
 *
 */
@Slf4j
public class ResponseProvider {

	public static void write(HttpServletResponse response) {
		R result = R.fail(ResultCode.UN_AUTHORIZED);
		response.setCharacterEncoding(QueenConstant.UTF_8);
		response.addHeader(QueenConstant.CONTENT_TYPE_NAME, MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		try {
			response.getWriter().write(Objects.requireNonNull(JsonUtil.toJson(result)));
		} catch (IOException ex) {
			log.error(ex.getMessage());
		}
	}

}
