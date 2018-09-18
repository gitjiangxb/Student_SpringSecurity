package com.imooc.exception;

/**
 * 自定义的一个异常
 * @author Jiangxb
 *	UserController.getInfo(Integer id)做为测试，报错时，需要把 id 反馈出来
 *	主要需要明确知道：是哪个用户信息不存在
 */
public class UserNotExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	public UserNotExistException(String id) {
		super("User not Exist");
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
