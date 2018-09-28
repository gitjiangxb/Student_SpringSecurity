package com.imooc.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @Package:com.imooc.security.core.social
 * @ClassName:ImoocSpringSocialConfigurer
 * @Description:TODO 自定义SpringSocial的配置类
 * @author:Jiangxb
 * @date:2018年9月28日 下午2:22:20
 * 	extends SpringSocialConfigurer通过重写postProcess这个方法，来改变地址(网站回调域：http://www.pinzhi365.com/qqLogin/callback.do)
 * 	
 */
public class ImoocSpringSocialConfigurer extends SpringSocialConfigurer {
	
	/**
	 * @Fields:filterProcessesUrl : TODO 过滤器要处理的请求路径，需要做成可配置的
	 */
	private String filterProcessesUrl;
	
	/**
	 * @Title:ImoocSpringSocialConfigurer
	 * @param filterProcessesUrl
	 * 通过构造函数来设置值
	 */
	public ImoocSpringSocialConfigurer(String filterProcessesUrl) {
		this.filterProcessesUrl = filterProcessesUrl;
	}
	
	/**
	 * 重写postProcess方法，改变默认的拦截的url
	 */
	@Override
	protected <T> T postProcess(T object) {
		SocialAuthenticationFilter filter = (SocialAuthenticationFilter)super.postProcess(object);
		filter.setFilterProcessesUrl(filterProcessesUrl);
		return (T)filter;
	}
}
