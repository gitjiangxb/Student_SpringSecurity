package com.imooc.security.core.social;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @Package:com.imooc.security.core.social
 * @ClassName:SocialConfig
 * @Description:TODO Social的配置类
 * @author:Jiangxb
 * @date:2018年9月26日 下午3:51:29
 * extends SocialConfigurerAdapter
 * 	Social配置的适配器
 */
@Configuration
@EnableSocial	// 启动Spring Social社交项目的一些特性
public class SocialConfig extends SocialConfigurerAdapter {
	
	/**
	 * @Fields:dataSource : TODO 注入数据源
	 */
	@Autowired
	private DataSource dataSource;
	
	/**
	 * 操作数据库的UserConnection这张表
	 */
	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		/**
		 * JdbcUsersConnectionRepository
		 * dataSource:数据源
		 * connectionFactoryLocator:会传入，这个类的作用就是去 查找ConnectionFactory
		 * textEncryptor:帮你把插入数据库的数据进行 加解密的工具;
		 * 			目前这里不做任何操作，原样插入进去(Encryptors.noOpText())
		 */
		return new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
		
		/**
		 * 若表加了前缀。需要配置
		 */
//		JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
//		repository.setTablePrefix("imooc_");	// 加了前缀的配置
//		return repository;
	}
	
	/**
	 * @Title:imoocSocialSecurityConfig
	 * @Description:TODO SpringSocial添加到过滤器链的配置
	 * @return:SpringSocialConfigurer
	 * @author:Jiangxb
	 * @date: 2018年9月27日 下午4:54:56
	 */
	@Bean
	public SpringSocialConfigurer imoocSocialSecurityConfig() {
		return new SpringSocialConfigurer();
	}
}
