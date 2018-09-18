package com.imooc.security.core.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.imooc.security.core.properties.SecurityConstants;

/**
 * @Package:com.imooc.security.core.authentication
 * @ClassName:AbstractChannelSecurityConfig
 * @Description:TODO 抽象通道安全配置：表单登录的配置
 * @author:Jiangxb
 * @date:2018年9月16日 下午11:42:02
 * 
 */
public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter {
	
	/**
	 * 注入 自定义的成功处理程序
	 * 	注意@Component("imoocAuthenticationSuccessHandler")
	 * @Fields:imoocAuthenticationSuccessHandler : TODO
	 */
	@Autowired
	private AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;
	
	
	/**
	 * 注入 自定义的失败处理程序
	 * 	注意@Component("imoocAuthenticationFailureHandler")
	 * @Fields:imoocAuthenticationFailureHandler : TODO
	 */
	@Autowired
	private AuthenticationFailureHandler imoocAuthenticationFailureHandler;
	
	
	/**
	 * @Title:applyPasswordAuthenticationConfig
	 * @Description:TODO 密码相关的一些配置
	 * @param http
	 * @throws Exception
	 * @return:void
	 * @author:Jiangxb
	 * @date: 2018年9月16日 下午11:42:12
	 */
	protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
		// 表单登录
		http.formLogin()
			.loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)	// 自定义登录界面(必须指定不需要身份认证)	会去执行BrowserSecurityController控制器里面的方法
			.loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)	// 标准登录页的form提交地址
			.successHandler(imoocAuthenticationSuccessHandler)	// 处理成功后调用的处理程序(自定义)
			.failureHandler(imoocAuthenticationFailureHandler);	// 处理失败后调用的处理程序(自定义)
	}

}
