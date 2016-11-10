/* 
 * RealNameCardBean.java  
 * 
 * version TODO
 *
 * 2016年11月10日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.payment.realname.bean;

import java.io.Serializable;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年11月10日 上午11:18:02
 * @since 
 */
public class RealNameCardBean implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2370714868513122999L;
	/**
	 * 银行卡卡号
	 */
	private String cardNo;
	/**
	 * 持卡人姓名
	 */
	private String cardKeeper;
	/**
	 * 银行卡类型
	 */
	private String cardType;
	/**
	 * 证件号
	 */
	private String certNo;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * cvn2
	 */
	private String cvn2;
	/**
	 * 卡有效期
	 */
	private String expired;
	/**
	 * @return the cardNo
	 */
	public String getCardNo() {
		return cardNo;
	}
	/**
	 * @param cardNo the cardNo to set
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	/**
	 * @return the cardKeeper
	 */
	public String getCardKeeper() {
		return cardKeeper;
	}
	/**
	 * @param cardKeeper the cardKeeper to set
	 */
	public void setCardKeeper(String cardKeeper) {
		this.cardKeeper = cardKeeper;
	}
	/**
	 * @return the cardType
	 */
	public String getCardType() {
		return cardType;
	}
	/**
	 * @param cardType the cardType to set
	 */
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	/**
	 * @return the certNo
	 */
	public String getCertNo() {
		return certNo;
	}
	/**
	 * @param certNo the certNo to set
	 */
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the cvn2
	 */
	public String getCvn2() {
		return cvn2;
	}
	/**
	 * @param cvn2 the cvn2 to set
	 */
	public void setCvn2(String cvn2) {
		this.cvn2 = cvn2;
	}
	/**
	 * @return the expired
	 */
	public String getExpired() {
		return expired;
	}
	/**
	 * @param expired the expired to set
	 */
	public void setExpired(String expired) {
		this.expired = expired;
	}
	
}
