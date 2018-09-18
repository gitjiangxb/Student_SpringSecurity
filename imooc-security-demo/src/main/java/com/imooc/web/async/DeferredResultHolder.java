package com.imooc.web.async;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * 使用DeferredReult异步处理Rest服务
 * ——在线程之间传递DeferredResult对象
 * @author Jiangxb
 *
 */
@Component
public class DeferredResultHolder {
	/**
	 * 可以理解成：
	 * key	：订单号
	 * value：订单号的处理结果 
	 */
	private Map<String,DeferredResult<String>> map = new HashMap<String,DeferredResult<String>>();

	public Map<String, DeferredResult<String>> getMap() {
		return map;
	}

	public void setMap(Map<String, DeferredResult<String>> map) {
		this.map = map;
	}
	
	
}
