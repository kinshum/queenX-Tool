package com.queen.core.oss.rule;

import com.queen.core.secure.utils.AuthUtil;
import com.queen.core.tool.utils.DateUtil;
import com.queen.core.tool.utils.FileUtil;
import com.queen.core.tool.utils.StringPool;
import com.queen.core.tool.utils.StringUtil;
import lombok.AllArgsConstructor;

/**
 * 默认存储桶生成规则
 *
 */
@AllArgsConstructor
public class QueenOssRule implements OssRule {

	/**
	 * 租户模式
	 */
	private final Boolean tenantMode;

	@Override
	public String bucketName(String bucketName) {
		String prefix = (tenantMode) ? AuthUtil.getTenantId().concat(StringPool.DASH) : StringPool.EMPTY;
		return prefix + bucketName;
	}

	@Override
	public String fileName(String originalFilename) {
		return "upload" + StringPool.SLASH + DateUtil.today() + StringPool.SLASH + StringUtil.randomUUID() + StringPool.DOT + FileUtil.getFileExtension(originalFilename);
	}

}
