package com.imooc.security.browser.supper;

/**
 * @Package:com.imooc.security.browser.supper
 * @ClassName:SocialUserInfo
 * @Description:TODO 社交用户信息实体
 * @author:Jiangxb
 * @date:2018年9月29日 下午1:54:33
 * 【前提】：需要跳转到注册页面
 * 方便当用户使用社交软件第一次登录的时候，需要跳转到注册页面时；
 * 	显示授权的第三方软件的头像什么的，方便用户知道目前实在用什么授权。
 * 获取用户信息的方法：
 * 	BrowserSecurityController.getSocialUserInfo()方法里面
 */
public class SocialUserInfo { 
	
	/**
	 * @Fields:providerId : TODO 告诉前台当前是那个第三方用户在做社交登录
	 */
	private String providerId;
	
	/**
	 * @Fields:providerUserId : TODO 第三方用户的openId
	 */
	private String providerUserId;
	
	/**
	 * @Fields:nickname : TODO 昵称
	 */
	private String nickname;
	
	/**
	 * @Fields:headimg : TODO 头像
	 */
	private String headimg;

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getProviderUserId() {
		return providerUserId;
	}

	public void setProviderUserId(String providerUserId) {
		this.providerUserId = providerUserId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadimg() {
		return headimg;
	}

	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	} 
	
	
}
