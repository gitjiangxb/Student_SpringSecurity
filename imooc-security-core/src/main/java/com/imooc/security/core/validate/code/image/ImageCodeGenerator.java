package com.imooc.security.core.validate.code.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import com.imooc.security.core.properties.MySecurityProperties;
import com.imooc.security.core.validate.code.ValidateCodeGenerator;

/**
 * @Package:com.imooc.security.core.validate.code
 * @ClassName:ImageCodeGenerator
 * @Description:TODO 图片验证码的生成器
 * @author:Jiangxb
 * @date:2018年9月14日 下午7:23:54
 * 	实现验证码生成器
 */
public class ImageCodeGenerator implements ValidateCodeGenerator {
	
	/**
	 * 系统配置类
	 * @Fields:mySecurityProperties : TODO
	 * get() / set()
	 */
	@Autowired
	private MySecurityProperties mySecurityProperties;

	/* (non-Javadoc)
	 * @see com.imooc.security.core.validate.code.ValidateCodeGenerator#createImageCode(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public ImageCode generate(ServletWebRequest request) {
		/**
		 * 图片的长和宽
		 * 	ServletRequestUtils.getIntParameter(request, "width", mySecurityProperties.getCode().getImage().getWidth());
		 *  先从【请求级配置】里面去（request）获取"width"若没有找到，就从【应用级配置】（mySecurityProperties.getCode().getImage().getWidth()）找，
		 *  若还是没有找到，则从【默认的配置】中找（ImageCodeProperties.width = 67;设置的默认值）
		 */
		int width = ServletRequestUtils.getIntParameter(request.getRequest(), "width", mySecurityProperties.getCode().getImage().getWidth());
		int height = ServletRequestUtils.getIntParameter(request.getRequest(), "height", mySecurityProperties.getCode().getImage().getHeight());
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		Graphics g = image.getGraphics();
		
		Random random = new Random();
		
		// 生成背景条纹
		g.setColor(getRandColor(200,250));
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		
		/**
		 *  生成4位随机数
		 *  	在请求里面无法传，【应用级配置】 ——> 【默认的配置】
		 */
		String sRand = "";
		for (int i = 0; i < mySecurityProperties.getCode().getImage().getLength(); i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			// 将随机数写到图片上
			g.drawString(rand, 13 * i + 6, 16);
		}
		
		g.dispose();
		return new ImageCode(image, sRand, mySecurityProperties.getCode().getImage().getExpireIn());
	}
	
	/**
	 * 生成随机背景条纹
	 * 
	 * @param fc
	 * @param bc
	 * @return
	 */
	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	public MySecurityProperties getMySecurityProperties() {
		return mySecurityProperties;
	}

	public void setMySecurityProperties(MySecurityProperties mySecurityProperties) {
		this.mySecurityProperties = mySecurityProperties;
	}

	
}
