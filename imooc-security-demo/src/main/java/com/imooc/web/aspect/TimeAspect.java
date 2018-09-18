package com.imooc.web.aspect;

import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 切片——实现记录服务耗时
 * @author Jiangxb
 * 	@Aspect	把当前类标识为一个切面供容器读取
 *		@Before 标识一个前置增强方法，相当于BeforeAdvice的功能
 *		@After final增强，不管是抛出异常或者正常退出都会执行
 *		@AfterThrowing 异常抛出增强，相当于ThrowsAdvice
 *		@AfterReturning 后置增强，相当于AfterReturningAdvice，方法正常退出时执行
 *		@Around 环绕增强，相当于MethodInterceptor
 *
 *	注解上的表达式：
 *		https://docs.spring.io/spring/docs/4.3.19.RELEASE/spring-framework-reference/htmlsingle/#aop-pointcuts
 *		11.2.3 Declaring a pointcut  里面有详细例子
 */

@Aspect	//把当前类标识为一个切面供容器读取
//@Component	// 必须加这个注解(让这个切片成为spring容器里面的一部分)
public class TimeAspect {
	// 这表达式说明：任何返回值  UserController的任何方法(任何参数)
	@Around("execution(* com.imooc.web.controller.UserController.*(..))")
	public Object handlerControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("time aspect start");
		
		// 方法的参数
		Object[] args = pjp.getArgs();
		for (Object arg : args) {
			System.out.println("arg is " + arg);
		}
		long start = new Date().getTime();
		// 调用控制器里面方法的返回值
		Object object = pjp.proceed();	// 真正进入UserController 的 getInfo() 方法
		
		System.out.println("time filter 耗时："+ (new Date().getTime() -start));
		System.out.println("time aspect end");
		return object;
	}
}







