/* 
 * RealTimeInsteadPayService.java  
 * 
 * version TODO
 *
 * 2016年10月21日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.payment.quickpay.service;

import com.zlebank.zplatform.payment.commons.bean.ResultBean;
import com.zlebank.zplatform.payment.exception.PaymentInsteadPayException;
import com.zlebank.zplatform.payment.exception.PaymentOrderException;
import com.zlebank.zplatform.payment.exception.PaymentQuickPayException;
import com.zlebank.zplatform.payment.exception.PaymentRouterException;
import com.zlebank.zplatform.payment.order.bean.InsteadPayOrderBean;

/**
 * 实时代付接口
 *
 * @author guojia
 * @version
 * @date 2016年10月21日 上午10:53:03
 * @since 
 */
public interface RealTimeInsteadPayService {

	/**
	 * 实时代付
	 * @param insteadPayOrderBean 代付订单bean
	 * @return 交易结果bean
	 */
	public ResultBean singleInsteadPay(InsteadPayOrderBean insteadPayOrderBean) throws PaymentOrderException,PaymentInsteadPayException,PaymentQuickPayException,PaymentRouterException;
}
