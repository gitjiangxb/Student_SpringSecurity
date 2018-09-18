package com.imooc.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.imooc.dto.User;
import com.imooc.dto.UserQueryCondition;



import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 编写 RESTfulAPI API
 * @author Jiangxb
 *常用注解：	
 *	@RestController 标明此Controller提供RestAPI
 *	@RequsetMapping及其变体，映射http请求url到java方法
 *	@RequestParam 映射请求参数到java方法的参数
 *		required参数 表示参数是否必填(false:可以不填)
 *		value参数 和 name参数是一样的效果
 *	@PageableDefult	指定分页参数默认值(Spring Data)
 *		page参数	表示从第几页开始
 *		size参数	每页显示多少条
 *		sort参数	分页规则
 *
 *	@PathVariable 映射url片段到java方法的参数
 *		name参数 和 value参数一样，指定变量的名字
 *	在url声明中使用正则表达式
 *		传统：@RequestMapping(value="/user/{id}",method = RequestMethod.GET)
 *		正则表达式：@RequestMapping(value="/user/{id:\\d+}",method = RequestMethod.GET)
 *					冒号分割 ‘:\\d+’ 含义是只能输入数字类型
 *	@JsonView 控制json输出内容
 *		例如：
 *		  以用户名和密码为例，有的时候不需要显示所有的信息。但是都是返回User对象，这时就需要对其做限制
 *		使用步骤：
 *			使用接口来声明多个视图		看User类
 *			在值对象的get方法上指定视图	看User类的get()
 *			在Controller方法上指定视图	url映射上面
 *		详情看UserControllerTest.class 测试类的两个测试方法
 *
 *	@RequestBody 映射请求体到java方法的参数(放到参数里面)
 *		如：前台已json格数传递参数给(User user),这是就要在User前面加上它
 *	日期类型参数的处理
 *		最好的就是存储为时间戳，等到了具体的显示场景下再转换成需要的格式(前后台传输最好是时间戳)
 *	@Valib 注解 和 BindingResult 验证请求参数的合法性并处理校验结果
 *		(写API一定要校验，常用的校验注解org.hibernate.validator下)
 *		在实体类的属性处加注解去限制,并且结合@Valib注解
 *		如：@NotBlank 不能为空；看下面的create()方法
 *		光光加了限制注解和@Valib注解，是不会进入到方法里面去处理的，服务发现错误直接报错
 *			有的时候是需要进入方法里面去记录一些信息(比如记录日志谁没有填密码。。)，这是就要结合使用BindingResult类
 *	@ResponseBody 将处理好的封装成json格式(返回的格式要为json，必加它)
 *
 * 补充：
 *	@Conditional 是Spring4新提供的注解，它的作用是按照一定的条件进行判断，满足条件给容器注册bean。
 *	@ConditionalOnBean 	(仅仅在当前上下文中存在某个对象时，才会实例化一个Bean)
 *	@ConditionalOnClass (某个class位于类路径上，才会实例化一个Bean/
 *						该注解的参数对应的类必须存在，否则不解析该注解修饰的配置类)
 *	@ConditionalOnExpression	(当表达式为true的时候，才会实例化一个Bean)
 *	@ConditionalOnMissingBean	(仅仅在当前上下文中不存在某个对象时，才会实例化一个Bean)
 *		如@ConditionOnMissingBean(name = "example")，这个表示如果name为“example”的
 *			bean存在，这该注解修饰的代码块不执行。
 *	@ConditionalOnMissingClass	(某个class类路径上不存在的时候，才会实例化一个Bean)
 *	@ConditionalOnNotWebApplication	(不是web应用)
 *	
 *	@ConfigurationProperties 主要用来把properties配置文件转化为bean来使用的
 *	@Configuration 用于定义配置类
 *  @EnableConfigurationProperties 作用是@ConfigurationProperties注解生效。
 *		说明：如果只配置@ConfigurationProperties注解，在IOC容器中是获取不到properties配置文件转化的bean的
 * 		如：
 * 			// prefix定义配置文件中属性
 * 			@ConfigurationProperties(prefix = "imooc.security")
 *			public class MySecurityProperties {
 *				// 属性必须保持与application.properties中的属性名称一致
 *			}
 *			
 *			@Configuration
 *	 		@EnableConfigurationProperties(MySecurityProperties.class)
 *			public class SecurityCoreConfig {
 *				// 空，这个类的主要作用是使其MySecurityProperties配置类生效
 *			}
 *
 *	常用的验证注解(hibernate.validator里面)：都有一个属性message(自定义错误消息)
 *		@NotNull			值不能为空
 *		@Null				值必须为空
 *		@Pattern(regex=)	字符串必须匹配正则表达式
 *		@Size(min=,max=)	集合的元素数量必须在min和max之间
 *		@CreditCardNumber(ignoreNonDigitCharacters=)	字符串必须是信用卡号(按美国的标准校验的)
 *		@Email				字符串必须是Email地址
 *		@Length(min=,max=)	检查字符串的长度
 *		@NotBlank			字符串必须有字符
 *		@NotEmpty			字符串不为null，集合有元素
 *		@Range(min=,max=)	数字必须大于等于min，小于等于max
 *		@SafeHtml			字符串是安全的html
 *		@URL				字符串是合法的URL
 *		@AssertFalse		(布尔类型)值必须是false
 *		@AssertTrue			(布尔类型)值必须是true
 *		@DecimalMax(value=,inclusive=)	值必须小于等于(inclusive=true)/小于(inclusive=false) value属性指定的值
 *										可以注解在字符串类型的属性上
 *		@DecimalMin(value=,inclusive=)	值必须大于等于(inclusive=true)/大于(inclusive=false) value属性指定的值
 *										可以注解在字符串类型的属性上
 *		@Digits(integer=,fraction=)		数字格式检查，integer指定整数部分的最大长度，fraction指定小数部分的最大长度
 *		@Future				值必须是未来的日期
 *		@Past				值必须是过去的日期
 *		@Max(value=)		值必须小于等于value指定的值。不能注解在字符串类型的属性上
 *		@Min(value=)		值必须大于等于value指定的值，不能注解在字符串类型的属性上
 *	自定义消息(BindingResult报的错误)
 *		验证注解的属性，如@NotBlank(message = "密码不能为空")
 *	自定义校验注解(注：为了体现效果，可以去改改自定义的校验逻辑返回的boolean值)
 *		第一步、右键新建——Annotation
 *			三个属性必须写
 *				String message() default "{org.hibernate.validator.constraints.NotBlank.message}";
 *				Class<?>[] groups() default { };
 *				Class<? extends Payload>[] payload() default { };
 *		第二步、在新建的Annotation类上添加三个注解
 *			@Target({ElementType.METHOD,ElementType.FIELD})		//指明你的注解能标注在哪里
 *			@Retention(RetentionPolicy.RUNTIME)					//运行时注解
 *			@Constraint(validatedBy = MyConstraintValidator.class)		//表明它的校验逻辑由谁来执行(第三步建的)
 *		第三步、完成校验逻辑类
 *			新建校验逻辑类去实现ConstraintValidator<A,T>
 *				A：你要验证的注解是什么(第一步新建的)
 *				T：验证的类型(就是说你需要验证在String类型上面还是其他类型Object)
 *	
 *	RESTful API的拦截(http://localhost:8080/user/1  getInfo()为例)
 *		过滤器(Filter):servlet提供的;只能拿到Http的请求和相应，它无法知道是什么控制器的什么方法来处理的。
 *		拦截器(Interceptor):spring提供的;不光只拦截自己写的控制器里面的请求，还会拦截spring提供的。
 *				它能拿到Http的请求和相应、以及处理的控制器的方法信息，但是它无法拿到这个方法真正的那个参数的值
 *		切片(Aspect)/AOP:可以拿到控制器的方法传入的值，但是无法拿到Http的请求和相应
 *			补充：Spring AOP 切片类
 *				一、切入点(注解)
 *					1、在哪些方法上起作用
 *					2、在什么时候起作用
 *				二、增强(方法)
 *					1、起作用时执行的业务逻辑
 *		顺序：(服务正常的情况是↓，发生异常时是↑)
 *			Filter(过滤器)最先起作用
 *			Interceptor(拦截器)
 *			ControllerAdvice(控制器的错误处理器) 这个不一定有
 *			Aspect(切片)	 
 *			Controller(控制器的方法)
 *			说明：
 *				(发生异常时，最先捕获到异常的是切片，这时切片要是继续往外抛异常，会到[如果你声明了ControllerAdvice]...
 *
 *	文件的上传与下载(利用测试用例来实现)
 *		文件上传
 *		文件下载
 *		多理解：FileController/FileControllerTest
 *
 *	异步处理REST服务
 *		使用Runnable异步处理Rest服务
 *			Callable<String>
 *		使用DeferredReult异步处理Rest服务
 *		异步处理配置：看WebConfig
 *		多理解：AsyncController
 *	与前端开发并行工作(现在大多数都是采用前后端分离)
 *		使用swagger自动生成html文档
 *			第一步：先导包(搜索springfox)springfox-swagger2 / springfox-swagger-ui
 *			第二步：添加注解支持 @EnableSwagger2	
 *			第三步：访问：http://localhost:8080/swagger-ui.html
 *			注：405错误，解决：检查Controller是否都加入了@RequestMapping注解
 *			默认是不包含说明的信息(HTML)。
 *				但是可以利用注解来备注信息。(在控制层的方法上写)
 *				//	方法的描述
 *					@ApiOperation(value = "用户查询服务") 
 *				// 参数的描述(1、对象类型；2、普通类型)
 *				 	1、对象：在对象的属性上面使用：@ApiModelProperty(value = "用户名")
 *					2、普通：在方法参数括号里面加：@ApiParam("用户id")
 *			详情看：swagger文档
 *		使用WireMock快速伪造RESTful服务(为了前端人士方便，伪造假数据)
 *			WireMock(独立的服务器，不用重启，不用反复部署)——写一些代码告诉它，收到什么请求将返回什么数据 
 *			步骤：
 *				第一步：下载独立的JAR(http://wiremock.org/docs/running-standalone/)
 *				第二步：cmd到下载路径下运行(先启动)($ java -jar wiremock-standalone-2.18.0.jar --port 8082)
 *				第三步：编写代码(添加依赖pom.xml github下的wiremock) 
 *					  添加一个main方法，在其中编辑代码，然后右键运行；MockService.java
 *				再去浏览器运行(此处的端口要使用WireMock的端口)：localhost:8082/order/1;查看是否是返回body里面的json
 */			

@RestController
@RequestMapping("/user")
public class UserController {
	
	
	/**
	 * @Title:getCurrentUser
	 * @Description:TODO 得到当前用户信息
	 * @return:Object
	 * @author:Jiangxb
	 * @date: 2018年9月15日 下午12:10:06
	 */
	@GetMapping("/me")
	public Object getCurrentUser() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	// 较上面写法简洁点
	@GetMapping("/me1")
	public Object getCurrentUser1(Authentication authentication) {
		return authentication;
	}
	
	// 不想返回那么多信息，只想要UserDetails信息
	@GetMapping("/me2")
	public Object getCurrentUser2(@AuthenticationPrincipal UserDetails user) {
		return user;
	}
	
	/**
	 * 用户查询服务
	 *  这时参数一个一个去传太麻烦了，而且当个数多的时候，不好控制
	 * 	SpringMVC 可以把这些参数组装到对象里面去的【以对象为参数】
	 * 		新建dto，UserQueryCondition
	 * @param condition
	 * @param pageable
	 * @return
	 */
	@GetMapping
	@JsonView(User.UserSimpleView.class)	//在所有用户信息查询的时候，不展示用户密码
	@ApiOperation(value = "用户查询服务")
	public List<User> query(UserQueryCondition condition,
			@PageableDefault(page = 2, size = 17, sort = "username,asc") Pageable pageable) {

		/**
		 * 用反射的ToString的一个工具来打印对象
		 * 	ReflectionToStringBuilder
		 */
		System.out.println(ReflectionToStringBuilder.toString(condition, ToStringStyle.MULTI_LINE_STYLE));

		System.out.println(pageable.getPageSize());
		System.out.println(pageable.getPageNumber());
		System.out.println(pageable.getSort());

		List<User> users = new ArrayList<>();
		users.add(new User());
		users.add(new User());
		users.add(new User());
		return users;
	}
	
	/**
	 * 根据id查询用户详情
	 * @param id	参数可以做正则表达式限制
	 * @return
	 */
	@GetMapping("/{id:\\d+}")
	@JsonView(User.UserDetailView.class)	//在单用户信息查询的时候，要展示用户密码
	@ApiOperation(value = "得到用户信息服务")
	public User getInfo(@ApiParam("用户id") @PathVariable String id) {
		System.out.println("进入getInfo服务");
		User user = new User();
		user.setUsername("tom");
		return user;
		
		//体验SpringBoot 默认的异常处理
//		throw new RuntimeException("user not exist");
		
		/**
		 * 自定义异常 
		 * 	此时抛出的异常还是，没有包含id的信息，springBoot默认情况下只去读错误信息，而不会读其他信息
		 * 	There was an unexpected error (type=Internal Server Error, status=500).
		 *	Request processing failed; nested exception is com.imooc.exception.UserNotExistException: User not Exist
		 *  需要在报错的信息中包含id信息：
		 *  	1、新建一个类：ControllerExceptionHandler
		 *  	2、为其添加一个注解：@ControllerAdvice
		 *  	3、添加一个方法来处理这个异常handleUserNotExistException(UserNotExistException ex)
		 *  	报错信息：{"id":"1","message":"User not Exist"}
		 */
//		throw new UserNotExistException(id);
	}
	
	/**
	 * 用户创建请求
	 * @param user
	 * @return
	 */
	/*@PostMapping
	public User create(@Valid @RequestBody User user) {
		//不加@RequestBody时，user都为空
		System.out.println("前台传入的参数：" + user);
		//这是传统方式的校验
		if(StringUtils.isBlank(user.getPassword())) {
			System.out.println("校验是否传入密码，没有");
		}
		*//**
		 * 实体类的Password属性添加了不能为空的@NotBlank注解，同时方法参数也添加了
		 * @Valid注解。服务一到入参处就发生错误，将不再进入方法体，也无法打印操作日志
		 * 具体看下面的方法，即对其进行了校验，也对其进行了处理(带着错误进入方法体)
		 *//*
		user.setId("1");
		return user;
	}*/
	
	@PostMapping
	@ApiOperation(value = "创建用户")
	public User create(@Valid @RequestBody User user,BindingResult errors) {
		//经过@Valid校验后是否存在错误
		if( errors.hasErrors() ) {
			errors.getAllErrors().stream()
			.forEach(error -> System.out.println(error.getDefaultMessage()));
			/**
			 * 控制台打印：may not be empty(应该不能为空)
			 */
		}
		//不加@RequestBody时，user都为空
		System.out.println("前台传入的参数：" + user);
		user.setId("1");
		return user;
	}
	
	
	@PutMapping("/{id:\\d+}")
	@ApiOperation(value = "更新用户服务")
	public User update(@Valid @RequestBody User user, BindingResult errors) {
		//经过@Valid校验后是否存在错误
		if( errors.hasErrors() ) {
			errors.getAllErrors().stream()
			.forEach(error -> {
				//将error强制转换为FieldError，这样就能拿到字段的名字
				//如：birthday 字段: must be in the past	(现在报错信息都是英文的，但是可以自定义)
				FieldError fieldError = (FieldError) error;
				String message = fieldError.getField() +" 字段: "+ error.getDefaultMessage();
				System.out.println(message);	//包含字段
				System.out.println(error.getDefaultMessage());	//不包含字段
				
			}
			);
			//return null;
		}else {	
			//不存在错误时做的逻辑
		}
		//不加@RequestBody时，user都为空
		System.out.println("前台传入的参数：" + user);
		user.setId("1");
		return user;
	}
	
	@DeleteMapping("/{id:\\d+}")
	@ApiOperation(value = "删除用户服务")
	public void delete(@PathVariable String id) {
		System.out.println("成功删除id为：" + id + " 用户信息");
	}
	
}
