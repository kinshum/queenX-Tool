package com.queen.core.launch.constant;

/**
 * zookeeper 配置.
 *
 * @author jensen
 */
public interface ZookeeperConstant {

	/**
	 * zookeeper id
	 */
	String ZOOKEEPER_ID = "zk";

	/**
	 * zookeeper connect string
	 */
	String ZOOKEEPER_CONNECT_STRING = "127.0.0.1:2181";

	/**
	 * zookeeper address
	 */
	String ZOOKEEPER_ADDRESS = "zookeeper://" + ZOOKEEPER_CONNECT_STRING;

	/**
	 * zookeeper root
	 */
	String ZOOKEEPER_ROOT = "/queen-services";
}
