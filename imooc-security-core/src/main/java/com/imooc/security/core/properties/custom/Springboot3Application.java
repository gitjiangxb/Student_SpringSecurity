package com.imooc.security.core.properties.custom;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ComponentProperties.class)
public class Springboot3Application {
	// 在AbstractValidateCodeProcessor.create()方法中测试打印信息
}
