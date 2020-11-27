package com.queen.core.tenant;

import com.queen.core.tool.utils.RandomType;
import com.queen.core.tool.utils.StringUtil;

/**
 * queen租户id生成器
 *
 */
public class QueenTenantId implements TenantId {
	@Override
	public String generate() {
		return StringUtil.random(6, RandomType.INT);
	}
}
