package com.imooc.security.core.social.qq.connect;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Template;

import com.imooc.security.core.social.qq.api.QQ;
import com.imooc.security.core.social.qq.api.QQimpl;

/**
 * @Package:com.imooc.security.core.social.qq.connet
 * @ClassName:QQServiceProvider
 * @Description:TODO 创建QQ服务提供商(ServiceProvider)
 * @author:Jiangxb
 * @date:2018年9月26日 下午1:56:23
 * extends AbstractOAuth2ServiceProvider<S>
 * 	这个泛型指Api的类型
 * ServiceProvider需要两部分：
 * 	当它需要OAuth2Operations，我
 * 		new OAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESSTOKEN)
 *  当它需要Api，我
 *  	new QQimpl(accessToken, appId);
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

	private String appId;
	
	/**
	 * @Fields:URL_AUTHORIZE : TODO 对应第一步(将用户导向认证服务器)URL
	 */
	private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";
	
	/**
	 * @Fields:URL_ACCESSTOKEN : TODO 对应第四步(申请立牌)URL
	 */
	private static final String URL_ACCESSTOKEN = "https://graph.qq.com/oauth2.0/token";
	
	/**
	 * 创建ServiceProvider需要的第一个实现：OAuth2Operations
	 * 	这里使用默认的OAuth2Template实现
	 * @Title:QQServiceProvider
	 * @param oauth2Operations
	 */
	public QQServiceProvider(String appId,String appSecret) {
		/**
		 * QQ互联去注册应用的时候，QQ互联会给每个应用分配appId和appSecret；这两个相当于app的用户名和密码，每次发请求的时候需要带这两个值，
		 * 	这样QQ就知道是哪个应用在向它发请求，这两个是配置的，需要通过参数传入进来；
		 * 	因为每个应用的这两个值都不一样，这个需要在每个用我们这个模块的具体的应用去配
		 * clientId：appId
		 * clientSecret：appSecret
		 * authorizeUrl：对应第一步(将用户导向认证服务器)
		 * accessTokenUrl：对应第四步(申请立牌)
		 * 最后两个参数：声明为常量，具体参考：
		 * 	http://wiki.connect.qq.com/%E4%BD%BF%E7%94%A8authorization_code%E8%8E%B7%E5%8F%96access_token
		 */
//		super(new OAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESSTOKEN));
		// 利用自定义的OAuth2Template去创建
		super(new QQOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESSTOKEN));
		this.appId = appId;
	}
	
	/* (non-Javadoc)
	 * 创建ServiceProvider需要的第二个实现：Api
	 * 	返回这个泛型声明的“QQ” 接口的实现，我们的实现是QQimpl
	 * @see org.springframework.social.oauth2.AbstractOAuth2ServiceProvider#getApi(java.lang.String)
	 */
	@Override
	public QQ getApi(String accessToken) {
		/**
		 * accessToken 会自动传入
		 * appId 需要定义一个属性
		 */
		return new QQimpl(accessToken, appId);
	}

}
