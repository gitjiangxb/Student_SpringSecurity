package com.imooc.security.core.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.imooc.security.core.properties.MySecurityProperties;
import com.imooc.security.core.validate.code.image.ImageCodeGenerator;
import com.imooc.security.core.validate.code.sms.DefaultSmsCodeSender;
import com.imooc.security.core.validate.code.sms.SmsCodeSender;

/**
 * @Package:com.imooc.security.core.validate.code
 * @ClassName:ValidateCodeBeanConfig
 * @Description:TODO 验证码的生成器的 配置类
 * @author:Jiangxb
 * @date:2018年9月14日 下午7:33:53
 * 作用：当使用者有不同的验证码生成器的实现，可以通过一定的规则去覆盖掉我们提供的默认接口
 * 	如：图片验证码生成器，若要重写覆盖，只需要创建一个名为“imageValidateCodeGenerator”的Bean；并实现“ValidateCodeGenerator”接口—— DemoImageCodeGenerator
 * 	       发送手机短信验证码，若要重写覆盖，只需要实现“SmsCodeSender”接口；—— DemoSmsCodeSender
 * 不管你怎么覆盖/重写，只要按照上面的命名格式，系统都会调用自定义的，从而覆盖掉默认提供的
 */
@Configuration
public class ValidateCodeBeanConfig {
	
	/**
	 * 系统配置类
	 * @Fields:mySecurityProperties : TODO
	 */
	@Autowired
	private MySecurityProperties mySecurityProperties;
	
	/**
	 * @Title:imageCodeGenerator
	 * @Description:TODO 默认使用自带的图片验证码的生成器接口的实现类
	 * @return:ValidateCodeGenerator
	 * @author:Jiangxb
	 * @date: 2018年9月14日 下午7:36:37
	 * 方法的名字 就是放到spring容器里面bean的名字
	 * 跟ValidateCodeController中注入ValidateCodeGenerator时的名称一样
	 * 
	 * @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")说明：
	 * 	系统启动后，初始化这个bean之前，会先去spring容器里面找“imageValidateCodeGenerator”这个bean,如果容器里面找到了，
	 * 就不会使用这个bean(也就是下面的方法)，而是用找到那个。如：DemoImageCodeGenerator
	 */
	@Bean	
	@ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
	public ValidateCodeGenerator imageValidateCodeGenerator() {
		ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
		codeGenerator.setMySecurityProperties(mySecurityProperties);
		return codeGenerator;
	}
	
	/**
	 * @Title:smsCodeSender
	 * @Description:TODO 默认使用自带的发送短信验证码的接口实现类
	 * @return:SmsCodeSender
	 * @author:Jiangxb
	 * @date: 2018年9月15日 下午5:47:27
	 * 目前这里存在问题：DemoSmsCodeSender
	 */
	@Bean	
	@ConditionalOnMissingBean(SmsCodeSender.class)	// 在系统里面找到了这个接口的实现也不会执行下面
	public SmsCodeSender smsCodeSender() {
		return new DefaultSmsCodeSender();
	}
	
}
