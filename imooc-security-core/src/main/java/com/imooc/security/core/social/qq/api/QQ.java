package com.imooc.security.core.social.qq.api;

/**
 * @Package:com.imooc.security.core.social.qq.api
 * @ClassName:QQ
 * @Description:TODO QQ登录的接口
 * @author:Jiangxb
 * @date:2018年9月23日 下午4:09:03
 * 	负责执行流程里面的第六步
 */
public interface QQ {
	
	/**
	 * @Title:getUserInfo
	 * @Description:TODO 获取QQ里的用户信息
	 * @return:QQUserInfo
	 * @author:Jiangxb
	 * @throws Exception 
	 * @date: 2018年9月23日 下午4:10:59
	 */
	QQUserInfo getUserInfo() throws Exception;
}
