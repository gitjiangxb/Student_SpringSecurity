package com.imooc.dto;


import java.util.Date;

import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonView;
import com.imooc.validator.MyConstraint;

/**
 * dto 这个包下 是存储输入输出的bean的
 * @author Jiangxb
 *
 */
public class User {
	//简单视图
	public interface UserSimpleView{};
	//详细视图 继承 简单视图
	public interface UserDetailView extends UserSimpleView{};
	
	private String id;
	
	@MyConstraint(message = "这是测试——自定义校验注解")
	private String username;
	
	@NotBlank(message = "密码不能为空")
	private String password;
	
	@Past(message = "生日必须是过去时间")
	private Date birthday;
	
	@JsonView(UserSimpleView.class)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	//指定username在简单视图中显示
	@JsonView(UserSimpleView.class)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	//指定password在详细视图中显示，因为基础了简单视图，所以username也会显示
	@JsonView(UserDetailView.class)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@JsonView(UserSimpleView.class)
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", birthday=" + birthday + "]";
	}
	
}
