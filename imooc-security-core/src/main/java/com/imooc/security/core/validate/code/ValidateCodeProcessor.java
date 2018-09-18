package com.imooc.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @Package:com.imooc.security.core.validate.code
 * @ClassName:ValidateCodeProcessor
 * @Description:TODO 验证码的处理器接口
 * @author:Jiangxb
 * @date:2018年9月15日 下午10:40:10
 * 作用：提供给不同的类型的验证码处理器的实现。包括创建和校验
 */
public interface ValidateCodeProcessor {
	/**
	 * 验证码放入session时的前缀
	 */
	String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";
	/**
	 * @Title:create
	 * @Description:TODO 创建校验码
	 * @param request	Spring的一个工具类ServletWebRequest(封装请求跟响应)
	 * @throws Exception
	 * @return:void
	 * @author:Jiangxb
	 * @date: 2018年9月15日 下午8:41:10
	 */
	void create(ServletWebRequest request) throws Exception;
	
	/**
	 * @Title:validate
	 * @Description:TODO 校验验证码
	 * @param servletWebRequest
	 * @return:void
	 * @author:Jiangxb
	 * @date: 2018年9月17日 上午11:20:04
	 */
	void validate(ServletWebRequest servletWebRequest);
}
