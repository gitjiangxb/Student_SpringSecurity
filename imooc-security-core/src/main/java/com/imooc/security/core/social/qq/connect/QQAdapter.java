package com.imooc.security.core.social.qq.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

import com.imooc.security.core.social.qq.api.QQ;
import com.imooc.security.core.social.qq.api.QQUserInfo;

/**
 * @Package:com.imooc.security.core.social.qq.connect
 * @ClassName:QQAdapter
 * @Description:TODO QQ适配器：负责把不同服务商提供的不同结构的数据(用户信息)转换成Connection标准的数据结构
 * @author:Jiangxb
 * @date:2018年9月26日 下午3:14:17
 * implements ApiAdapter<A>
 * 	泛型指的是 当前适配器，适配的Api类型 (QQ)
 */
public class QQAdapter implements ApiAdapter<QQ> {
	/**
	 * 通过api 拿到标准结构的用户信息
	 */
	@Override
	public UserProfile fetchUserProfile(QQ api) {
		
//		return UserProfile.EMPTY;
		return null;
	}

	/**
	 * Connection数据 和 api数据 做适配
	 * 需要完成：
	 * 	利用api得到的值，赋值给 values里面对应的值
	 */
	@Override
	public void setConnectionValues(QQ api, ConnectionValues values) {
		// 通过api获取QQ用户信息
		QQUserInfo userInfo = api.getUserInfo();
		
		// 显示的名字
		values.setDisplayName(userInfo.getNickname());
		// 用户的头像
		values.setImageUrl(userInfo.getFigureurl_qq_1());
		// 个人主页(QQ没有主页；微博存在主页，这里放主页的url)
		values.setProfileUrl(null);
		// 服务商id(openId)
		values.setProviderUserId(userInfo.getOpenId());
		
	}

	/**
	 * 测试当前api是否可用，也就是说QQ的服务是否还通着
	 */
	@Override
	public boolean test(QQ api) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * 跟主页类似，QQ不存在
	 */
	@Override
	public void updateStatus(QQ api, String messgae) {
		// QQ不存在，所以什么都不做
	}

}
