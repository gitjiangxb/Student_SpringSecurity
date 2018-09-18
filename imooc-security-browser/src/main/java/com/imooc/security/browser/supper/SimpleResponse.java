package com.imooc.security.browser.supper;

/**
 * 简单的响应信息
 * @author Jiangxb
 *	封装返回信息
 */
public class SimpleResponse {
	
	/**
	 * 封装待返回的内容
	 * @Fields:content : TODO
	 */
	private Object content;
	
	public SimpleResponse(Object content) {
		this.content = content;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}
	
	
}
