/**
 * 
 */
package com.imooc.security.core.validate.code.image;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

import com.imooc.security.core.validate.code.ValidateCode;

/**
 * 图片验证码 实体
 * @Package: com.imooc.security.core.validate.code
 * @author: Jiangxb
 * @date: 2018年9月14日上午9:18:57
 * 继承验证码实体：因为有共同属性的存在
 */
public class ImageCode extends ValidateCode {
	
	/**
	 * 图片
	 * @Fields:image : TODO
	 */
	private BufferedImage image;
	
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
	public ImageCode(BufferedImage image, String code, int expireIn) {
		super(code, expireIn);
		this.image = image;
	}

	public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
		super(code, expireTime);
		this.image = image;
	}
	
	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	
}
