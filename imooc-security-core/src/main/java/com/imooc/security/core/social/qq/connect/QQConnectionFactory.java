package com.imooc.security.core.social.qq.connect;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;

import com.imooc.security.core.social.qq.api.QQ;

/**
 * @Package:com.imooc.security.core.social.qq.connect
 * @ClassName:QQConnectionFactory
 * @Description:TODO QQ连接工厂负责创建Connection实例
 * @author:Jiangxb
 * @date:2018年9月26日 下午3:39:40
 * 	extends OAuth2ConnectionFactory<S>
 * 		这个泛型指：指定你的Api是什么
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

	/**
	 * 在QQAutoConfig配置类中来创建
	 * @Title:QQConnectionFactory
	 * @param providerId	
	 * @param appId	
	 * @param appSecret	
	 */
	public QQConnectionFactory(String providerId,String appId,String appSecret) {
		/**
		 * providerId：服务提供商的唯一标识
		 * serviceProvider：自己写的QQServiceProvider,通过参数传入
		 * 		用于执行授权流和获取本机服务API实例的ServiceProvider模型
		 * apiAdapter：	自己写的QQAdapter
		 * 		适配器，用于将不同服务提供商的个性化用户信息映射到Connection
		 */
		super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
	}

}
