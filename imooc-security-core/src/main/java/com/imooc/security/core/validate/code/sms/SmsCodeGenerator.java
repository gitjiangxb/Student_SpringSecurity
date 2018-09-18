package com.imooc.security.core.validate.code.sms;


import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.imooc.security.core.properties.MySecurityProperties;
import com.imooc.security.core.validate.code.ValidateCode;
import com.imooc.security.core.validate.code.ValidateCodeGenerator;

/**
 * @Package:com.imooc.security.core.validate.code
 * @ClassName:SmsCodeGenerator
 * @Description:TODO 手机短信验证码生成器
 * @author:Jiangxb
 * @date:2018年9月15日 下午5:53:53
 * 	短信验证码生成起没有其他的逻辑，只包含生成随机的数字
 */
@Component("smsValidateCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {
	
	/**
	 * 系统配置类
	 * @Fields:mySecurityProperties : TODO
	 * get() / set()
	 */
	@Autowired
	private MySecurityProperties mySecurityProperties;
	
	/* (non-Javadoc)
	 * @see com.imooc.security.core.validate.code.ValidateCodeGenerator#generate(org.springframework.web.context.request.ServletWebRequest)
	 */
	@Override
	public ValidateCode generate(ServletWebRequest request) {
		/**
		 * 生成随机数字的验证码，长度可配置,过期时间也可以配置
		 */
		String code = RandomStringUtils.randomNumeric(mySecurityProperties.getCode().getSms().getLength());
		return new ValidateCode(code, mySecurityProperties.getCode().getSms().getExpireIn());
	}
	

	public MySecurityProperties getMySecurityProperties() {
		return mySecurityProperties;
	}

	public void setMySecurityProperties(MySecurityProperties mySecurityProperties) {
		this.mySecurityProperties = mySecurityProperties;
	}

	
}
