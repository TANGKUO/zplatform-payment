package com.zlebank.zplatform.payment.commons.exception;

import java.util.ResourceBundle;

public abstract class AbstractAccountPayDescException extends AbstractDescribeException{
	 
    /**
	 * 
	 */
	private static final long serialVersionUID = 8484664176033605192L;
	private static final  ResourceBundle RESOURCE = ResourceBundle.getBundle("payment_exception");
	@Override
	public ResourceBundle getResourceBundle() {
		return RESOURCE;
	}
}
