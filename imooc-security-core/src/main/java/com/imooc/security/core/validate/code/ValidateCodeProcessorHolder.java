package com.imooc.security.core.validate.code;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Package:com.imooc.security.core.validate.code
 * @ClassName:ValidateCodeProcessorHolder
 * @Description:TODO 验证码处理器拥有者
 * @author:Jiangxb
 * @date:2018年9月16日 下午11:30:13
 * 
 */
@Component
public class ValidateCodeProcessorHolder {
	/**
	 * 收集系统中所有的 {@link ValidateCodeProcessor} 验证码的处理器接口的实现。
	 * @Fields:validateCodeProcessors : TODO 
	 * spring依赖查找
	 */
	@Autowired
	private Map<String, ValidateCodeProcessor> validateCodeProcessors;
 
	/**
	 * @Title:findValidateCodeProcessor
	 * @Description:TODO 根据验证码类型查找对应的验证码处理器
	 * @param type 验证码类型
	 * @return:ValidateCodeProcessor
	 * @author:Jiangxb
	 * @date: 2018年9月17日 上午10:02:19
	 */
	public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type) {
		return findValidateCodeProcessor(type.toString().toLowerCase());
	}
 
	/**
	 * @Title:findValidateCodeProcessor
	 * @Description:TODO 根据请求类型查找对应的验证码处理器
	 * @param type	image / sms
	 * @return:ValidateCodeProcessor
	 * @author:Jiangxb
	 * @date: 2018年9月16日 下午11:28:57
	 */
	public ValidateCodeProcessor findValidateCodeProcessor(String type) {
		String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
		ValidateCodeProcessor processor = validateCodeProcessors.get(name);
		if (processor == null) {
			throw new ValidateCodeException("验证码处理器" + name + "不存在");
		}
		return processor;
	}

}
