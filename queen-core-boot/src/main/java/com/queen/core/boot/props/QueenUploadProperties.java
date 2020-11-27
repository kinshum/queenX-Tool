package com.queen.core.boot.props;

import com.queen.core.tool.utils.PathUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.lang.Nullable;


/**
 * 文件上传配置
 *
 */
@Getter
@Setter
@RefreshScope
@ConfigurationProperties("queen.upload")
public class QueenUploadProperties {

	/**
	 * 文件保存目录，默认：jar 包同级目录
	 */
	@Nullable
	private String savePath = PathUtil.getJarPath();
}
