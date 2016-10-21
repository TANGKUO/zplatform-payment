/* 
 * InsteadPayOrderBean.java  
 * 
 * version TODO
 *
 * 2016年10月20日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.payment.order.bean;

import java.io.Serializable;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年10月20日 上午8:44:59
 * @since
 */
public class InsteadPayOrderBean implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8986669943007549989L;
	/**
	 *  交易类型
	 */
	private String txnType;
	/**
	 *  交易子类
	 */
	private String txnSubType;
	/**
	 *  产品类型
	 */
	private String bizType;
	/** 
	 * 渠道类型
	 */
	private String channelType;
	/** 
	 * 通知地址
	 */
	private String backUrl;
	/** 
	 * 订单发送时间
	 */
	private String txnTime;
	/** 
	 * 商户代码
	 */
	private String merId;
	/** 
	 * 商户订单号
	 */
	private String orderId;
	/** 
	 * 交易币种
	 */
	private String currencyCode;
	/** 
	 * 金额
	 */
	private String txnAmt;
	/** 
	 * 账号类型
	 */
	private String accType;
	/** 
	 * 账号
	 */
	private String accNo;
	/** 
	 * 户名
	 */
	private String accName;
	/** 
	 * 开户行代码
	 */
	private String bankCode;
	/** 
	 * 开户行省
	 */
	private String issInsProvince;
	/** 
	 * 开户行市
	 */
	private String issInsCity;
	/** 
	 * 开户行名称
	 */
	private String issInsName;
	/** 
	 * 证件类型
	 */
	private String certifTp;
	/** 
	 * 证件号码
	 */
	private String certifId;
	/** 
	 * 手机号
	 */
	private String phoneNo;
	
	/**
	 * 合作机构代码
	 */
	private String coopInstiId;
	/**
	 * 附言
	 */
	private String notes;
	/**
	 * @return the txnType
	 */
	public String getTxnType() {
		return txnType;
	}
	/**
	 * @param txnType the txnType to set
	 */
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	/**
	 * @return the txnSubType
	 */
	public String getTxnSubType() {
		return txnSubType;
	}
	/**
	 * @param txnSubType the txnSubType to set
	 */
	public void setTxnSubType(String txnSubType) {
		this.txnSubType = txnSubType;
	}
	/**
	 * @return the bizType
	 */
	public String getBizType() {
		return bizType;
	}
	/**
	 * @param bizType the bizType to set
	 */
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	/**
	 * @return the channelType
	 */
	public String getChannelType() {
		return channelType;
	}
	/**
	 * @param channelType the channelType to set
	 */
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	/**
	 * @return the backUrl
	 */
	public String getBackUrl() {
		return backUrl;
	}
	/**
	 * @param backUrl the backUrl to set
	 */
	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}
	/**
	 * @return the txnTime
	 */
	public String getTxnTime() {
		return txnTime;
	}
	/**
	 * @param txnTime the txnTime to set
	 */
	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}
	/**
	 * @return the merId
	 */
	public String getMerId() {
		return merId;
	}
	/**
	 * @param merId the merId to set
	 */
	public void setMerId(String merId) {
		this.merId = merId;
	}
	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}
	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	/**
	 * @return the txnAmt
	 */
	public String getTxnAmt() {
		return txnAmt;
	}
	/**
	 * @param txnAmt the txnAmt to set
	 */
	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}
	/**
	 * @return the accType
	 */
	public String getAccType() {
		return accType;
	}
	/**
	 * @param accType the accType to set
	 */
	public void setAccType(String accType) {
		this.accType = accType;
	}
	/**
	 * @return the accNo
	 */
	public String getAccNo() {
		return accNo;
	}
	/**
	 * @param accNo the accNo to set
	 */
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	/**
	 * @return the accName
	 */
	public String getAccName() {
		return accName;
	}
	/**
	 * @param accName the accName to set
	 */
	public void setAccName(String accName) {
		this.accName = accName;
	}
	/**
	 * @return the bankCode
	 */
	public String getBankCode() {
		return bankCode;
	}
	/**
	 * @param bankCode the bankCode to set
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	/**
	 * @return the issInsProvince
	 */
	public String getIssInsProvince() {
		return issInsProvince;
	}
	/**
	 * @param issInsProvince the issInsProvince to set
	 */
	public void setIssInsProvince(String issInsProvince) {
		this.issInsProvince = issInsProvince;
	}
	/**
	 * @return the issInsCity
	 */
	public String getIssInsCity() {
		return issInsCity;
	}
	/**
	 * @param issInsCity the issInsCity to set
	 */
	public void setIssInsCity(String issInsCity) {
		this.issInsCity = issInsCity;
	}
	/**
	 * @return the issInsName
	 */
	public String getIssInsName() {
		return issInsName;
	}
	/**
	 * @param issInsName the issInsName to set
	 */
	public void setIssInsName(String issInsName) {
		this.issInsName = issInsName;
	}
	/**
	 * @return the certifTp
	 */
	public String getCertifTp() {
		return certifTp;
	}
	/**
	 * @param certifTp the certifTp to set
	 */
	public void setCertifTp(String certifTp) {
		this.certifTp = certifTp;
	}
	/**
	 * @return the certifId
	 */
	public String getCertifId() {
		return certifId;
	}
	/**
	 * @param certifId the certifId to set
	 */
	public void setCertifId(String certifId) {
		this.certifId = certifId;
	}
	/**
	 * @return the phoneNo
	 */
	public String getPhoneNo() {
		return phoneNo;
	}
	/**
	 * @param phoneNo the phoneNo to set
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	/**
	 * @return the coopInstiId
	 */
	public String getCoopInstiId() {
		return coopInstiId;
	}
	/**
	 * @param coopInstiId the coopInstiId to set
	 */
	public void setCoopInstiId(String coopInstiId) {
		this.coopInstiId = coopInstiId;
	}
	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}
	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
}
