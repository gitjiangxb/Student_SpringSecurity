package com.imooc.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 自定义注解(还得写校验器)
 * @author Jiangxb
 *	@Target这个注解指明你的注解能标注在哪里
 *		ElementType.METHOD	方法上
 *		ElementType.FIELD	字段上
 *		。。。。
 */
@Target({ElementType.METHOD,ElementType.FIELD})		//指明你的注解能标注在哪里
@Retention(RetentionPolicy.RUNTIME)					//运行时注解
@Constraint(validatedBy = MyConstraintValidator.class)		//表明它的校验逻辑由谁来执行
public @interface MyConstraint {
	/**
	 * 这三个属性 必须写
	 */
	//校验不过的时候发的信息
	String message() default "{org.hibernate.validator.constraints.NotBlank.message}";
	//
	Class<?>[] groups() default { };
	//
	Class<? extends Payload>[] payload() default { };
	
}
