package com.imooc.security.core.properties;

/**
 * @Package:com.imooc.security.core.properties
 * @ClassName:BrowserProperties
 * @Description:TODO 浏览器相关的配置项
 * @author:Jiangxb
 * @date:2018年9月13日上午9:57:15
 * 
 */
public class BrowserProperties {
	
	/**
	 * 设置默认的登录界面方式
	 * @Fields:loginPage : TODO
	 */
	private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;
	
	/**
	 * 设置默认登录类型——返回JSON
	 * 新建一个枚举类型：LoginType
	 * @Fields:loginType : TODO
	 */
	private LoginResponseType loginType = LoginResponseType.JSON;
	
	/**
	 * 记住我 的过期时间：秒为单位
	 * @Fields:rememberMeSeconds : TODO
	 */
	private int rememberMeSeconds = 3600;
	
	public String getLoginPage() {
		return loginPage;
	}

	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}

	public LoginResponseType getLoginType() {
		return loginType;
	}

	public void setLoginType(LoginResponseType loginType) {
		this.loginType = loginType;
	}

	public int getRememberMeSeconds() {
		return rememberMeSeconds;
	}

	public void setRememberMeSeconds(int rememberMeSeconds) {
		this.rememberMeSeconds = rememberMeSeconds;
	}
	
	
	
}
