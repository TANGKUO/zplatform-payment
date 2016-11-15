/* 
 * PaymentRealNameException.java  
 * 
 * version TODO
 *
 * 2016年11月10日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.payment.exception;

import com.zlebank.zplatform.payment.commons.exception.AbstractRealNameDescException;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年11月10日 上午11:22:25
 * @since 
 */
public class PaymentRealNameException extends AbstractRealNameDescException{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5825227604243819693L;
	private String code;
	public PaymentRealNameException(String code,Object ... para ) {
        this.params = para;
        this.code = code;
    }
	
	public PaymentRealNameException(String code) {
        this.code = code;
    }
	
    /**
     *
     * @return
     */
    @Override
    public String getCode() {
        return code;
    }
	/**
	 * 
	 */
	public PaymentRealNameException() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}
