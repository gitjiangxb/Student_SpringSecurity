package com.imooc.code;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.imooc.security.core.validate.code.ValidateCodeGenerator;
import com.imooc.security.core.validate.code.image.ImageCode;

/**
 * @Package:com.imooc.code
 * @ClassName:DemoImageCodeGenerator
 * @Description:TODO 自定义一个图片验证码的生成器 接口的实现类
 * @author:Jiangxb
 * @date:2018年9月14日 下午7:50:43
 * @Component("imageCodeGenerator")
 * 		使用了这个注解，会去覆盖自带的"imageValidateCodeGenerator" Bean
 * 	因为在图片验证码的生成器的配置类 ValidateCodeBeanConfig中 使用了
 * 		@ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
 */
//@Component("imageValidateCodeGenerator")
public class DemoImageCodeGenerator implements ValidateCodeGenerator {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public ImageCode generate(ServletWebRequest request) {
		logger.info("更高级的图形验证码生成器的实现类");
		// TODO Auto-generated method stub
		return null;
	}

}
