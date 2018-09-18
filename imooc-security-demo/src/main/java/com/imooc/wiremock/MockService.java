package com.imooc.wiremock;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;

import com.github.tomakehurst.wiremock.client.WireMock;

/**
 * 使用WireMock快速伪造RESTful服务
 * @author Jiangxb
 *
 */
public class MockService {

	public static void main(String[] args) throws IOException {
		/*
		 * 这里要调用很多静态方法，可以将其添加到Favorites里面。就会静态导包
		 * import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
		 */
		WireMock.configureFor(8082);	// 通过端口连接服务(因为在本机不指定ip)
		WireMock.removeAllMappings();	// 清空之前的配置
		mock("/order/1","01");		// 第一个服务
		mock("/order/2","02");		// 第二个服务(新增非常简单，只需要重新运行这个main)
//		...
	}
	
	/**
	 * 封装：告诉WireMock如何处理
	 * @param url	处理的请求地址
	 * @param fileName	处理返回的结果(写在文件里面)
	 * @throws IOException
	 */
	private static void mock(String url,String fileName) throws IOException {
		// spring 加载资源文件 
		ClassPathResource resource = new ClassPathResource("mock/resources/" + fileName + ".txt");
		// 将文件读出来
		String	content = StringUtils.join(FileUtils.readLines(resource.getFile(), "UTF-8").toArray(),"\n");
		// 正式告诉它如何处理服务
		WireMock.stubFor(
				// get请求
				WireMock.get(WireMock.urlPathEqualTo(url))
				// 返回的结果(body内容:JSON,状态码200)
				.willReturn(WireMock.aResponse().withBody(content).withStatus(200))
				);
	}
}


/*
 详细的讲解
  
  		// spring 加载资源文件 
		ClassPathResource resource = new ClassPathResource("mock/resources/01.txt");
		 * 将文件读出来
		 * FileUtils.readLines(resource.getFile(), "UTF-8").toArray() 读出文件，返回的是List<String>
		 * 利用StringUtils.join("".toArray(),"处理一下回车\n"),将其拼接为一个字符串
		String	content = StringUtils.join(FileUtils.readLines(resource.getFile(), "UTF-8").toArray(),"\n");
		// 正式告诉它如何处理服务
		WireMock.stubFor(
				// get请求
				WireMock.get(
						// url地址
						WireMock.urlPathEqualTo("/order/1")
						)
				// 返回
				.willReturn(WireMock.aResponse()
						// body里面写 JSON(json在这里写比较麻烦，在src/main/resources/mock/resources/01.txt)
						// .withBody("{\"id\":1}")
						.withBody(content)
						// 返回状态码
						.withStatus(200)
						)
				);
  
 */
