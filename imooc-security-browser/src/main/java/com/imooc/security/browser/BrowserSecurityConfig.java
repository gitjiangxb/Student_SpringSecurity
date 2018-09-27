package com.imooc.security.browser;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.imooc.security.core.authentication.AbstractChannelSecurityConfig;
import com.imooc.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.imooc.security.core.properties.MySecurityProperties;
import com.imooc.security.core.properties.SecurityConstants;
import com.imooc.security.core.validate.code.ValidateCodeSecurityConfig;

/**
 * @Package:com.imooc.security.browser
 * @ClassName:BrowserSecurityConfig
 * @Description:TODO 浏览器安全的配置
 * @author:Jiangxb
 * @date:2018年9月13日上午9:55:33
 *  extends AbstractChannelSecurityConfig
 *		抽象通道安全配置(表单登录的配置)
 */
@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig  {
	
	/**
	 * 注入系统配置类
	 * @Fields:mySecurityProperties : TODO
	 */
	@Autowired
	private MySecurityProperties mySecurityProperties;
	
	/**
	 * 数据源
	 * @Fields:dataSource : TODO
	 */
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	/**
	 * 注入 短信验证码登录的一些配置，因为手机验证码的逻辑是自己实现的
	 * @Fields:smsCodeAuthenticationSecurityConfig : TODO
	 */
	@Autowired
	private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
	
	/**
	 * 校验码相关的一些配置
	 * @Fields:validateCodeSecurityConfig : TODO
	 */
	@Autowired
	private ValidateCodeSecurityConfig validateCodeSecurityConfig;
	
	/**
	 * @Title:persistentTokenRepository
	 * @Description:TODO 配置一个TokenRepository来去读写我们的数据库
	 * @return:PersistentTokenRepository
	 * @author:Jiangxb
	 * @date: 2018年9月15日 下午1:57:06
	 * 需要为其指定 数据源
	 * 	@Autowired
	 *	private DataSource dataSource;
	 * 同时需要创建一张表：JdbcTokenRepositoryImpl.CREATE_TABLE_SQL变量
	 * 查找到Token后，要将其保存再UserDetailsService里面，方便执行下一步登录逻辑，
	 * 	因此还需要配置UserDetailsService
	 * 		@Autowired
	 *		private UserDetailsService userDetailsService;
	 */
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		// 为其设置 数据源(已经在application.properties配置文件里面配置了数据源)
		tokenRepository.setDataSource(dataSource);
		/**
		 *  配置项：在启动的时候去创建表(也可以直接拿脚本去数据库创建：CREATE_TABLE_SQL)
		 *  	第一次运行时需要配置这个，第二次就不再需要这个配置了
		 */
//		tokenRepository.setCreateTableOnStartup(true);
		return tokenRepository;
	}
	
	/**
	 * 配置 密码 
	 * @return BCryptPasswordEncoder() 实现了PasswordEncoder接口
	 */
	@Bean
	public 	PasswordEncoder passwordEncoder() {
		// 这是返回SpringSecurity的加密方式，你可以自己写加密方式，但是必须实现PasswordEncoder接口
		return new BCryptPasswordEncoder(); 
	}
	
	/**
	 * configure()方法有三种，只是参数不一样
	 * 	AuthenticationManagerBuilder
	 * 	HttpSecurity：它允许对特定的http请求基于安全考虑进行配置；默认情况下，适用于所有的请求，但可以使用
	 * 				requestMatcher(RequestMatcher)或者其他相似的方法进行限制
	 * 	WebSecurity
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 密码相关的一些配置(表单登录方式/成功处理器/失败处理器。。)
		applyPasswordAuthenticationConfig(http);
		
		http.apply(validateCodeSecurityConfig)			// 验证码相关的配置
				.and()
			.apply(smsCodeAuthenticationSecurityConfig)	// 短信验证码【登录】的一些配置
				.and()
			.rememberMe()			// 浏览器特有的配置(记住我)
				.tokenRepository(persistentTokenRepository())
				.tokenValiditySeconds(mySecurityProperties.getBrowser().getRememberMeSeconds())
				.userDetailsService(userDetailsService)
				.and()
			.authorizeRequests()	// 浏览器特有的配置(对请求做授权)
				.antMatchers(
					SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
					SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
					mySecurityProperties.getBrowser().getLoginPage(),
					SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*")
					.permitAll()	// 匹配到这个url时，不需要身份认证
				.anyRequest()		// 任何请求
				.authenticated()	// 都需要身份认证
				.and()
			.csrf().disable();		// 跨站请求伪造防护不可用(disable)
	} 
}
