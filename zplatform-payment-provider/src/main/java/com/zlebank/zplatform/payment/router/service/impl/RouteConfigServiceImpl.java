/* 
 * RouteConfigServiceImp.java  
 * 
 * version TODO
 *
 * 2016年10月12日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.payment.router.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.payment.dao.RouteConfigDAO;
import com.zlebank.zplatform.payment.exception.PaymentQuickPayException;
import com.zlebank.zplatform.payment.exception.PaymentRouterException;
import com.zlebank.zplatform.payment.router.service.RouteConfigService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年10月12日 上午9:41:39
 * @since 
 */
@Service("RouteConfigService")
public class RouteConfigServiceImpl implements RouteConfigService{

	@Autowired
	private RouteConfigDAO routeConfigDAO;
	/**
	 *
	 * @param transTime
	 * @param transAmt
	 * @param memberId
	 * @param busiCode
	 * @param cardNo
	 * @return
	 * @throws PaymentQuickPayException
	 */
	@Override
	public String getTradeChannel(String transTime, String transAmt,String memberId, String busiCode, String cardNo,String routeVer)throws PaymentRouterException {
		return routeConfigDAO.getTradeRoute(transTime, transAmt, memberId, busiCode, cardNo, routeVer);
	}
	/**
	 *
	 * @param cardNo
	 * @return
	 */
	@Override
	public Map<String, Object> getCardInfo(String cardNo) {
		// TODO Auto-generated method stub
		return routeConfigDAO.getCardInfo(cardNo);
	}

	
}
