/* 
 * QueryServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年10月17日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.payment.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.payment.dao.InsteadPayRealtimeDAO;
import com.zlebank.zplatform.payment.dao.TxnsLogDAO;
import com.zlebank.zplatform.payment.dao.TxnsOrderinfoDAO;
import com.zlebank.zplatform.payment.enums.BusiTypeEnum;
import com.zlebank.zplatform.payment.enums.OrderType;
import com.zlebank.zplatform.payment.exception.PaymentOrderException;
import com.zlebank.zplatform.payment.order.bean.OrderResultBean;
import com.zlebank.zplatform.payment.order.service.QueryService;
import com.zlebank.zplatform.payment.pojo.PojoInsteadPayRealtime;
import com.zlebank.zplatform.payment.pojo.PojoTxnsLog;
import com.zlebank.zplatform.payment.pojo.PojoTxnsOrderinfo;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年10月17日 上午10:15:49
 * @since 
 */
@Service("queryService")
public class QueryServiceImpl implements QueryService{

	@Autowired
	private TxnsOrderinfoDAO txnsOrderinfoDAO;
	@Autowired
	private TxnsLogDAO txnsLogDAO;
	@Autowired
	private InsteadPayRealtimeDAO insteadPayRealtimeDAO;
	/**
	 *
	 * @param merchNo
	 * @param orderId
	 * @return
	 * @throws PaymentOrderException 
	 */
	@Override
	public OrderResultBean queryOrder(String merchNo, String orderId) throws PaymentOrderException {
		PojoTxnsOrderinfo orderinfo = txnsOrderinfoDAO.getOrderinfoByOrderNoAndMerchNo(orderId, merchNo);
		if(orderinfo==null){
			throw new PaymentOrderException("PC004");
		}
		PojoTxnsLog txnsLog = txnsLogDAO.getTxnsLogByTxnseqno(orderinfo.getRelatetradetxn());
		OrderResultBean order = new OrderResultBean();
		order.setMerId(orderinfo.getFirmemberno());
		order.setMerName(orderinfo.getFirmembername());
		order.setMerAbbr(orderinfo.getFirmembershortname());
		order.setOrderId(orderinfo.getOrderno());
		order.setTxnAmt(orderinfo.getOrderamt()+"");
		order.setTxnTime(orderinfo.getOrdercommitime());
		order.setOrderStatus(orderinfo.getStatus());
		order.setOrderDesc(orderinfo.getOrderdesc());
		order.setCurrencyCode(orderinfo.getCurrencycode());
		order.setTn(orderinfo.getTn());
		BusiTypeEnum busitype = BusiTypeEnum.fromValue(txnsLog.getBusitype());
		String code=OrderType.UNKNOW.getCode();
		if(busitype.equals(BusiTypeEnum.consumption)){
			code=OrderType.CONSUME.getCode();
		}else if(busitype.equals(BusiTypeEnum.refund)){
			code=OrderType.REFUND.getCode();
		}else if(busitype.equals(BusiTypeEnum.charge)){
			code=OrderType.RECHARGE.getCode();
		}else if(busitype.equals(BusiTypeEnum.withdrawal)){
			code=OrderType.WITHDRAW.getCode();
		}
		order.setOrderType(code);
		return order;
	}
	/**
	 *
	 * @param merchNo
	 * @param orderId
	 * @throws PaymentOrderException 
	 */
	@Override
	public OrderResultBean queryInsteadPayOrder(String merchNo, String orderId) throws PaymentOrderException {
		PojoInsteadPayRealtime orderinfo = insteadPayRealtimeDAO.getOrderinfoByOrderNoAndMerchNo(merchNo, orderId);//getOrderinfoByOrderNoAndMerchNo(orderId, merchNo);
		if(orderinfo==null){
			throw new PaymentOrderException("PC004");
		}
		PojoTxnsLog txnsLog = txnsLogDAO.getTxnsLogByTxnseqno(orderinfo.getTxnseqno());
		OrderResultBean order = new OrderResultBean();
		order.setMerId(orderinfo.getMerId());
		order.setMerName(orderinfo.getMerName());
		order.setMerAbbr(orderinfo.getMerNameAbbr());
		order.setOrderId(orderinfo.getOrderno());
		order.setTxnAmt(orderinfo.getTransAmt()+"");
		order.setTxnTime(orderinfo.getOrderCommiTime());
		order.setOrderStatus(orderinfo.getStatus());
		order.setOrderDesc(orderinfo.getNotes());
		order.setCurrencyCode(orderinfo.getCurrencyCode());
		order.setTn(orderinfo.getTn());
		BusiTypeEnum busitype = BusiTypeEnum.fromValue(txnsLog.getBusitype());
		String code=OrderType.UNKNOW.getCode();
		if(busitype.equals(BusiTypeEnum.consumption)){
			code=OrderType.CONSUME.getCode();
		}else if(busitype.equals(BusiTypeEnum.refund)){
			code=OrderType.REFUND.getCode();
		}else if(busitype.equals(BusiTypeEnum.charge)){
			code=OrderType.RECHARGE.getCode();
		}else if(busitype.equals(BusiTypeEnum.withdrawal)){
			code=OrderType.WITHDRAW.getCode();
		}
		order.setOrderType(code);
		return order;
		
	}
	/**
	 *
	 * @param tn
	 * @return
	 * @throws PaymentOrderException 
	 */
	@Override
	public OrderResultBean queryOrderByTN(String tn) throws PaymentOrderException {
		PojoTxnsOrderinfo orderinfo = txnsOrderinfoDAO.getOrderinfoByTN(tn);
		if(orderinfo==null){
			throw new PaymentOrderException("PC004");
		}
		PojoTxnsLog txnsLog = txnsLogDAO.getTxnsLogByTxnseqno(orderinfo.getRelatetradetxn());
		OrderResultBean order = new OrderResultBean();
		order.setMerId(orderinfo.getFirmemberno());
		order.setMerName(orderinfo.getFirmembername());
		order.setMerAbbr(orderinfo.getFirmembershortname());
		order.setOrderId(orderinfo.getOrderno());
		order.setTxnAmt(orderinfo.getOrderamt()+"");
		order.setTxnTime(orderinfo.getOrdercommitime());
		order.setOrderStatus(orderinfo.getStatus());
		order.setOrderDesc(orderinfo.getOrderdesc());
		order.setCurrencyCode(orderinfo.getCurrencycode());
		order.setTn(orderinfo.getTn());
		BusiTypeEnum busitype = BusiTypeEnum.fromValue(txnsLog.getBusitype());
		String code=OrderType.UNKNOW.getCode();
		if(busitype.equals(BusiTypeEnum.consumption)){
			code=OrderType.CONSUME.getCode();
		}else if(busitype.equals(BusiTypeEnum.refund)){
			code=OrderType.REFUND.getCode();
		}else if(busitype.equals(BusiTypeEnum.charge)){
			code=OrderType.RECHARGE.getCode();
		}else if(busitype.equals(BusiTypeEnum.withdrawal)){
			code=OrderType.WITHDRAW.getCode();
		}
		order.setOrderType(code);
		return order;
	}

}
