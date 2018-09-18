package com.imooc.security.core.properties;


/**
 * @Package:com.imooc.security.core.properties
 * @ClassName:ImageCodeProperties
 * @Description:TODO 图片验证码基本参数配置类
 * @author:Jiangxb
 * @date:2018年9月14日 下午5:10:08
 * 	继承手机验证码基本配置类：因为他们存在共性属性
 */
public class ImageCodeProperties extends SmsCodeProperties{
	/**
	 * 验证码长度
	 * @Fields:width : TODO
	 */
	private int width = 67;
	
	/**
	 * 验证码宽度
	 * @Fields:height : TODO
	 */
	private int height = 23;
	
	
	/**
	 * 利用构造函数 将图片验证码设置默认长度 4
	 * @Title:ImageCodeProperties
	 */
	public ImageCodeProperties(){
		setLength(4);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	
}
