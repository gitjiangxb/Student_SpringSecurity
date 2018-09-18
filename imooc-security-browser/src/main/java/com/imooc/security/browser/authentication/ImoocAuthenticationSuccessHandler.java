package com.imooc.security.browser.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.security.core.properties.LoginResponseType;
import com.imooc.security.core.properties.MySecurityProperties;

/**
 * 自定义认证成功的处理程序
 * 	—— implements AuthenticationSuccessHandler
 * @Package: com.imooc.security.browser.authentication
 * @author: Jiangxb
 * @date: 2018年9月13日上午10:40:39
 * @Component注解：声明为spring的组件
 */
@Component("imoocAuthenticationSuccessHandler")
public class ImoocAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	
	/**
	 * 将Authentication转换为JSON格式的 工具类
	 * @Fields:objectMapper : TODO
	 */
	@Autowired
	private ObjectMapper objectMapper;
	
	/**
	 * 注入系统配置类
	 * @Fields:mySecurityProperties : TODO
	 */
	@Autowired
	private MySecurityProperties mySecurityProperties;
	
	/*
	 * 登录成功会调用
	 * (non-Javadoc)
	 * @see org.springframework.security.web.authentication.AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
	 * Authentication接口也是SpringSecurity的核心接口，作用：封装我们的认证信息
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse,
			Authentication authentication) throws IOException, ServletException {
		
		logger.info("登录成功");	// 返回的状态码是200
		
		// 如果配置的LoginType是JSON，就调自己写的处理方式
		if(LoginResponseType.JSON.equals(mySecurityProperties.getBrowser().getLoginType())) {
			httpservletresponse.setContentType("application/json;charset=UTF-8");
			// 先转成JSON格式的字符串，然后利用response以“application/json”的形式write回到响应里面去
			httpservletresponse.getWriter().write(objectMapper.writeValueAsString(authentication));
		}else {
			// 如果不是，就调用父类的方法，进行跳转
			super.onAuthenticationSuccess(httpservletrequest, httpservletresponse, authentication);
		}
		
	}

}
