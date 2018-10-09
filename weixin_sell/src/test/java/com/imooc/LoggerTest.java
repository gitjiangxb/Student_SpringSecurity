package com.imooc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Package:com.imooc
 * @ClassName:LoggerTest
 * @Description:TODO 日志的测试类
 * @author:Jiangxb
 * @date:2018年10月8日 下午7:03:24
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoggerTest {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Test
	public void test1() {
		String name = "imooc";
        String password = "123456";
		logger.info("info...");
		logger.info("name: " + name + " ,password: " + password);
		// {} 表示占位符的意思
		logger.info("name: {} , password: {}", name, password);
		logger.error("错误error...");
		logger.warn("warn...");
	}
}
