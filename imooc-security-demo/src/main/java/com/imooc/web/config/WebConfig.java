package com.imooc.web.config;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.imooc.web.filter.TimeFilter;
import com.imooc.web.interceptor.TimeInterceptor;

/**
 * 配置类
 * @author Jiangxb
 *将第三方的过滤器加入进去/加入自己定义的过滤器。并且可以选择过滤的服务请求
 */
//@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	/****************下面是写 拦截器 时编辑的**************************/
	@Autowired
	private TimeInterceptor timeInterceptor;
	
	/**
	 * 同步的处理，使用它来注册拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(timeInterceptor);
	}
	
	
	/****************下面是写 异步处理 时编辑的**************************/
	
	/**
	 * spring 线程池技术,是使用java.util.concurrent.ThreadPoolExecutor进行实现的
	 * @return
	 */
	public ThreadPoolTaskExecutor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);	//核心线程数
		executor.setMaxPoolSize(30);	//最大线程数
		executor.setQueueCapacity(100);	//队列最大长度
		executor.setThreadNamePrefix("ronnie-task-");
		executor.initialize();
		return executor;
	}
	
	/**
	 * 异步的处理，使用它来注册拦截器
	 */
	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
		/**
		 * 如果你要拦截异步处理的请求，必须采用以下的两个方法(选其一，看你的异步实现是用什么)来单独注册拦截器
		 */
//		configurer.registerCallableInterceptors(interceptors);
//		configurer.registerDeferredResultInterceptors(interceptors);
		// 异步请求默认的超时时间
//		configurer.setDefaultTimeout(timeout);
		/**
		 * 在默认的情况下，你用Runnable(Callable)去执行异步请求的时候，spring是用了自己的一个简单的线程池来处理的，
		 * 它不会重用这个线程池里面的线程，而是每次当你调用的时候去重新开一个线程；可以利用下面这个方法去设置一些可重用的线程池
		 * 来替代spring默认的不重用的线程池
		 */
//		configurer.setTaskExecutor(getAsyncExecutor());
		
		super.configureAsyncSupport(configurer);
	}
	
	
	/****************下面是写 过滤器 时编辑的**************************/
	
	@Bean
	public FilterRegistrationBean timeFilter() {
		
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		// 定义的过滤器(第三方的过滤器)
		TimeFilter timeFilter = new TimeFilter();
		registrationBean.setFilter(timeFilter);
		// 可以自定义什么服务请求需要过滤
		List<String> urls = new ArrayList<>();
		urls.add("/*");	// 这是全部的请求
		return registrationBean;
	}
}
