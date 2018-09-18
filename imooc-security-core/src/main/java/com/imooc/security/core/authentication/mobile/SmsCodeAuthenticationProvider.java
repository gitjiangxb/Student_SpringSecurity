package com.imooc.security.core.authentication.mobile;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Package:com.imooc.security.core.authentication.mobile
 * @ClassName:SmsCodeAuthenticationProvider
 * @Description:TODO 提供校验逻辑
 * @author:Jiangxb
 * @date:2018年9月16日 下午3:12:59
 * 
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {
	
	private  UserDetailsService userDetailsService;

	/* (non-Javadoc)
	 * 身份认证的逻辑
	 * 	用我们的UserDetailsService获取用户信息；获取到以后，拿这个用户信息重新组装一个已经认证的Authentication
	 * @see org.springframework.security.authentication.AuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		// 将传入的authentication强转位自己写的
		SmsCodeAuthenticationToken smsCodeAuthenticationToken = (SmsCodeAuthenticationToken)authentication;
		/**
		 *  通过smsCodeAuthenticationToken.getPrincipal()拿到手机号
		 *  因为在SmsCodeAuthenticationFilter.attemptAuthentication()
		 *  	过滤器中创建SmsCodeAuthenticationToken实例时已经将手机号写进去了
		 */
		UserDetails user = userDetailsService.loadUserByUsername((String) smsCodeAuthenticationToken.getPrincipal());
		
		// 将读出来的信息做一个判断
		if(user == null) {
			throw new InternalAuthenticationServiceException("无法获取用户信息");
		}
		
		// 若读到信息，重新创建Token（两个参数将是否认证设置为True）(用户信息，用户权限)
		SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(user, user.getAuthorities());
		// 将之前未认证的Details Copy 到已经认证的Details里面
		authenticationResult.setDetails(smsCodeAuthenticationToken.getDetails());
		
		return authenticationResult;
	}

	/* (non-Javadoc)
	 * 判断传入的是不是SmsCodeAuthenticationToken这种类型的
	 * @see org.springframework.security.authentication.AuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
	}

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

}
