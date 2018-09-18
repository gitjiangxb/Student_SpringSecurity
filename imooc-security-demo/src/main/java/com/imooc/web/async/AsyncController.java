package com.imooc.web.async;

import java.util.concurrent.Callable;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * 异步处理REST服务
 * @author Jiangxb
 *
 */
//@RestController
public class AsyncController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 同步处理的方式(图1)
	 * @return
	 */
	@RequestMapping("/order")
	public String order() {
		logger.info("主线程开始");
		try {
			// 休眠1秒，具体的是下单业务流程
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	 
		logger.info("主线程结束");
		return "success";
	}
	
	/**
	 * Callable 实现异步操作(图2)
	 * @return
	 * 副线程在处理业务逻辑的时候，主线程还可以去接收http请求
	 * 不足：
	 * 	使用Runnable来处理时，副线程必须由主线程吊起，副线程代码是写在主线程里面的，当
	 * 应用场景过于负责时，则不试用了
	 */
	@RequestMapping("/asyncOrder1")
	public Callable<String> asyncOrder1(){
		logger.info("主线程开始");
		// Callable实际上就是单开一个线程
		Callable<String> result = new Callable<String>() {
			@Override
			public String call() throws Exception {
				logger.info("副线程开始");
				Thread.sleep(1000);
				logger.info("副线程结束");
				return "success";
			}
		};
		logger.info("主线程结束");
		return result;
	} 
	
	// 注入模拟的消息队列
	@Autowired
	private MockQueue mockQueue;
	// 注入线程1与线程2之间消息传递DeferredResult对象
	@Autowired
	private DeferredResultHolder deferredResultHolder;
	
	/**
	 * DeferredResult 实现异步操作(图：线程1)
	 * @return
	 */
	@RequestMapping("/asyncOrder2")
	public DeferredResult<String> asyncOrder2() {
		logger.info("主线程开始");
		// 生成8位的随机数(订单号)
		String orderNmber = RandomStringUtils.randomNumeric(8);
		mockQueue.setPlaceOrder(orderNmber);	// 设置订单，放入消息队列中
		// 创建消息的交互对象(图：线程1 与 线程2 的交互)
		DeferredResult<String> result = new DeferredResult<String>();
		// 放入【传递】	
		deferredResultHolder.getMap().put(orderNmber, result);
		logger.info("主线程结束");
		return result;
	}
}
