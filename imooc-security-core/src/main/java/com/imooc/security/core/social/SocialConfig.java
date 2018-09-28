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

import com.imooc.security.core.properties.MySecurityProperties;

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
	 * @Fields:mySecurityProperties : TODO 注入系统配置类
	 */
	@Autowired
	private MySecurityProperties mySecurityProperties;
	
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
		
//		return new SpringSocialConfigurer();
		/**
		 * 为了解决redirect_uri，保持一致的问题，修改原来利用自带的SpringSocialConfigurer
		 * 	说明：因为我们默认在登录界面上面写的是：<a href="/auth/qq">QQ登录</a>；而SocialAuthenticationFilter的DEFAULT_FILTER_PROCESSES_URL = "/auth";
		 * 		再者：QQAutoConfig里面读取的：mySecurityProperties.getSocial().getQq().getProviderId() 默认配置的就是“qq”。
		 * 但是根据在QQ互联上申请的网站回调域：http://www.pinzhi365.com/qqLogin/callback.do，需要做成适配，就需要去重写SpringSocialConfigurer的postProcess这个方法
		 * 改成利用自定义的ImoocSpringSocialConfigurer(filterProcessesUrl)
		 *  	而filterProcessesUrl是可配置的，在SocialProperties配置类中去设置配置项，然后在配置文件中去新增配置项：
		 *  			imooc.security.social.filterProcessesUrl = /qqLogin
		 */
		String filterProcessesUrl = mySecurityProperties.getSocial().getFilterProcessesUrl();
		ImoocSpringSocialConfigurer configurer = new ImoocSpringSocialConfigurer(filterProcessesUrl);
		return configurer;
	}
}
