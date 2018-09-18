package com.imooc.security.core.validate.code.sms;

/**
 * @Package:com.imooc.security.core.validate.code.sms
 * @ClassName:DefaultSmsCodeSender
 * @Description:TODO 默认 实现发送短信验证码的接口
 * @author:Jiangxb
 * @date:2018年9月15日 下午5:32:29
 * 	这个默认的实现时为了让人去覆盖的；
 * 		所以在ValidateCodeBeanConfig增加一个方法
 */
public class DefaultSmsCodeSender implements SmsCodeSender {

	@Override
	public void send(String mobile, String code) {
		// TODO Auto-generated method stub
		System.out.println("Default向手机:" + mobile + "发送短信验证码:" + code);
	}

}
