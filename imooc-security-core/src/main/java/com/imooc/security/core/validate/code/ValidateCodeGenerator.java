package com.imooc.security.core.validate.code;


import org.springframework.web.context.request.ServletWebRequest;

/**
 * @Package:com.imooc.security.core.validate.code
 * @ClassName:ValidateCodeGenerator
 * @Description:TODO 验证码的生成器
 * @author:Jiangxb
 * @date:2018年9月14日 下午7:19:55
 * 作用：提供给不同的类型的验证码生成器的实现。包括验证码的具体生成逻辑
 */
public interface ValidateCodeGenerator {
	
	/**
	 * @Title:createImageCode
	 * @Description:TODO 验证码生成逻辑
	 * @param request
	 * @return:ValidateCode
	 * @author:Jiangxb
	 * @date: 2018年9月14日 下午7:22:20
	 */
	ValidateCode generate(ServletWebRequest request);
}
