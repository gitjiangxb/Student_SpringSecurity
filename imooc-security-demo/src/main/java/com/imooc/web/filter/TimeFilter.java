package com.imooc.web.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 过滤器——实现记录服务耗时
 * @author Jiangxb
 *	@Component注解，可以让过滤器起作用
 *	所有的服务请求都会经过这个过滤器。
 *
 *有时也会用第三方的过滤器来拦截，第三方的过滤器是没有@Component注解。
 *传统的是又web.xml配置文件，但是springBoot是没有这个配置文件的，
 *那如何使用第三方的过滤器？
 *	第一步：新建一个包路径com.imooc.web.config
 *	第二步：创建类WebConfig类，并用@Configuration注解修饰
 *	第三步：创建一个方法，返回类型为FilterRegistrationBean，并用@Bean注解修饰
 */
// @Component	// (为了测试第三方的过滤器的使用，去掉这个注解)
public class TimeFilter implements Filter {

	// 销毁
	@Override
	public void destroy() {
		System.out.println("time filter destroy");
	}

	// 处理过滤器逻辑
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("time filter start");
		long start = new Date().getTime();
		chain.doFilter(request, response); //调用下一个
		System.out.println("time filter 耗时："+ (new Date().getTime() -start));
		System.out.println("time filter finish");
	}

	// 初始化
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("time filter init");
	}

}
