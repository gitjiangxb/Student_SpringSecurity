package com.imooc.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Package:com.imooc.security.core.properties
 * @ClassName:MySecurityProperties
 * @Description:TODO 系统配置类
 * @author:Jiangxb
 * @date:2018年9月13日 上午9:56:37
 * 注解说明：说明这个类会读取以"imooc.security"开头的配置项
 */
@ConfigurationProperties(prefix = "imooc.security")
public class MySecurityProperties {
	/**
	 * 配置项举例说明：
	 * 		imooc.security.browser.loginPage
	 * 	会读取到SecurityProperties类中的 browser对象的loginPage属性中
	 * 因此：名称一定要一一对应，包括对象名和属性名
	 * 说明：当配置文件里面不存在imooc.security.browser.loginPage 配置信息时
	 * 		必须：private BrowserProperties browser = new BrowserProperties();这样写，否则会报错
	 * 	不能这样写private BrowserProperties browser; // 会报 空指针异常
	 */
	
	/**
	 * @Fields:browser : TODO 浏览器相关的配置项
	 */
	private BrowserProperties browser = new BrowserProperties();
	
	
	/**
	 * @Fields:code : TODO 验证码相关的配置项
	 */
	private ValidateCodeProperties code = new ValidateCodeProperties();

	/**
	 * @Fields:social : TODO 验证码相关的配置项
	 */
	private SocialProperties social = new SocialProperties();
	
	
	public BrowserProperties getBrowser() {
		return browser;
	}

	public void setBrowser(BrowserProperties browser) {
		this.browser = browser;
	}

	public ValidateCodeProperties getCode() {
		return code;
	}

	public void setCode(ValidateCodeProperties code) {
		this.code = code;
	}

	public SocialProperties getSocial() {
		return social;
	}

	public void setSocial(SocialProperties social) {
		this.social = social;
	}
	
	
}
