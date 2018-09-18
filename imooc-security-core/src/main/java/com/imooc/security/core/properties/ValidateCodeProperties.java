package com.imooc.security.core.properties;

/**
 * @Package:com.imooc.security.core.properties
 * @ClassName:ValidateCodeProperties
 * @Description:TODO 验证码相关的配置项
 * @author:Jiangxb
 * @date:2018年9月14日 下午5:26:52
 * 包含：图片验证码基本参数配置类 ImageCodeProperties
 */
public class ValidateCodeProperties {
	
	/**
	 * 图片验证码基本参数配置类
	 * @Fields:image : TODO
	 */
	private ImageCodeProperties image = new ImageCodeProperties();
	
	
	/**
	 * 手机验证码基本参数配置类
	 * @Fields:sms : TODO
	 */
	private SmsCodeProperties sms = new SmsCodeProperties();

	
	public ImageCodeProperties getImage() {
		return image;
	}

	public void setImage(ImageCodeProperties image) {
		this.image = image;
	}

	public SmsCodeProperties getSms() {
		return sms;
	}

	public void setSms(SmsCodeProperties sms) {
		this.sms = sms;
	}
	
	
}
