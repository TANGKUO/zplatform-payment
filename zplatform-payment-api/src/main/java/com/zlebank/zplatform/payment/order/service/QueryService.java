/* 
 * QueryService.java  
 * 
 * version TODO
 *
 * 2016年10月17日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.payment.order.service;

import com.zlebank.zplatform.payment.order.bean.OrderResultBean;

/**
 * 交易订单查询接口
 *
 * @author guojia
 * @version
 * @date 2016年10月17日 上午10:03:21
 * @since 
 */
public interface QueryService {

	/**
	 * 交易订单查询方法
	 * @param merchNo 商户号
	 * @param orderId 订单号
	 * @return 订单结果bean
	 */
	public OrderResultBean queryOrder(String merchNo,String orderId); 
}