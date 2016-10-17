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

import com.zlebank.zplatform.payment.commons.dao.TxnsLogDAO;
import com.zlebank.zplatform.payment.commons.dao.TxnsOrderinfoDAO;
import com.zlebank.zplatform.payment.commons.enums.BusiTypeEnum;
import com.zlebank.zplatform.payment.commons.enums.OrderType;
import com.zlebank.zplatform.payment.order.bean.OrderResultBean;
import com.zlebank.zplatform.payment.order.service.QueryService;
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
	/**
	 *
	 * @param merchNo
	 * @param orderId
	 * @return
	 */
	@Override
	public OrderResultBean queryOrder(String merchNo, String orderId) {
		PojoTxnsOrderinfo orderinfo = txnsOrderinfoDAO.getOrderinfoByOrderNoAndMerchNo(orderId, merchNo);
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
