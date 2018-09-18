package com.imooc.security.core;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.imooc.security.core.properties.MySecurityProperties;
/**
 * @Package:com.imooc.security.core
 * @ClassName:SecurityCoreConfig
 * @Description:TODO 配置类：为了让MySecurityProperties这些配置信息生效
 * @author:Jiangxb
 * @date:2018年9月13日上午10:28:22
 * 
 */
@Configuration
@EnableConfigurationProperties(MySecurityProperties.class)
public class SecurityCoreConfig {
	
}
