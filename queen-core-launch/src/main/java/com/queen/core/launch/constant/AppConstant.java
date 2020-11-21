package com.queen.core.launch.constant;

/**
 * 系统常量
 *
 * @author jensen
 */
public interface AppConstant {

	/**
	 * 应用版本
	 */
	String APPLICATION_VERSION = "2.2.6";

	/**
	 * 基础包
	 */
	String BASE_PACKAGES = "com.queen";

	/**
	 * 应用名前缀
	 */
	String APPLICATION_NAME_PREFIX = "queen-";
	/**
	 * 网关模块名称
	 */
	String APPLICATION_GATEWAY_NAME = APPLICATION_NAME_PREFIX + "gateway";
	/**
	 * 授权模块名称
	 */
	String APPLICATION_AUTH_NAME = APPLICATION_NAME_PREFIX + "auth";
	/**
	 * 监控模块名称
	 */
	String APPLICATION_ADMIN_NAME = APPLICATION_NAME_PREFIX + "admin";
	/**
	 * 报表系统名称
	 */
	String APPLICATION_REPORT_NAME = APPLICATION_NAME_PREFIX + "report";
	/**
	 * 集群监控名称
	 */
	String APPLICATION_TURBINE_NAME = APPLICATION_NAME_PREFIX + "turbine";
	/**
	 * 链路追踪名称
	 */
	String APPLICATION_ZIPKIN_NAME = APPLICATION_NAME_PREFIX + "zipkin";
	/**
	 * websocket名称
	 */
	String APPLICATION_WEBSOCKET_NAME = APPLICATION_NAME_PREFIX + "websocket";
	/**
	 * 首页模块名称
	 */
	String APPLICATION_DESK_NAME = APPLICATION_NAME_PREFIX + "desk";
	/**
	 * 系统模块名称
	 */
	String APPLICATION_SYSTEM_NAME = APPLICATION_NAME_PREFIX + "system";
	/**
	 * 用户模块名称
	 */
	String APPLICATION_USER_NAME = APPLICATION_NAME_PREFIX + "user";
	/**
	 * 日志模块名称
	 */
	String APPLICATION_LOG_NAME = APPLICATION_NAME_PREFIX + "log";
	/**
	 * 开发模块名称
	 */
	String APPLICATION_DEVELOP_NAME = APPLICATION_NAME_PREFIX + "develop";
	/**
	 * 流程设计器模块名称
	 */
	String APPLICATION_FLOWDESIGN_NAME = APPLICATION_NAME_PREFIX + "flowdesign";
	/**
	 * 工作流模块名称
	 */
	String APPLICATION_FLOW_NAME = APPLICATION_NAME_PREFIX + "flow";
	/**
	 * 资源模块名称
	 */
	String APPLICATION_RESOURCE_NAME = APPLICATION_NAME_PREFIX + "resource";
	/**
	 * 测试模块名称
	 */
	String APPLICATION_TEST_NAME = APPLICATION_NAME_PREFIX + "test";
	/**
	 * 演示模块名称
	 */
	String APPLICATION_DEMO_NAME = APPLICATION_NAME_PREFIX + "demo";

	/**
	 * 开发环境
	 */
	String DEV_CODE = "dev";
	/**
	 * 生产环境
	 */
	String PROD_CODE = "prod";
	/**
	 * 测试环境
	 */
	String TEST_CODE = "test";

	/**
	 * 代码部署于 linux 上，工作默认为 mac 和 Windows
	 */
	String OS_NAME_LINUX = "LINUX";

}
