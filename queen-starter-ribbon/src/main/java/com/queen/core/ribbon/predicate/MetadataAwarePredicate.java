package com.queen.core.ribbon.predicate;

import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.queen.core.ribbon.support.QueenRibbonRuleProperties;
import com.queen.core.ribbon.utils.BeanUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 基于 Metadata 的服务筛选
 *
 */
public class MetadataAwarePredicate extends DiscoveryEnabledPredicate {
	public static final MetadataAwarePredicate INSTANCE = new MetadataAwarePredicate();

	@Override
	protected boolean apply(NacosServer server) {
		final Map<String, String> metadata = server.getMetadata();

		// 获取配置
		QueenRibbonRuleProperties properties = BeanUtil.getBean(QueenRibbonRuleProperties.class);
		// 服务里的配置
		String localTag = properties.getTag();

		if (StringUtils.isBlank(localTag)) {
			return true;
		}

		// 本地有 tag，服务没有，返回 false
		String metaDataTag = metadata.get("tag");
		if (StringUtils.isBlank(metaDataTag)) {
			return false;
		}

		return metaDataTag.equals(localTag);
	}

}
