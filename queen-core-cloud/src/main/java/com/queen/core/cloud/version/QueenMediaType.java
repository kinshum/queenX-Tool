package com.queen.core.cloud.version;

import lombok.Getter;
import org.springframework.http.MediaType;

/**
 *
 * <p>
 * https://developer.github.com/v3/media/
 * </p>
 *
 */
@Getter
public class QueenMediaType {
	private static final String MEDIA_TYPE_TEMP = "application/vnd.%s.%s+json";

	private final String appName = "queen";
	private final String version;
	private final MediaType mediaType;

	public QueenMediaType(String version) {
		this.version = version;
		this.mediaType = MediaType.valueOf(String.format(MEDIA_TYPE_TEMP, appName, version));
	}

	@Override
	public String toString() {
		return mediaType.toString();
	}
}
