/* 
 * RealNameCardService.java  
 * 
 * version TODO
 *
 * 2016年11月10日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.payment.realname.service;

import com.zlebank.zplatform.payment.commons.bean.ResultBean;
import com.zlebank.zplatform.payment.exception.PaymentRealNameException;
import com.zlebank.zplatform.payment.realname.bean.RealNameCardBean;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年11月10日 上午11:18:34
 * @since 
 */
public interface RealNameCardService {

	/**
	 * 银行卡实名认证
	 * @param realNameCardBean 卡实名认证bean
	 * @return
	 * @throws PaymentRealNameException
	 */
	public ResultBean realName(RealNameCardBean realNameCardBean) throws PaymentRealNameException;
}
