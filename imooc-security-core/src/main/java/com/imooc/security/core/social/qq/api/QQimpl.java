package com.imooc.security.core.social.qq.api;

import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Package:com.imooc.security.core.social.qq.api
 * @ClassName:QQimpl
 * @Description:TODO QQ登录的接口实现类
 * @author:Jiangxb
 * @date:2018年9月23日 下午4:11:56
 * extends AbstractOAuth2ApiBinding
 * 	它包含两个属性
 * 	private final String accessToken;	// 保存前五步执行完后的Token
 *  private RestTemplate restTemplate;	// 第六步是获取用户信息，需要往服务提供商发一个请求，由它来完成
 *  
 *  调用QQ提供的API(https://graph.qq.com/user/get_user_info)时，需要传递三个参数：
 *  	access_token：父类AbstractOAuth2ApiBinding，已经提供了
 *  	oauth_consumer_key：申请QQ登录成功后，分配给应用的appid
 *  	openid：用户的ID，与QQ号码一一对应。 
 *			可通过调用https://graph.qq.com/oauth2.0/me?access_token=YOUR_ACCESS_TOKEN 来获取。
 */
public class QQimpl extends AbstractOAuth2ApiBinding implements QQ {

	/**
	 * @Fields:URL_GET_OPENID : TODO 通过access_token得到openId的URL
	 */
	private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
	
	/**
	 * @Fields:URL_GET_USERINFO : TODO 得到用户信息URL
	 */
	private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";
	
	/**
	 * @Fields:appId : TODO 得到用户信息URL_参数
	 */
	private String appId;
	
	/**
	 * @Fields:openId : TODO 得到用户信息URL_参数
	 */
	private String openId;
	
	
	/**
	 * @Fields:objectMapper : TODO 工具类：将返回的JSON字符串转换为QQUserInfo对象
	 */
	private ObjectMapper objectMapper = new ObjectMapper();
	
	/**
	 * @Title:QQimpl
	 * @param accessToken	完成前五步得到的Token，需要传入
	 * @param appId		系统的配置信息，也需要传入
	 */
	public QQimpl(String accessToken,String appId) {
		/**
		 *  父类构造函数说明：
		 *  protected AbstractOAuth2ApiBinding(String accessToken)
		 *  {
		 *   	// 调用自己默认的两个参数的构造器，同时传入默认的token策略
		 *   	// AUTHORIZATION_HEADER：把accessToken在发请求的时候，默认放到请求头里面；但是根据获取用户信息的API，需要把accessToken作为参数传递，因此需要调用两个参数的构造函数
		 *   	https://graph.qq.com/user/get_user_info?access_token=YOUR_ACCESS_TOKEN&oauth_consumer_key=YOUR_APP_ID&openid=YOUR_OPENID
		 *   
		 *       this(accessToken, TokenStrategy.AUTHORIZATION_HEADER);
		 *   } 
		 *
		 *  protected AbstractOAuth2ApiBinding(String accessToken, TokenStrategy tokenStrategy)
		 *   {
		 *       this.accessToken = accessToken;
		 *       restTemplate = createRestTemplate(accessToken, getOAuth2Version(), tokenStrategy);
		 *      configureRestTemplate(restTemplate);
		 *   }
		 */
		// TokenStrategy.ACCESS_TOKEN_PARAMETER 会将accessToken作为参数传递，名为access_token
		super(accessToken,TokenStrategy.ACCESS_TOKEN_PARAMETER);
		
		this.appId = appId;
		// 这个写法就是把accessToken传入到URL_GET_OPENID中%s的位置
		String url = String.format(URL_GET_OPENID, accessToken);
		/**
		 * 调用父类的方法发送get请求,返回的类型参考：http://wiki.connect.qq.com/%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7openid_oauth2-0
		 * callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} );
		 */
		String result = getRestTemplate().getForObject(url, String.class);
		System.out.println("得到openidAPI的返回结果：" + result);
		// 截取得到openid 
		this.openId = StringUtils.substringBetween(result, "\"openid\":", "}");
	}
	
	@Override
	public QQUserInfo getUserInfo() throws Exception {
		// 发送请求，获取用户信息;因为构造函数的super(..),会将accessToken传递到请求参数中去
		String url = String.format(URL_GET_USERINFO, appId,openId);
		// 发送get请求，去获取用户信息
		String result = getRestTemplate().getForObject(url, String.class);
		System.out.println("得到用户信息的返回结果：" + result);
		// 利用ObjectMapper工具类
		return objectMapper.readValue(result, QQUserInfo.class);
	}

}
