package com.imooc.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import com.imooc.exception.UserNotExistException;
/**
 * 控制器的错误处理器
 * @author Jiangxb
 *
 */
//这个类里面写的方法都是原来处理其他的控制器所抛出来的异常
@ControllerAdvice
public class ControllerExceptionHandler {
	/**
	 * 针对UserNotExistException这个异常做的响应
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(UserNotExistException.class)	// 处理什么异常
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)	//500
	public Map<String,Object> handleUserNotExistException(UserNotExistException ex){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("id",ex.getId());
		resultMap.put("message", ex.getMessage()); 	
		return resultMap;
	}
}
