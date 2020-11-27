package com.queen.core.ribbon.rule;

import com.netflix.loadbalancer.Server;
import com.queen.core.ribbon.predicate.MetadataAwarePredicate;
import com.queen.core.ribbon.support.QueenRibbonRuleProperties;
import com.queen.core.ribbon.utils.BeanUtil;
import com.queen.core.ribbon.utils.HostUtil;
import org.springframework.util.ObjectUtils;
import org.springframework.util.PatternMatchUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ribbon 路由规则器
 *
 */
public class MetadataAwareRule extends DiscoveryEnabledRule {

	public MetadataAwareRule() {
		super(MetadataAwarePredicate.INSTANCE);
	}

	@Override
	public List<Server> filterServers(List<Server> serverList) {
		QueenRibbonRuleProperties ribbonProperties = BeanUtil.getBean(QueenRibbonRuleProperties.class);
		List<String> priorIpPattern = ribbonProperties.getPriorIpPattern();

		// 1. 查找是否有本机 ip
		String hostIp = HostUtil.getHostIp();

		// 优先的 ip 规则
		boolean hasPriorIpPattern = !priorIpPattern.isEmpty();
		String[] priorIpPatterns = priorIpPattern.toArray(new String[0]);

		List<Server> priorServerList = new ArrayList<>();
		for (Server server : serverList) {
			String host = server.getHost();
			// 2. 优先本地 ip 的服务
			if (ObjectUtils.nullSafeEquals(hostIp, host)) {
				return Collections.singletonList(server);
			}
			// 3. 优先的 ip 服务
			if (hasPriorIpPattern && PatternMatchUtils.simpleMatch(priorIpPatterns, host)) {
				priorServerList.add(server);
			}
		}

		// 4. 如果优先的有数据直接返回
		if (!priorServerList.isEmpty()) {
			return priorServerList;
		}

		return Collections.unmodifiableList(serverList);
	}

}
