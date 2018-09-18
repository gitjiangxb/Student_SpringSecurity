/**
 * 
 */
package com.imooc.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.service.HelloService;

/**
 * 校验器
 * @author Jiangxb
 *	ConstraintValidator<A, T>
 *	两个泛型
 *		A：你要验证的注解是什么
 *		T：验证的类型(就是说你需要验证在String类型上面还是其他类型Object)
 */
public class MyConstraintValidator implements ConstraintValidator<MyConstraint,Object> {
	/**
	 * 说明：校验器这个类里面可以利用@Autowired注入任何你需要的东西 
	 * 举例:写一个服务 HelloService
	 */
	@Autowired
	private HelloService helloService;
	
	/**
	 * 校验器初始化时做的事情
	 */
	@Override
	public void initialize(MyConstraint constraintAnnotation) {
		// TODO Auto-generated method stub
		System.out.println("my Validator init(我的校验器初始化了)");
	}

	/**
	 * 校验逻辑(校验的值，上下文)
	 */
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		//体验这个方法里面可以添加其他的服务@Autowired
		System.out.println(helloService.greeting("tom"));
		System.out.println("校验的值"+ value);
		//返回true：校验成功；返回false：校验失败
		return false;
	}

}
