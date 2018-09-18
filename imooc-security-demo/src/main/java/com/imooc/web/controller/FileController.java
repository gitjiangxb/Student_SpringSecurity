package com.imooc.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.imooc.dto.FileInfo;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 文件上传 / 下载
 * @author Jiangxb
 *
 */
@RestController
@RequestMapping("/file")
public class FileController {
	
	// 放文件的路径
	private String folder = "C:\\A Soft And Data\\Java\\Data\\SpringToolSuite\\imooc-security-demo\\src\\main\\java\\com\\imooc\\web\\controller";
	
	/**
	 * 上传文件
	 * @param file	必须与测试用例的第一个参数保持一样的名称
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "上传文件")
	@PostMapping
	public FileInfo upload(@RequestParam("file") MultipartFile file) throws Exception {
		// 传入的参数的名称
		System.out.println(file.getName());
		// 原始的文件名(第二个参数)
		System.out.println(file.getOriginalFilename());
		// 传入的文件大小
		System.out.println(file.getSize());
		
		// 创建一个文件，已时间戳.txt 为名称
		File localFile = new File(folder,new Date().getTime()+".txt");
		// 将传入的文件写入本地文件(MultipartFile的transferTo(File localFile)方法直接转存文件到指定的路径)
		file.transferTo(localFile);
		// 要是不上传到本地，可以使用getInputStream()拿到输入流，将文件的内容读出来，然后写到其他的地方(OSS服务/HDFS等)
		//file.getInputStream();
		
		return new FileInfo(localFile.getAbsolutePath());
	}
	
	/**
	 * 文件下载
	 * @param id	文件名称:1536558161065
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "下载文件")
	public void download(@ApiParam("文件名称")@PathVariable String id,HttpServletRequest request,HttpServletResponse response) throws IOException {
		/**
		 * JDK1.7 的新语法，再try()后面的括号里面声明流，在方法执行完后，凡是在try()括号中的声明的流会自动关，
		 * 这样就不需要在finally里面去关闭了
		 * try(流的定义){代码块}——待代码块执行完毕，流会自动关闭；
		 * 		前提:()里面的对象必须实现AutoCloseable接口，执行完成之后自动关闭流
		 */
		try (
				// 输入流，读文件
				InputStream inputStream = new FileInputStream(new File(folder,id+".txt"));
				// 输出流，写文件
				OutputStream outputStream = response.getOutputStream();
				){
			// 修改ContentType为下载
			response.setContentType("application/x-download");
			// 下载下来的文件名
			response.addHeader("Content-Disposition", "attachment;filename=test.txt");
			// 将这个文件的输入流 拷贝 到输出流 里面去
			IOUtils.copy(inputStream, outputStream);
			outputStream.flush();
		} 
	}
}














