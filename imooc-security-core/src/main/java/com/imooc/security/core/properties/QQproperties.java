package com.imooc.security.core.properties;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * @Package:com.imooc.security.core.properties
 * @ClassName:QQproperties
 * @Description:TODO QQ的配置信息
 * @author:Jiangxb
 * @date:2018年9月27日 上午10:40:54
 * extends SocialProperties
 * 		Spring Social提供的，它包含两个字段
 * 			private String appId;
 * 			private String appSecret;
 */
public class QQproperties extends SocialProperties {
	
	/**
	 * @Fields:providerId : TODO 还要添加一个字段，服务提供商的标识
	 * 服务提供商的id(QQ/微信)
	 */
	private String providerId = "qq";

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	
	
}
