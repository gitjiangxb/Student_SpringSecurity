package com.imooc.security.core.authentication.mobile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import com.imooc.security.core.properties.SecurityConstants;

/**
 * @Package:com.imooc.security.core.authentication.mobile
 * @ClassName:SmsCodeAuthenticationFilter
 * @Description:TODO 手机验证码过滤器
 * @author:Jiangxb
 * @date:2018年9月16日 下午2:52:37
 * 作用：过滤短信登录请求，组装SmsCodeAuthenticationToken
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter{

	public static final String IMOOC_FORM_MOBILE_KEY = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;
	
    private String mobileParameter = IMOOC_FORM_MOBILE_KEY;
    
    /**
     * 当前过滤器是不是只处理post请求
     * @Fields:postOnly : TODO
     */
    private boolean postOnly = true;
	    
    public SmsCodeAuthenticationFilter()
    {
        super(new AntPathRequestMatcher(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, "POST"));
    }

    
    /* (non-Javadoc)
     * 认证流程
     * @see org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter#attemptAuthentication(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException
    {
    	// 当前的请求是不是post
        if(postOnly && !request.getMethod().equals("POST"))
        	// 不是post，抛出异常
            throw new AuthenticationServiceException((new StringBuilder()).append("Authentication method not supported: ").append(request.getMethod()).toString());
       
        String mobile = obtainMobile(request);
        
        if(mobile == null)
        	mobile = "";
        // 去除空格
        mobile = mobile.trim();
        // 实例化Token（一个参数，将是否认证设置为false）
        SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile);
        // 把请求的信息设置到Token里面
        setDetails(request, authRequest);
        // 将Token传递(到AuthenticationManager)进去
        return getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * @Title:obtainMobile
     * @Description:TODO 获取手机号
     * @param request
     * @return:String
     * @author:Jiangxb
     * @date: 2018年9月16日 下午3:09:25
     */
    private String obtainMobile(HttpServletRequest request) {
    	  return request.getParameter(mobileParameter);
	}

    /**
     * @Title:setDetails
     * @Description:TODO 把请求的信息设置到Token里面
     * @param request
     * @param authRequest
     * @return:void
     * @author:Jiangxb
     * @date: 2018年9月16日 下午3:09:58
     */
    protected void setDetails(HttpServletRequest request, SmsCodeAuthenticationToken authRequest)
    {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }


    public void setPostOnly(boolean postOnly)
    {
        this.postOnly = postOnly;
    }

	public String getMobileParameter() {
		return mobileParameter;
	}

	public void setMobileParameter(String mobileParameter) {
		Assert.hasText(mobileParameter, "Mobile parameter must not be empty or null");
		this.mobileParameter = mobileParameter;
	}

 

}
