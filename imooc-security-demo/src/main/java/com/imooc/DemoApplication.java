package com.imooc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * 说明：
 * 	直接启动会出现错误
 * 	1、Cannot determine embedded database driver class for database type NONE
 * 		没有配置数据库的连接信息：application.properties
 * 	2、配完数据库再启动，还是报错
 * 		No Spring Session store is configured: set the 'spring.session.store-type' property
 * 		Spring Session的存储没有被配置
 * 		因为imooc-security-browser的pom里面配置了spring-session用来做集群管理，现在先不用；
 * 		在application.properties配置文件里面将其关闭:spring.session.store-type = none
 *	3、以上改完 访问：localhost:8080/hello时会让你输入登录信息
 *		这是在spring Boot环境下spring security的默认设置
 *			security.basic.enabled	= false
 *		默认配置关闭后再启动 就可以访问了:会在页面打印“hello spring security”
 *	4、补充说明：
 *		restlet client，可用于测试RESTful接口。
 *			Restlet Client插件是一款运行在chrome内核浏览器上的Web服务测试插件
 *	5、SpringBoot 内部默认做错误处理的类：BasicErrorController(都是处理/error的请求)
 *			errorHtml：当请求头的Accept中包含text/html，就会进到这个方法中处理，返回HTML
 *			error：不包含时进入这个方法，返回JSON
 *		自定义错误页面，SpringBoot会根据状态码去调用不同的处理方式(UserController.getInfo())
 *			只需要在：src/main/resources 这个目录下创建 resources/error/404.html 等处理404错误的页面
 *			以上这样写 只对浏览器发出的请求做处理，返回相应的错误状态码页面。
 *			而，Restlet Client插件访问返回的 还是JSON
 *		有时错误信息，不止包含这些，还需要包含其他的信息：
 *			新建一个包：com.imooc.exception ，自定义一个异常：UserNotExistException
 *			还可以自定义异常返回的信息：如ControllerExceptionHandler
 *	【6】、非常重要的信息：
 *		SpringBoot项目的Bean装配默认则是根据Application类所在的包位置从上往下扫描。
 *	Application类 是指SpringBoot项目的入口类。这个类的位置很关键：如果Application类所在
 * 	的包为com.imooc，则只会扫描com.imooc包及其所有的子包
 * 		如：com.imooc.security.core	会扫描，不会抛出异常
 * 		   imooc.security.core	不会扫描到，会抛出异常
 * @author Jiangxb
 *
 */

@SpringBootApplication	// 	SpringBoot项目
@RestController	// 这个类变成了提供Rest服务的类
@EnableSwagger2	// 使用swagger自动生成html文档
public class DemoApplication {
	
	
	public static void main(String[] args) {
		//spring程序 的启动方式
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@GetMapping("/hello")	//http请求 get方式
	@ApiOperation(value = "测试")
	public String hello() {
		return "hello spring security";
	}
}
