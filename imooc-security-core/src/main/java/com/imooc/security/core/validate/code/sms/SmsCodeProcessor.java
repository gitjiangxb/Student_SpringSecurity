package com.imooc.security.core.validate.code.sms;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import com.imooc.security.core.validate.code.ValidateCode;
import com.imooc.security.core.validate.code.impl.AbstractValidateCodeProcessor;
/**
 * @Package:com.imooc.security.core.validate.code.sms
 * @ClassName:SmsCodeProcessor
 * @Description:TODO 短信验证码处理器
 * @author:Jiangxb
 * @date:2018年9月15日 下午9:01:48
 * smsValidateCodeProcessor
 */
@Component("smsValidateCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {
	/**
	 * 短信验证码发送器
	 * @Fields:smsCodeSender : TODO
	 */
	@Autowired
	private SmsCodeSender smsCodeSender;
	
	/* (non-Javadoc)
	 * 发送
	 * @see com.imooc.security.core.validate.code.impl.AbstractValidateCodeProcessor#send(org.springframework.web.context.request.ServletWebRequest, com.imooc.security.core.validate.code.ValidateCode)
	 */
	@Override
	protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
		String mobile = ServletRequestUtils.getRequiredStringParameter((ServletRequest)request.getRequest(),"mobile");
		// 调用短信验证码发送器
		smsCodeSender.send(mobile, validateCode.getCode());
		
	}

}
