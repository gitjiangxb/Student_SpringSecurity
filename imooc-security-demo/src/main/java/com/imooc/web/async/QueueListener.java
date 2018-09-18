package com.imooc.web.async;

import static org.mockito.Matchers.matches;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 使用DeferredReult异步处理Rest服务
 * ——模拟队列监听器，对应线程2
 * @author Jiangxb
 *ContextRefreshedEvent
 *	整个spring容器初始化完毕的事件
 */
@Component
public class QueueListener implements ApplicationListener<ContextRefreshedEvent> {
	
	// 注入模拟的消息队列
	@Autowired
	private MockQueue mockQueue;
	// 注入线程1与线程2之间传递DeferredResult对象
	@Autowired
	private DeferredResultHolder deferredResultHolder;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// 单开一个线程(图：线程2),
		new Thread(()-> {
			// 这里有一个无限循环，要是不单开线程，会阻塞系统的启动
			while(true) {
				if(StringUtils.isNotBlank(mockQueue.getComlaceOrder())) {
					String orderNumber = mockQueue.getComlaceOrder();
					logger.info("返回订单处理结果："+orderNumber);
					/**
					 * 通过订单号拿到DeferredResult
					 * 	当调用setResult()方法，意味着我整个异步处理完成了，往浏览器返回结果
					 */
					deferredResultHolder.getMap().get(orderNumber).setResult("place order success");
					mockQueue.setComlaceOrder(null);
				}else {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	
}
