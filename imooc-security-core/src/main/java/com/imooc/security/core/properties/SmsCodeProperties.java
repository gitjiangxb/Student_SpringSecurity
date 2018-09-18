package com.imooc.security.core.properties;


/**
 * @Package:com.imooc.security.core.properties
 * @ClassName:SmsCodeProperties
 * @Description:TODO 手机短信验证码基本参数配置类
 * @author:Jiangxb
 * @date:2018年9月15日 下午5:58:55
 * 
 */
public class SmsCodeProperties {
	
	/**
	 * 验证码位数
	 * @Fields:length : TODO
	 */
	private int length = 6;
	
	/**
	 * 验证码过期时间
	 * @Fields:expireIn : TODO
	 */
	private int expireIn = 60;
	
	/**
	 * 图片验证码拦截的接口
	 * 	可以存在多个，已逗号分隔
	 * 例：/user,/user/*
	 * @Fields:url : TODO
	 */
	private String url = "";
	
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getExpireIn() {
		return expireIn;
	}

	public void setExpireIn(int expireIn) {
		this.expireIn = expireIn;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
