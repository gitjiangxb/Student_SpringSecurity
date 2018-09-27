package com.imooc.security.core.properties;

/**
 * @Package:com.imooc.security.core.properties
 * @ClassName:SocialProperties
 * @Description:TODO 社交软件登录相关的配置项
 * @author:Jiangxb
 * @date:2018年9月27日 上午10:48:02
 * 
 */
public class SocialProperties {
	
	/**
	 * @Fields:qq : TODO QQ的配置文件
	 */
	private QQproperties qq = new QQproperties();

	public QQproperties getQq() {
		return qq;
	}

	public void setQq(QQproperties qq) {
		this.qq = qq;
	}
	
	
}
