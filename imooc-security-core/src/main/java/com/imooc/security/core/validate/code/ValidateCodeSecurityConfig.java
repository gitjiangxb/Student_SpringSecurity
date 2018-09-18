package com.imooc.security.core.validate.code;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

/**
 * @Package:com.imooc.security.core.validate.code
 * @ClassName:ValidateCodeSecurityConfig
 * @Description:TODO 验证码的安全配置
 * @author:Jiangxb
 * @date:2018年9月16日 下午11:10:12
 * 
 */
@Component("validateCodeSecurityConfig")
public class ValidateCodeSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	/**
	 * 注入 验证 验证码过滤器
	 * @Fields:validateCodeFilter : TODO
	 */
	@Autowired
	private Filter validateCodeFilter;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		/**
		 * 在过滤器之前执行：验证码过滤器 需要在 form登录的过滤器之前执行
		 * 	validateCodeFilter：验证码过滤器
		 *  AbstractPreAuthenticatedProcessingFilter：处理form登录的过滤器，与form登录有关的所有操作都是在这里进行的。
		 */
		http.addFilterBefore(validateCodeFilter, AbstractPreAuthenticatedProcessingFilter.class);
	}
	
}
