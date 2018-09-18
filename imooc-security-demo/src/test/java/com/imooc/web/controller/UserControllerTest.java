package com.imooc.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * 测试用例：测试RESTful风格的API
 * @author Jiangxb
 *
 */
@RunWith(SpringRunner.class)	//表示如何运行测试用例
@SpringBootTest
public class UserControllerTest {
	/**
	 * 使用MockMvc伪造mvc环境
	 */
	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;
	
	@Before	//这个注解表示，每次测试用例之前去执行
	public void Setup() {
		//构建一个伪造的mvc环境
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	//查询全部用户信息的测试用例
	@Test
	public void whenQuerySuccess() throws Exception {
//		/**
//		 * mockMvc.perform(方法执行请求的方法
//		 * MockMvcRequestBuilders.get(模拟发出get请求)
//		 * .param(请求的参数，可以单个传，也可以传递类对象)
//		 * .contentType(设置发生json_utf-8的请求))
//		 */
//		mockMvc.perform(MockMvcRequestBuilders.get("/user")
//				.param("username", "jojo")
//				.contentType(MediaType.APPLICATION_JSON_UTF8))
//				/**
//				 * 当perform执行后，我们可以去写一些期望andExpect(写在这个方法参数里面)
//				 * .andExpect(MockMvcResultMatchers.status().isOk())
//				 * 		—— 期望返回的结果的状态码是 isOK，200
//				 * .andExpect(MockMvcResultMatchers.jsonPath("$.length").value(3))
//				 * 		——期望返回的是集合，并且集合长度为3
//				 * 		jsonPath：来解析返回json里面的内容，然后针对json内容去做判断
//				 *			讲解：https://github.com/json-path/JsonPath
//				 */
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("$.length").value(3));
//	
//		/**
//		 * 说明：上面出现的静态的方法status()、get()等
//		 * 	再写其他的测试用例也会出现。因此有一种简便的写法：
//		 * 		可以将MockMvcRequestBuilders和MockMvcResultMatchers类的
//		 * 		声明加到【SpringToolSuite的偏好设置里面】
//		 * 			window——>preperences——>Favorites
//		 * 			——>New Type
//		 * 	然后就可以直接使用了，如下：
//		 *		最上面就会出现静态导入包：import static 。。。.get的方法
//		 */
		mockMvc.perform(get("/user")
				//以对象的形式传递参数
				.param("username", "jojo")
				.param("age", "18")
				.param("ageTo", "60")
				.param("xxx", "yyy")
				
				/*分页信息
				 *	显示第三页，每页15条数据，按照age desc排序 
				 */
//				.param("size", "15")
//				.param("page", "3")
//				.param("sort", "age,desc")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(3));
	}
	
	@Test
	public void whenQuerySuccess1() throws Exception {
		String result = mockMvc.perform(get("/user")
				//以对象的形式传递参数
				.param("username", "jojo")
				.param("age", "18")
				.param("ageTo", "60")
				.param("xxx", "yyy")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(3))
				//把服务器返回的json字符串已string类型的变量返回回去
				.andReturn().getResponse().getContentAsString();
		System.out.println("@JsonView简单视图，不需要返回密码："+ result);
		//[{"username":null},{"username":null},{"username":null}]
	}
	
	@Test
	public void whenGetInfoSuccess() throws Exception {
		//查询用户id为1 的详细用户信息。期望返回状态为200，并且返回的json包含username，值为tom
		String result = mockMvc.perform(get("/user/1")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.username").value("tom"))
				.andReturn().getResponse().getContentAsString();
		System.out.println("@JsonView详细视图，需要返回密码："+ result);
		//{"username":"tom","password":null}
	}
	
	@Test
	public void whenGetInfoFail() throws Exception {
		mockMvc.perform(get("/user/a")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is4xxClientError());
	}
	
	
	@Test
	public void whenCreateSuccess() throws Exception {
		Date  date = new Date();
		System.out.println("前台传入后台的时间戳："+date.getTime());
		//请求参数为JSON格式
//		String content = "{\"username\":\"tom\",\"password\":\"123\",\"birthday\":"+date.getTime()+"}";
		//校验参数输入不能为空
		String content = "{\"username\":\"tom\",\"password\":null,\"birthday\":"+date.getTime()+"}";
		String result = mockMvc.perform(post("/user")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(content))
				.andExpect(status().isOk())
				//期望访问的值包含id，并且id=1
				.andExpect(jsonPath("$.id").value("1"))
				.andReturn().getResponse().getContentAsString();
		System.out.println("后台处理返回的值：" + result);
	}
	

	@Test
	public void whenUpdateSuccess() throws Exception {
		//jdk1.8 新加的属性
		Date  date = new Date(LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
		System.out.println("前台传入后台的时间戳："+date.getTime());
		String content = "{\"id\":\"1\",\"username\":\"tom\",\"password\":null,\"birthday\":"+date.getTime()+"}";
		//针对id是1的用户进行修改
		String result = mockMvc.perform(put("/user/1")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(content))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value("1"))
				.andReturn().getResponse().getContentAsString();
		System.out.println("后台处理返回的值：" + result);
	}
	
	@Test
	public void whenDeleteSuccess() throws Exception {
		//删除一般只需要返回处理的状态码(RESTful)
		mockMvc.perform(delete("/user/1")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
	}
	
}
