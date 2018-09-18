package com.imooc.security.core.validate.code;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import com.imooc.security.core.properties.MySecurityProperties;
import com.imooc.security.core.properties.SecurityConstants;


/**
 * @Package:com.imooc.security.core.validate.code
 * @ClassName:ValidateCodeFilter
 * @Description:TODO 验证 验证码过滤器
 * @author:Jiangxb
 * @date:2018年9月14日 上午10:10:40
 * ——extends OncePerRequestFilter
 * 这个工具类用来保证这个过滤器只被调用一次
 */
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {
	/**
	 * 验证码校验失败处理器
	 */
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	
	/**
	 * @Fields:mySecurityProperties : TODO  系统配置信息
	 */
	@Autowired
	private MySecurityProperties mySecurityProperties;
	
	/**
	 * @Fields:validateCodeProcessorHolder : TODO 系统中的校验码处理器拥有者
	 * 说明：ValidateCodeProcessorHolder 包含 ValidateCodeProcessor
	 * 	   AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor
	 */
	@Autowired
	private ValidateCodeProcessorHolder validateCodeProcessorHolder;
	
	/**
	 * @Fields:urlMap : TODO 存放所有需要校验验证码的url
	 */
	private Map<String, ValidateCodeType> urlMap = new HashMap<>();
	
	/**
	 * @Fields:pathMatcher : TODO 验证请求url与配置的url是否匹配的工具类
	 */
	private AntPathMatcher pathMatcher = new AntPathMatcher();
 
	/* (non-Javadoc)
	 * 初始化要拦截的url配置信息
	 * @see org.springframework.web.filter.GenericFilterBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();
		// 首先将默认的表单提交的url 添加到urlMap中(标准登录页面上form表单提交的地址)
		urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
		// 然后再去读取配置文件中配置的url
		addUrlToMap(mySecurityProperties.getCode().getImage().getUrl(), ValidateCodeType.IMAGE);
		
		urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
		addUrlToMap(mySecurityProperties.getCode().getSms().getUrl(), ValidateCodeType.SMS);
	}
 
	/**
	 * @Title:addUrlToMap
	 * @Description:TODO 将系统中配置的：需要校验验证码的URL根据校验的类型放入map
	 * @param urlString
	 * @param type
	 * @return:void
	 * @author:Jiangxb
	 * @date: 2018年9月16日 下午10:44:13
	 */
	protected void addUrlToMap(String urlString, ValidateCodeType type) {
		if (StringUtils.isNotBlank(urlString)) {
			// 取出配置里面的 图片验证码/短信验证码 拦截的接口 ，转换为String类型的数组
			String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
			for (String url : urls) {
				urlMap.put(url, type);
			}
		}
	}
 
 
	/**
	 * @Title:getValidateCodeType
	 * @Description:TODO 获取校验码的类型，如果当前请求不需要校验，则返回null
	 * @param request
	 * @return:ValidateCodeType
	 * @author:Jiangxb
	 * @date: 2018年9月17日 上午9:37:55
	 */
	private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
		ValidateCodeType result = null;
		/**
		 *  request.getMethod()：获取请求方式
		 *  然后：
		 * 		StringUtils.equalsIgnoreCase(null, null)   = true
	     * 		StringUtils.equalsIgnoreCase(null, "abc")  = false
	     * 		StringUtils.equalsIgnoreCase("abc", null)  = false
	     * 		StringUtils.equalsIgnoreCase("abc", "abc") = true
	     * 		StringUtils.equalsIgnoreCase("abc", "ABC") = true
		 */
		/**
		 *  1、这个过滤器只有在登录的时候才生效 并且 为post请求
		 *  	拦截的接口："/authentication/form"  "/authentication/mobile" 。。。。
		 *  2、现在使用配置了，修改下面的代码
		 *  	因为配置里面会存在类似(“/user/*”)这样的路径，不能使用equals去判断
		 *    要使用Spring的工具类： AntPathMatcher
		 */
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
			Set<String> urls = urlMap.keySet();
			for (String url : urls) {
				if (pathMatcher.match(url, request.getRequestURI())) {
					result = urlMap.get(url);
				}
			}
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * 执行过滤器内部方法
	 * @see org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
 
		ValidateCodeType type = getValidateCodeType(request);
		if (type != null) {
			logger.info("校验请求(" + request.getRequestURI() + ")中的验证码,验证码类型" + type);
			try {
				// 先找到对应的验证码处理器的类型，再去验证
				validateCodeProcessorHolder.findValidateCodeProcessor(type).validate(new ServletWebRequest(request, response));
				logger.info("验证码校验通过");
			} catch (ValidateCodeException exception) {
				authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
				return;
			}
		}
		// 如果不是登录，会去调用其他的过滤器
		chain.doFilter(request, response);
	}
	
}
