package com.imooc.security.browser.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.security.browser.supper.SimpleResponse;
import com.imooc.security.core.properties.LoginResponseType;
import com.imooc.security.core.properties.MySecurityProperties;

/**
 * 自定义认证失败的处理程序
 * @Package: com.imooc.security.browser.authentication
 * @author: Jiangxb
 * @date: 2018年9月13日下午3:38:20
 * 
 */
@Component("imoocAuthenticationFailureHandler")
public class ImoocAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
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
	 * 登录失败会调用
	 * (non-Javadoc)
	 * @see org.springframework.security.web.authentication.AuthenticationFailureHandler#onAuthenticationFailure(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
	 * 
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse,
			AuthenticationException authenticationexception) throws IOException, ServletException {
		
		logger.info("登录失败");
		
		// 如果配置的LoginType是JSON，就调自己写的处理方式
		if(LoginResponseType.JSON.equals(mySecurityProperties.getBrowser().getLoginType())) {
			httpservletresponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			httpservletresponse.setContentType("application/json;charset=UTF-8");
			// 先转成JSON格式的字符串，然后利用response以“application/json”的形式write回到响应里面去
//			httpservletresponse.getWriter().write(objectMapper.writeValueAsString(authenticationexception)); // 会打印错误堆栈信息
			httpservletresponse.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(authenticationexception.getMessage())));	// 改成只打印错误信息
		}else {
			// springBoot 默认的处理方式，跳到错误界面
			super.onAuthenticationFailure(httpservletrequest, httpservletresponse, authenticationexception);
		}
	}

}
