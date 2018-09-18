/**
 * 
 */
package com.imooc.security.core.validate.code;

import java.time.LocalDateTime;

/**
 * @Package:com.imooc.security.core.validate.code
 * @ClassName:ValidateCode
 * @Description:TODO 验证码 实体 == 手机短信验证码实体
 * @author:Jiangxb
 * @date:2018年9月15日 下午5:09:53
 * 
 */
public class ValidateCode {
	
	/**
	 * 随机数
	 * @Fields:code : TODO
	 */
	private String code;
	
	/**
	 * 过期时间(验证码的过期时间)
	 * @Fields:expireTime : TODO
	 */
	private LocalDateTime expireTime;
	
	
	/**
	 * 验证码一般是设置 多少秒过期，而不是直接设置过期时间段
	 * @Title:ImageCode
	 * @Description:TODO
	 * @param image
	 * @param code
	 * @param expireIn	传入过期的秒数
	 * 但是在验证的时候，是根据传入的秒数，然后转换成时间去比对的
	 * 	因此，传入过期的时间秒数，保存却要保存时间点数
	 * 使用当前时间+秒数：LocalDateTime.now().plusSeconds(expireIn);
	 */
	public ValidateCode(String code, int expireIn) {
		this.code = code;
		this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
	}

	public ValidateCode(String code, LocalDateTime expireTime) {
		this.code = code;
		this.expireTime = expireTime;
	}
	
	/**
	 * 验证 时间是否过期
	 * @Title:isExpried
	 * @Description:TODO
	 * @return boolean
	 * @author Jiangxb
	 * @date 2018年9月14日上午10:49:42
	 */
	public boolean isExpried() {
		// 当前时间 是否在 过期时间 之后
		return LocalDateTime.now().isAfter(expireTime);
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public LocalDateTime getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(LocalDateTime expireTime) {
		this.expireTime = expireTime;
	}
	
	
}
