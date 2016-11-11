/* 
 * AccountPayService.java  
 * 
 * version TODO
 *
 * 2016年11月11日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.payment.accpay.service;

import com.zlebank.zplatform.payment.accpay.bean.AccountPayBean;
import com.zlebank.zplatform.payment.commons.bean.ResultBean;
import com.zlebank.zplatform.payment.exception.PaymentAccountPayException;
import com.zlebank.zplatform.payment.exception.PaymentQuickPayException;

/**
 * 账户支付service
 *
 * @author guojia
 * @version
 * @date 2016年11月11日 上午10:24:56
 * @since 
 */
public interface AccountPayService {

	/**
	 * 账户支付（无密码）
	 * @param payBean 账户支付bean
	 * @return
	 */
	public ResultBean pay(AccountPayBean payBean) throws PaymentAccountPayException,PaymentQuickPayException;
}
