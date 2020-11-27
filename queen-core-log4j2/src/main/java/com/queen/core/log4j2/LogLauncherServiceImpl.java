package com.queen.core.log4j2;
;
import com.queen.core.auto.service.AutoService;
import com.queen.core.launch.service.LauncherService;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * 日志启动器
 *
 * @author jensen
 */
@AutoService(LauncherService.class)
public class LogLauncherServiceImpl implements LauncherService {

	@Override
	public void launcher(SpringApplicationBuilder builder, String appName, String profile, boolean isLocalDev) {
		System.setProperty("logging.config", String.format("classpath:log/log4j2_%s.xml", profile));
		// RocketMQ-Client 4.2.0 Log4j2 配置文件冲突问题解决：https://www.jianshu.com/p/b30ae6dd3811
		System.setProperty("rocketmq.client.log.loadconfig", "false");
		//  RocketMQ-Client 4.3 设置默认为 slf4j
		System.setProperty("rocketmq.client.logUseSlf4j", "true");
		// 非本地 将 全部的 System.err 和 System.out 替换为log
		if (!isLocalDev) {
			System.setOut(LogPrintStream.out());
			System.setErr(LogPrintStream.err());
		}
	}

}
