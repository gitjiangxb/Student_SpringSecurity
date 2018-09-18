package com.imooc.security.core.properties.custom;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Package:com.imooc.security.core.properties.custom
 * @ClassName:ComponentProperties
 * @Description:TODO 测试用：指定读取的配置文件
 * @author:Jiangxb
 * @date:2018年9月18日 上午11:52:02
 * 
 */
@Configuration 
@ConfigurationProperties(prefix = "local",ignoreUnknownFields=false)
@PropertySource(value = "classpath:custom.properties",ignoreResourceNotFound = true)
public class ComponentProperties {
	private String host;
    private String port;
	
    public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	
	@Override
	public String toString() {
		return "ComponentProperties [host=" + host + ", port=" + port + "]";
	}
    
    
}
