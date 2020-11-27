package com.queen.core.ribbon.predicate;

import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.PredicateKey;
import org.springframework.lang.Nullable;

/**
 * 过滤服务
 *
 */
public abstract class DiscoveryEnabledPredicate extends AbstractServerPredicate {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean apply(@Nullable PredicateKey input) {
		return input != null
			&& input.getServer() instanceof NacosServer
			&& apply((NacosServer) input.getServer());
	}

	/**
	 * Returns whether the specific {@link NacosServer} matches this predicate.
	 *
	 * @param server the discovered server
	 * @return whether the server matches the predicate
	 */
	protected abstract boolean apply(NacosServer server);
}
