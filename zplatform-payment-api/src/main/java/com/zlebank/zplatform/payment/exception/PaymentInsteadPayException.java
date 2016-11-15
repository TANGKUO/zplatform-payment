/* 
 * PaymentInsteadPayException.java  
 * 
 * version TODO
 *
 * 2016年10月21日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.payment.exception;

import com.zlebank.zplatform.payment.commons.exception.AbstractInsteadPayDescException;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年10月21日 上午11:05:51
 * @since 
 */
public class PaymentInsteadPayException extends AbstractInsteadPayDescException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3258448378139864978L;
	private String code;
	/**
	 *
	 * @return
	 */
	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return code;
	}
	public PaymentInsteadPayException(String code,Object ... para ) {
        this.params = para;
        this.code = code;
    }
	
	public PaymentInsteadPayException(String code) {
        this.code = code;
    }
	/**
	 * 
	 */
	public PaymentInsteadPayException() {
		super();
		// TODO Auto-generated constructor stub
	}
}
