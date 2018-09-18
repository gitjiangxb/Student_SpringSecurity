package com.imooc.security.core.validate.code.sms;

/**
 * @Package:com.imooc.security.core.validate.code.sms
 * @ClassName:SmsCodeSender
 * @Description:TODO 发送短信验证码的接口
 * @author:Jiangxb
 * @date:2018年9月15日 下午5:30:40
 * 
 */
public interface SmsCodeSender {
	
	/**
	 * @Title:send
	 * @Description:TODO 发送方法
	 * @param mobile	手机号
	 * @param code		验证码
	 * @return:void
	 * @author:Jiangxb
	 * @date: 2018年9月15日 下午5:31:36
	 */
	void send(String mobile,String code);
}
