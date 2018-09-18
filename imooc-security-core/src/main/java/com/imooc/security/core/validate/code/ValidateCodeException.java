package com.imooc.security.core.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
 * @Package:com.imooc.security.core.validate.code
 * @ClassName:ValidateCodeException
 * @Description:TODO 自定义 验证码 抛出的异常
 * @author:Jiangxb
 * @date:2018年9月14日上午10:25:12
 * 
 */
public class ValidateCodeException extends AuthenticationException {

	/**
	 * @Fields:serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 249719667345252331L;
	
	public ValidateCodeException(String msg) {
		super(msg);
	}

}
