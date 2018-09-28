package com.imooc.security.core.social.qq.connect;

import java.nio.charset.Charset;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @Package:com.imooc.security.core.social.qq.connect
 * @ClassName:QQOAuth2Template
 * @Description:TODO 自定义实现OAuth2Template
 * @author:Jiangxb
 * @date:2018年9月28日 下午5:13:13
 * 	重写createRestTemplate()为了解决不能处理(text/html)这种content type的响应
 *  重写postForAccessGrant()为了解决获取Access_Token返回的特殊类型的处理
 */
public class QQOAuth2Template extends OAuth2Template {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
		super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
		// 将其设置为True，这样构造参数的时候才会带上client_id和client_secret
		setUseParametersForClientAuthentication(true);
	}
	
	@Override
	protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
		// 发送post请求，接收的响应数据类型=String.class
		String responseStr = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
		logger.info("获取Access_Token的响应：" + responseStr);
		// 以”&“切割成数组
		String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseStr, "&");
		String access_token = StringUtils.substringAfterLast(items[0], "=");			// 授权令牌，Access_Token。
		long expires_in = new Long(StringUtils.substringAfterLast(items[1], "="));		// 该access token的有效期，单位为秒。
		String refresh_token = StringUtils.substringAfterLast(items[2], "=");			// 在授权自动续期步骤中，获取新的Access_Token时需要提供的参数
		// 将请求的格式按照QQ的标准做了自定义的解析
		return new AccessGrant(access_token, null, refresh_token, expires_in);
	}

	@Override
	protected RestTemplate createRestTemplate() {
		// 先拿到父类的结果
		RestTemplate restTemplate = super.createRestTemplate();
		// 再为其添加一个能处理(text/html)
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		return restTemplate;
	}
}
