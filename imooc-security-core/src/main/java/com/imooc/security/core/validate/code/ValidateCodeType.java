package com.imooc.security.core.validate.code;

import com.imooc.security.core.properties.SecurityConstants;

/**
 * @Package:com.imooc.security.core.validate.code
 * @ClassName:ValidateCodeType
 * @Description:TODO 验证码类型
 * @author:Jiangxb
 * @date:2018年9月16日 下午11:33:25
 * 
 */
public enum  ValidateCodeType {
	/**
	 * 短信验证码
	 */
	SMS {
		@Override
		public String getParamNameOnValidate() {
			return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
		}
	},
	/**
	 * 图片验证码
	 */
	IMAGE {
		@Override
		public String getParamNameOnValidate() {
			return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
		}
	};
 
	/**
	 * @Title:getParamNameOnValidate
	 * @Description:TODO 校验时从请求中获取的参数的名字
	 * @return:String
	 * @author:Jiangxb
	 * @date: 2018年9月17日 上午10:55:31
	 */
	public abstract String getParamNameOnValidate();
}
