package com.imooc.code;


import org.springframework.stereotype.Component;

import com.imooc.security.core.validate.code.sms.SmsCodeSender;

@Component
public class DemoSmsCodeSender implements SmsCodeSender{

	@Override
	public void send(String mobile, String code) {
		System.out.println("Demo向手机:" + mobile + ",发送短信验证码:" + code);
	}

}
