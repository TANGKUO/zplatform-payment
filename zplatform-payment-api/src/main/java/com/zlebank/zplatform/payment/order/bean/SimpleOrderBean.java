/* 
 * OrderBean.java  
 * 
 * version TODO
 *
 * 2015年8月27日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.payment.order.bean;

import java.io.Serializable;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年8月27日 上午10:41:06
 * @since 
 */
public class SimpleOrderBean implements Serializable{
    private static final long serialVersionUID = -7317360443683683832L;
    
    
    private String coopInstiId="";
    private String merId="";
    private String merName="";
    private String merAbbr="";
    private String orderId="";
    private String txnType="";
    private String txnSubType="";
    private String bizType="";
    private String channelType="";
    private String accessType="";
    private String txnTime="";
    private String txnAmt="";
    private String currencyCode="";
    private String orderTimeout="";
    private String payTimeout="";
    private String reqReserved="";
    private String reserved="";
    private String orderDesc="";
    private String memberId="";
    private String productcode;
    private String groupCode;
    private String frontUrl="";
    private String backUrl="";
	/**
	 * @return the frontUrl
	 */
	public String getFrontUrl() {
		return frontUrl;
	}
	/**
	 * @param frontUrl the frontUrl to set
	 */
	public void setFrontUrl(String frontUrl) {
		this.frontUrl = frontUrl;
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
	 * @return the merName
	 */
	public String getMerName() {
		return merName;
	}
	/**
	 * @param merName the merName to set
	 */
	public void setMerName(String merName) {
		this.merName = merName;
	}
	/**
	 * @return the merAbbr
	 */
	public String getMerAbbr() {
		return merAbbr;
	}
	/**
	 * @param merAbbr the merAbbr to set
	 */
	public void setMerAbbr(String merAbbr) {
		this.merAbbr = merAbbr;
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
	 * @return the accessType
	 */
	public String getAccessType() {
		return accessType;
	}
	/**
	 * @param accessType the accessType to set
	 */
	public void setAccessType(String accessType) {
		this.accessType = accessType;
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
	 * @return the orderTimeout
	 */
	public String getOrderTimeout() {
		return orderTimeout;
	}
	/**
	 * @param orderTimeout the orderTimeout to set
	 */
	public void setOrderTimeout(String orderTimeout) {
		this.orderTimeout = orderTimeout;
	}
	/**
	 * @return the payTimeout
	 */
	public String getPayTimeout() {
		return payTimeout;
	}
	/**
	 * @param payTimeout the payTimeout to set
	 */
	public void setPayTimeout(String payTimeout) {
		this.payTimeout = payTimeout;
	}
	/**
	 * @return the reqReserved
	 */
	public String getReqReserved() {
		return reqReserved;
	}
	/**
	 * @param reqReserved the reqReserved to set
	 */
	public void setReqReserved(String reqReserved) {
		this.reqReserved = reqReserved;
	}
	/**
	 * @return the reserved
	 */
	public String getReserved() {
		return reserved;
	}
	/**
	 * @param reserved the reserved to set
	 */
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}
	/**
	 * @return the orderDesc
	 */
	public String getOrderDesc() {
		return orderDesc;
	}
	/**
	 * @param orderDesc the orderDesc to set
	 */
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}
	/**
	 * @return the memberId
	 */
	public String getMemberId() {
		return memberId;
	}
	/**
	 * @param memberId the memberId to set
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	/**
	 * @return the productcode
	 */
	public String getProductcode() {
		return productcode;
	}
	/**
	 * @param productcode the productcode to set
	 */
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	/**
	 * @return the groupCode
	 */
	public String getGroupCode() {
		return groupCode;
	}
	/**
	 * @param groupCode the groupCode to set
	 */
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
    
    
    
}
