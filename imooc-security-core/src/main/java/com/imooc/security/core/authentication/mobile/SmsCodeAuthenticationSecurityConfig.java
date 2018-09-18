package com.imooc.security.core.authentication.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @Package:com.imooc.security.core.authentication.mobile
 * @ClassName:SmsCodeAuthenticationSecurityConfig
 * @Description:TODO 手机短信验证码 【登录验证】的安全配置
 * @author:Jiangxb
 * @date:2018年9月16日 下午4:00:21
 * extends SecurityConfigurerAdapter<O, SecurityBuilder<O>>需要指定两个泛型
 * 第一个：DefaultSecurityFilterChain
 * 第二个：HttpSecurity
 */
@Component
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	
	@Autowired
	private AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;
	
	@Autowired
	private AuthenticationFailureHandler imoocAuthenticationFailureHandler;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		// 手机验证码过滤器
		SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
		// 1、将AuthenticationManager set进去
		smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		// 2、需要把它跟其他过滤器的失败/成功处理统一起来
		smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(imoocAuthenticationSuccessHandler);
		smsCodeAuthenticationFilter.setAuthenticationFailureHandler(imoocAuthenticationFailureHandler);
		
		// 提供校验逻辑
		SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
		// 1、将UserDetailsService  set进去
		smsCodeAuthenticationProvider.setUserDetailsService(userDetailsService);
		
		// 然后添加到SpringSecurity的安全框架里面
		http.authenticationProvider(smsCodeAuthenticationProvider)	// 这个方法会将自己的smsCodeAuthenticationProvider添加到AuthenticationManager管的Provider集合里面
			.addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);	// 将自己写的过滤器加到UsernamePasswordAuthenticationFilter这个过滤器之后
	}
}
