package com.imooc.security.core.validate.code.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import com.imooc.security.core.properties.custom.ComponentProperties;
import com.imooc.security.core.validate.code.ValidateCode;
import com.imooc.security.core.validate.code.ValidateCodeException;
import com.imooc.security.core.validate.code.ValidateCodeGenerator;
import com.imooc.security.core.validate.code.ValidateCodeProcessor;
import com.imooc.security.core.validate.code.ValidateCodeType;

/**
 * @Package:com.imooc.security.core.validate.code.impl
 * @ClassName:AbstractValidateCodeProcessor 
 * @Description:TODO 抽象验证码处理器
 * @author:Jiangxb
 * @date:2018年9月15日 下午8:43:53
 * 	implements ValidateCodeProcessor	验证码处理器
 * @param <C>
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 操作session的工具类
	 * @Fields:sessionStrategy : TODO
	 */
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
	
	/**
	 * 收集系统中所有的 {@link ValidateCodeGenerator} 接口（验证码的生成器）的实现。
	 *  目前存在两个：
	 *  	@Component("smsCodeGenerator")
	 *  	SmsCodeGenerator implements ValidateCodeGenerator
	 *  	在ValidateCodeBeanConfig.imageCodeGenerator()方法上面的注解
	 *  	ImageCodeGenerator implements ValidateCodeGenerator
	 * @Fields:validateCodeGenerators : TODO
	 * spring看到这样一个map，在启动的时候，会查找spring容器里面所有ValidateCodeGenerator接口的实现；找到以后
	 * 	把对应的Bean的名字作为key保存起来
	 */
	@Autowired
	private Map<String, ValidateCodeGenerator> validateCodeGenerators;
	
	/**
	 * @Fields:componentProperties : TODO 使用自定义的配置文件读取信息(测试用)
	 */
	@Autowired
	private ComponentProperties componentProperties;
	
	/* (non-Javadoc)
	 * 封装了三个步骤：
	 * @see com.imooc.security.core.validate.code.ValidateCodeProcessor#create(org.springframework.web.context.request.ServletWebRequest)
	 */
	@Override
	public void create(ServletWebRequest request) throws Exception {
		System.out.println("测试自定义配置文件：" + componentProperties.toString());
		logger.info(validateCodeGenerators.toString());
		// 第一步：创建/生成验证码（generate()涉及到spring的依赖查找功能）
		C validateCode = generate(request);
		// 第二步：保存验证码到Session
		save(request, validateCode);
		// 第三步：发送/写入Io
		send(request, validateCode);
	}

	/**
	 * @Title:save
	 * @Description:TODO 保存校验码到 session
	 * @param request
	 * @param validateCode
	 * @return:void
	 * @author:Jiangxb
	 * @date: 2018年9月15日 下午10:10:14
	 */
	private void save(ServletWebRequest request, C validateCode) {
		/**
		 *  2、将随机数保存到Session中
		 *  setAttribute(RequestAttributes requestattributes, String s, Object obj);
		 *  第一个参数：把请求传入
		 *  第二个参数：key
		 *  第三个参数：value
		 */
		logger.info("保存进去的Key：" + getSessionKey(request));
		sessionStrategy.setAttribute(request, getSessionKey(request), validateCode);
	}

	/**
	 * @Title:generate
	 * @Description:TODO 生成校验码
	 * @param request
	 * @return:C
	 * @author:Jiangxb
	 * @date: 2018年9月15日 下午10:07:20
	 */
	@SuppressWarnings("unchecked")
	private C generate(ServletWebRequest request) {
		/**
		 * 1、根据不同的类型去调用不同的验证码的生成器
		 * 	imageValidateCodeGenerator / smsValidateCodeGenerator
		 * Spring的依赖搜索
		 */
		String type = getValidateCodeType(request).toString().toLowerCase();
		String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
		ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
		if (validateCodeGenerator == null) {
			throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
		}
		return (C) validateCodeGenerator.generate(request);
	}
	
	/**
	 * @Title:send
	 * @Description:TODO 发送校验码，由子类实现
	 * @param request
	 * @param validateCode
	 * @throws Exception
	 * @return:void
	 * @author:Jiangxb
	 * @date: 2018年9月15日 下午8:53:24
	 * 由不同的子类去实现：
	 * 	SmsCodeProcessor 短信验证码处理器
	 * 	ImageCodeProcessor 图片验证码处理器
	 */
	protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;
	
	
	/**
	 * 实现父类的验证方法
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void validate(ServletWebRequest request) {
 
		ValidateCodeType processorType = getValidateCodeType(request);
		String sessionKey = getSessionKey(request);
 
		// 从session中获取验证码
		C codeInSession = (C) sessionStrategy.getAttribute(request, sessionKey);
 
		String codeInRequest;
		try {
			// 从请求中获取验证码
			codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
					processorType.getParamNameOnValidate());
		} catch (ServletRequestBindingException e) {
			throw new ValidateCodeException("获取验证码的值失败");
		}
 
		if (StringUtils.isBlank(codeInRequest)) {
			throw new ValidateCodeException(processorType + "验证码的值不能为空");
		}
 
		if (codeInSession == null) {
			throw new ValidateCodeException(processorType + "验证码不存在");
		}
 
		if (codeInSession.isExpried()) {
			sessionStrategy.removeAttribute(request, sessionKey);
			throw new ValidateCodeException(processorType + "验证码已过期");
		}
 
		if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
			throw new ValidateCodeException(processorType + "验证码不匹配");
		}
 
		sessionStrategy.removeAttribute(request, sessionKey);
	}
 
	/**
	 * @Title:getValidateCodeType
	 * @Description:TODO 根据请求的url获取校验码的类型
	 * @p
	 * .aram request
	 * @return:ValidateCodeType
	 * @author:Jiangxb
	 * @date: 2018年9月17日 上午10:53:01
	 */
	private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
		// getClass().getSimpleName() 获取源代码中给出的“底层类”简称。
		String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
		return ValidateCodeType.valueOf(type.toUpperCase());
	}
	
	
	/**
	 * @Title:getSessionKey
	 * @Description:TODO 构建验证码放入session时的key
	 * @param request
	 * @return:String
	 * @author:Jiangxb
	 * @date: 2018年9月17日 上午10:53:30
	 */
	private String getSessionKey(ServletWebRequest request) {
		return SESSION_KEY_PREFIX + getValidateCodeType(request).toString().toUpperCase();
	}
	
	

}
