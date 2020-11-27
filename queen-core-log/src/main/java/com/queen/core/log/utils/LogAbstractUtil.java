package com.queen.core.log.utils;

import com.queen.core.launch.props.QueenProperties;
import com.queen.core.launch.server.ServerInfo;
import com.queen.core.log.model.LogAbstract;
import com.queen.core.secure.utils.AuthUtil;
import com.queen.core.tool.constant.QueenConstant;
import com.queen.core.tool.utils.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Log 相关工具
 *
 * @author jensen
 */
public class LogAbstractUtil {

	/**
	 * 向log中添加补齐request的信息
	 *
	 * @param request     请求
	 * @param logAbstract 日志基础类
	 */
	public static void addRequestInfoToLog(HttpServletRequest request, LogAbstract logAbstract) {
		if (ObjectUtil.isNotEmpty(request)) {
			logAbstract.setTenantId(Func.toStr(AuthUtil.getTenantId(), QueenConstant.ADMIN_TENANT_ID));
			logAbstract.setRemoteIp(WebUtil.getIP(request));
			logAbstract.setUserAgent(request.getHeader(WebUtil.USER_AGENT_HEADER));
			logAbstract.setRequestUri(UrlUtil.getPath(request.getRequestURI()));
			logAbstract.setMethod(request.getMethod());
			logAbstract.setParams(WebUtil.getRequestContent(request));
			logAbstract.setCreateBy(AuthUtil.getUserAccount(request));
		}
	}

	/**
	 * 向log中添加补齐其他的信息（eg：queen、server等）
	 *
	 * @param logAbstract     日志基础类
	 * @param queenProperties 配置信息
	 * @param serverInfo      服务信息
	 */
	public static void addOtherInfoToLog(LogAbstract logAbstract, QueenProperties queenProperties, ServerInfo serverInfo) {
		logAbstract.setServiceId(queenProperties.getName());
		logAbstract.setServerHost(serverInfo.getHostName());
		logAbstract.setServerIp(serverInfo.getIpWithPort());
		logAbstract.setEnv(queenProperties.getEnv());
		logAbstract.setCreateTime(DateUtil.now());
		if (logAbstract.getParams() == null) {
			logAbstract.setParams(StringPool.EMPTY);
		}
	}
}
