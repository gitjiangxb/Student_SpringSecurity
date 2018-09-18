package com.imooc.web.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


/**
 * 拦截器——实现记录服务耗时
 * @author Jiangxb
 *	光声明为@Component，拦截器是不起作用的，需要额外的配置
 *	WebConfig类中做修改：
 *		extends WebMvcConfigurerAdapter
 *		并重写addInterceptors()方法
 */
//@Component
public class TimeInterceptor implements HandlerInterceptor {
	
	// 都会被调用(不管成功还是失败)
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("afterCompletion");
		
		long start = (long) request.getAttribute("startTime");
		System.out.println("time interceptor 耗时："+ (new Date().getTime() -start));
		System.out.println("ex is " + ex);

	}
	// 控制器方法 被调用之后(抛出了异常，这个方法不被调用)
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mView)
			throws Exception {
		System.out.println("postHandle");
		
		long start = (long) request.getAttribute("startTime");
		System.out.println("time interceptor 耗时："+ (new Date().getTime() -start));
		
	}

	// 控制器方法 被调用之前
	// 拦截器较过滤器的优势：有第三个参数handler，它是真正用来处理当前这个请求的控制器的那个方法的声明
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println("preHandle");
		
		System.err.println("类名：" + ((HandlerMethod)handler).getBean().getClass().getName());	//类名
		System.err.println("方法名：" + ((HandlerMethod)handler).getMethod().getName());			//方法名
		request.setAttribute("startTime", new Date().getTime());
		return true;	// 布尔值，决定是否继续调用后面的方法
	}

}
