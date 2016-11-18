/* 
 * RouteConfigDAO.java  
 * 
 * version TODO
 *
 * 2016年10月12日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.payment.dao;

import java.util.Map;

import com.zlebank.zplatform.payment.commons.dao.BaseDAO;
import com.zlebank.zplatform.payment.exception.PaymentRouterException;
import com.zlebank.zplatform.payment.pojo.PojoRouteConfig;

/**
 * 交易路由DAO--oracle
 *
 * @author guojia
 * @version
 * @date 2016年10月12日 上午9:45:06
 * @since 
 */
public interface RouteConfigDAO extends BaseDAO<PojoRouteConfig>{

	/**
	 * 
	 * @param transTime 交易时间
	 * @param transAmt 交易金额
	 * @param memberId 会员号
	 * @param busiCode 业务代码
	 * @param cardNo 卡号
	 * @param routeVer 路由版本-商户
	 * @return
	 * @throws PaymentRouterException
	 */
	public String getTradeRoute(String transTime, String transAmt,String memberId, String busiCode, String cardNo, String routeVer)
			throws PaymentRouterException;
	
	/**
	 * 通过银行卡卡号获取银行卡bin信息
	 * @param cardNo 银行卡号
	 * @return 卡bin信息集合Map
	 */
	public Map<String, Object> getCardInfo(String cardNo);
}
