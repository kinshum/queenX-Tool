package com.queen.core.swagger;

import com.queen.core.launch.constant.AppConstant;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * SwaggerProperties
 *
 */
@Data
@ConfigurationProperties("swagger")
public class SwaggerProperties {
	/**
	 * swagger会解析的包路径
	 **/
	private List<String> basePackages = new ArrayList<>(Collections.singletonList(AppConstant.BASE_PACKAGES));
	/**
	 * swagger会解析的url规则
	 **/
	private List<String> basePath = new ArrayList<>();
	/**
	 * 在basePath基础上需要排除的url规则
	 **/
	private List<String> excludePath = new ArrayList<>();
	/**
	 * 标题
	 **/
	private String title = "QueenX 接口文档系统";
	/**
	 * 描述
	 **/
	private String description = "QueenX 接口文档系统";
	/**
	 * 版本
	 **/
	private String version = AppConstant.APPLICATION_VERSION;
	/**
	 * 许可证
	 **/
	private String license = "Powered By QueenX";
	/**
	 * 许可证URL
	 **/
	private String licenseUrl = "https://xxx.vip";
	/**
	 * 服务条款URL
	 **/
	private String termsOfServiceUrl = "https://xxx.vip";

	/**
	 * host信息
	 **/
	private String host = "";
	/**
	 * 联系人信息
	 */
	private Contact contact = new Contact();
	/**
	 * 全局统一鉴权配置
	 **/
	private Authorization authorization = new Authorization();

	@Data
	@NoArgsConstructor
	public static class Contact {

		/**
		 * 联系人
		 **/
		private String name = "jensen";
		/**
		 * 联系人url
		 **/
		private String url = "xxxx";
		/**
		 * 联系人email
		 **/
		private String email = "shenjian26@hotmail.com";

	}

	@Data
	@NoArgsConstructor
	public static class Authorization {

		/**
		 * 鉴权策略ID，需要和SecurityReferences ID保持一致
		 */
		private String name = "";

		/**
		 * 需要开启鉴权URL的正则
		 */
		private String authRegex = "^.*$";

		/**
		 * 鉴权作用域列表
		 */
		private List<AuthorizationScope> authorizationScopeList = new ArrayList<>();

		private List<String> tokenUrlList = new ArrayList<>();
	}

	@Data
	@NoArgsConstructor
	public static class AuthorizationScope {

		/**
		 * 作用域名称
		 */
		private String scope = "";

		/**
		 * 作用域描述
		 */
		private String description = "";

	}
}
