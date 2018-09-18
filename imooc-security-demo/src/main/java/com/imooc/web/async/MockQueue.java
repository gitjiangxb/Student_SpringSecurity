package com.imooc.web.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 使用DeferredReult异步处理Rest服务
 * ——模拟消息队列
 * @author Jiangxb
 *
 */
@Component
public class MockQueue {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private String placeOrder;		//下单的消息：当他有值的时候代表接到了下单消息
	private String comlaceOrder;	//下单完成的消息：当他有值的时候代表接到了下单完成的消息
	
	public String getPlaceOrder() {
		return placeOrder;
	}
	
	public void setPlaceOrder(String placeOrder) {
		new Thread(()->{
			// 模拟下单操作	(图：应用2)
			logger.info("接到下单请求: "+placeOrder);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.comlaceOrder = placeOrder;
			logger.info("下单请求处理完成: "+placeOrder);
  		}).start();
	}
	public String getComlaceOrder() {
		return comlaceOrder;
	}
	public void setComlaceOrder(String comlaceOrder) {
		this.comlaceOrder = comlaceOrder;
	}
	
	
}
