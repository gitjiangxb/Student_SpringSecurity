package com.imooc.security.core.social.qq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

import com.imooc.security.core.properties.MySecurityProperties;
import com.imooc.security.core.properties.QQproperties;
import com.imooc.security.core.social.qq.connect.QQConnectionFactory;

/**
 * @Package:com.imooc.security.core.social.qq.config
 * @ClassName:QQAutoConfig
 * @Description:TODO 为自定义的QQConnectionFactory，配置上QQproperties中的三个配置项
 * @author:Jiangxb
 * @date:2018年9月27日 上午10:57:56
 * 
 */
@Configuration
/**
 * 这个注解的含义：控制某个配置文件是否生效
 * 当配置文件里面的：imooc.security.social.qq.app-id = "",app-id配置了值，那么下面的配置信息才生效
 */
@ConditionalOnProperty(prefix = "imooc.security.social.qq",name = "app-id")
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

	/**
	 * @Fields:mySecurityProperties : TODO 注入系统配置类
	 */
	@Autowired
	private MySecurityProperties mySecurityProperties;
	
	@Override
	protected ConnectionFactory<?> createConnectionFactory() {
		// 通过系统配置类实例 获取 QQ配置项
		QQproperties qqConfig = mySecurityProperties.getSocial().getQq();
		return new QQConnectionFactory(qqConfig.getProviderId(), qqConfig.getAppId(), qqConfig.getAppSecret());
	}

}
