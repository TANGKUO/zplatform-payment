/* 
 * PayBean.java  
 * 
 * version TODO
 *
 * 2016年10月11日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.payment.validate.bean;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 支付数据
 *
 * @author guojia
 * @version
 * @date 2016年10月11日 下午5:00:54
 * @since 
 */
public class PayCheckBean implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3925366988705419895L;
	/**
	 * 受理订单号
	 */
	@NotEmpty(message = "param.empty.tn")
	@Length(max = 18, message = "param.error.tn")
	private String tn;
	/**
	 * 银行卡卡号
	 */
	@Length(max = 32, message = "param.error.cardNo")
	private String cardNo;
	/**
	 * 持卡人姓名
	 */
	@Length(max = 64, message = "param.error.cardKeeper")
	private String cardKeeper;
	/**
	 * 银行卡类型
	 */
	@Length(max = 1, message = "param.error.cardType")
	private String cardType;
	/**
	 * 证件号
	 */
	@Length(max = 32, message = "param.error.certNo")
	private String certNo;
	/**
	 * 手机号
	 */
	@Length(max = 32, message = "param.error.phone")
	private String phone;
	/**
	 * 绑卡标示
	 */
	@Length(max = 12, message = "param.error.bindId")
	private String bindId;
	/**
	 * @return the tn
	 */
	public String getTn() {
		return tn;
	}
	/**
	 * @param tn the tn to set
	 */
	public void setTn(String tn) {
		this.tn = tn;
	}
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
	 * @return the bindId
	 */
	public String getBindId() {
		return bindId;
	}
	/**
	 * @param bindId the bindId to set
	 */
	public void setBindId(String bindId) {
		this.bindId = bindId;
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
	
	
}
