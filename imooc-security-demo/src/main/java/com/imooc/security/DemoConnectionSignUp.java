package com.imooc.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * @Package:com.imooc.security
 * @ClassName:DemoConnectionSignUp
 * @Description:TODO 实现默认的注册
 * @author:Jiangxb
 * @date:2018年9月29日 下午3:13:58
 * 当社交软件第一次登录时，直接跳转到登录成功页面，不需要经过“Demo 注册页”
 * 	重写ConnectionSignUp接口的execute()方法即可
 */
@Component
public class DemoConnectionSignUp implements ConnectionSignUp {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public String execute(Connection<?> connection) {
		// TODO 根据社交用户信息(connection)默认创建用户并返回用户的唯一标识;这里拿昵称为用户的唯一标识，真实的逻辑根据任务来做
		logger.info("默认创建用户并返回用户的唯一标识:" + connection.getDisplayName());
		return connection.getDisplayName();
	}

}
