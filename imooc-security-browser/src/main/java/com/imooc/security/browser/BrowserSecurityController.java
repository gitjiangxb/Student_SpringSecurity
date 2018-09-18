package com.imooc.security.browser;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.imooc.security.browser.supper.SimpleResponse;
import com.imooc.security.core.properties.SecurityConstants;
import com.imooc.security.core.properties.MySecurityProperties;


/**
 * @Package:com.imooc.security.browser
 * @ClassName:BrowserSecurityController
 * @Description:TODO 处理用户请求
 * @author:Jiangxb
 * @date:2018年9月13日上午9:46:47
 * 
 */
@RestController
public class BrowserSecurityController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 说明：如果需要身份认证，它会做一个跳转(根据configure配置里面的http.loginPage());
	 * 	但是在执行这个跳转之前SpringSecurity 利用HttpSessionRequestCache 将当前的请求缓存到session里面 
	 * SavedRequest就是之前保存的
	 */
	private RequestCache requestCache = new HttpSessionRequestCache();
	
	// 做跳转的工具	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	/**
	 * 注入系统配置类
	 * @Fields:mySecurityProperties : TODO
	 */
	@Autowired
	private MySecurityProperties mySecurityProperties;
	
	
	/**
	 * @Title:requireAuthentication
	 * @Description:TODO 当需要身份认证时跳转到这里
	 * @param request	请求
	 * @param response	响应
	 * @return:SimpleResponse
	 * @author:Jiangxb
	 * @date: 2018年9月13日上午9:50:47
	 */
//	@RequestMapping("/authentication/require")
	@RequestMapping(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED) // 返回状态码401，未授权的状态码
	public SimpleResponse requireAuthentication(HttpServletRequest request,HttpServletResponse response) {
		
		// 做一个判断：引发跳转的是html还是其他的请求，需要一个类(RequestCache)拿到引发跳转的请求
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		
		if(savedRequest != null) {
			String targetUrl = savedRequest.getRedirectUrl();
			logger.info("引发跳转的请求是：" + targetUrl);
			// 判断是否以.html结尾
			if(StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
				// 跳转到登录页
				try {
					/**
					 * (请求，响应，url(第二个问题：如何让用户自己去配登录页))
					 * 问题解决：从系统配置类里面读取；如果用户没有配置，就跳转标准登录页。
					 * 需要赋初值	BrowserProperties . loginPage = "/imooc-signIn.html";
					 */
					redirectStrategy.sendRedirect(request, response, mySecurityProperties.getBrowser().getLoginPage());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return new SimpleResponse("访问的服务需要身份认证，请引导用户到登录页");
	}
}
