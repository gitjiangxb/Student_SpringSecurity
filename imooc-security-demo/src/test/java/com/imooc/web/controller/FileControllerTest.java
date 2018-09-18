package com.imooc.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


/**
 * 测试用例：测试上传/下载
 * @author Jiangxb
 *
 */
@RunWith(SpringRunner.class)	//表示如何运行测试用例
@SpringBootTest
public class FileControllerTest {
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
	
	
	@Test
	public void whenUploadSuccess() throws Exception {
		/**
		 * new MockMultipartFile(name, originalFilename, contentType, content)
		 *  第一个：文件内容放到的参数的名称
		 *  第二个：传入的文件的名称
		 *  第三个：文件类型，必写：如同
		 *  					<form action="/file" method="post" enctype="multipart/form-data">
		 *  						选择文件：<input type="file" name="file">
		 *  							   <input type="submit" value="提交">
		 *  					</form>
		 *  	[说明：]enctype="multipart/form-data"表示该表单是要处理文件的，必须填写
		 *  第四个：文件的内容，bytes数组
		 */
		String result = mockMvc.perform(fileUpload("/file")
				.file(new MockMultipartFile("file", "test.txt", "multipart/form-data", "hello upload".getBytes("UTF-8"))))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		System.out.println("测试用例返回的值:" + result);
	}
}


















