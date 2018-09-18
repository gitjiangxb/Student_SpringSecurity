package com.imooc.security.core.validate.code;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.imooc.security.core.properties.SecurityConstants;


/**
 * @Package:com.imooc.security.core.validate.code
 * @ClassName:ValidateCodeController
 * @Description:TODO 校验码的控制器
 * @author:Jiangxb
 * @date:2018年9月14日上午9:27:24
 * 
 */
@RestController
public class ValidateCodeController {
	
	@Autowired
	private ValidateCodeProcessorHolder validateCodeProcessorHolder;
	
	/**
	 * @Title:createCode
	 * @Description:TODO 创建验证码，根据验证码类型不同，调用不同的 {@link ValidateCodeProcessor}接口实现
	 * @param request	请求
	 * @param response	响应
	 * @param type		获取验证码的类型：image / sms
	 * @throws Exception
	 * @return:void
	 * @author:Jiangxb
	 * @date: 2018年9月15日 下午9:26:11
	 */
	@GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{type}")
	public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) throws Exception {
		/**
		 * 根据类型去调用不同的验证码处理器去 调用创建验证码方法，然后再创建验证码方法里面根据不同类型去生成验证码逻辑
		 */
		validateCodeProcessorHolder.findValidateCodeProcessor(type).create(new ServletWebRequest(request, response));
	}
}
