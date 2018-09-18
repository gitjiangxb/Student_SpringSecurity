package com.imooc.security.core.authentication.mobile;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * @Package:com.imooc.security.core.authentication.mobile
 * @ClassName:SmsCodeAuthenticationToken
 * @Description:TODO 短信验证码的Token
 * @author:Jiangxb
 * @date:2018年9月16日 下午2:37:29
 * 作用：封装我们的登录信息；在身份认证之前里面封装的是我们传入的手机号，
 * 	身份认证成功以后里面放的是认证成功的用户信息
 */
public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken{
	
	private static final long serialVersionUID = 420L;
	
    /**
     * 放认证信息的，认证之前放手机号，认证之后。。。。
     * @Fields:principal : TODO
     */
    private final Object principal;	

    /**
     * @Title:SmsCodeAuthenticationToken
     * @param mobile
     */
    public SmsCodeAuthenticationToken(String mobile)
    {
        super(null);
        this.principal = mobile;
        setAuthenticated(false);	// 没登录时，principal放手机号
    }

    /**
     * @Title:SmsCodeAuthenticationToken
     * @param principal
     * @param authorities
     */
    public SmsCodeAuthenticationToken(Object principal, Collection authorities)
    {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    public Object getCredentials()
    {
        return null;
    }

    public Object getPrincipal()
    {
        return principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException
    {
        if(isAuthenticated)
        {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
            return;
        }
    }

    public void eraseCredentials()
    {
        super.eraseCredentials();
    }

}
