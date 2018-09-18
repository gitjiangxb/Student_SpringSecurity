package com.imooc.security.core.validate.code.image;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.imooc.security.core.validate.code.impl.AbstractValidateCodeProcessor;

/**
 * @Package:com.imooc.security.core.validate.code.image
 * @ClassName:ImageCodeProcessor
 * @Description:TODO 图片验证码处理器
 * @author:Jiangxb
 * @date:2018年9月15日 下午8:55:57
 * imageValidateCodeProcessor
 */
@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

	/* (non-Javadoc)
	 * 发送图形验证码，将其写到响应中
	 * @see com.imooc.security.core.validate.code.impl.AbstractValidateCodeProcessor#send(org.springframework.web.context.request.ServletWebRequest, com.imooc.security.core.validate.code.ValidateCode)
	 */
	@Override
	protected void send(ServletWebRequest request, ImageCode validateCode) throws Exception {
		/**
		 *  3、将生成的图片写到接口的响应中
		 *  write(im, formatName, output)
		 *  第一个参数：写出去的图片
		 *  第二个参数：写出去的格式
		 *  第三个参数：写道响应的输出流里面
		 */
		ImageIO.write(validateCode.getImage(), "JPEG", request.getResponse().getOutputStream());
	}

}
