/* 
 * RouteConfigDAOImpl.java  
 * 
 * version TODO
 *
 * 2016年10月12日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.payment.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.payment.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.payment.dao.RouteConfigDAO;
import com.zlebank.zplatform.payment.exception.PaymentRouterException;
import com.zlebank.zplatform.payment.pojo.PojoRouteConfig;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年10月12日 上午9:48:35
 * @since
 */
@Repository("routeConfigDAO")
public class RouteConfigDAOImpl extends HibernateBaseDAOImpl<PojoRouteConfig>
		implements RouteConfigDAO {

	private static final Logger logger = LoggerFactory
			.getLogger(RouteConfigDAOImpl.class);

	@Transactional(readOnly = true)
	@Override
	public String getTradeRoute(String transTime, String transAmt,String memberId, String busiCode, String cardNo, String routeVer)
			throws PaymentRouterException {
		try {
			String bankcode = null;
			String cardType = null;
			if (StringUtils.isNotEmpty(cardNo)) {
				Map<String, Object> cardInfoMap = getCardInfo(cardNo);
				if (cardInfoMap == null) {

				}
				bankcode = cardInfoMap.get("BANKCODE").toString();
				cardType = cardInfoMap.get("TYPE").toString();
			}
			StringBuffer sqlBuffer = new StringBuffer();
			List<Object> paramList = new ArrayList<Object>();
			sqlBuffer = new StringBuffer();
			sqlBuffer.append("SELECT rt ");
			sqlBuffer.append("FROM (SELECT t.rid AS rid, t.ROUTVER AS rt ");
			sqlBuffer.append("FROM t_route_config t ");
			sqlBuffer.append("WHERE 1=1 ");
			if (StringUtils.isNotEmpty(routeVer)) {
				sqlBuffer.append("AND t.merchroutver = ? ");
				paramList.add(routeVer);
			}
			sqlBuffer.append("AND t.status = '00' ");
			sqlBuffer.append("AND t.stime <= ? ");
			paramList.add(transTime);
			sqlBuffer.append("AND t.etime > ? ");
			paramList.add(transTime);
			sqlBuffer.append("AND t.minamt <= ? ");
			paramList.add(transAmt);
			sqlBuffer.append("AND t.maxamt >= ? ");
			paramList.add(transAmt);
			sqlBuffer.append("AND t.isdef = '1' ");

			if (StringUtils.isNotEmpty(bankcode)) {
				sqlBuffer
						.append("AND (t.bankcode like ? or t.bankcode is null) ");
				paramList.add("%" + bankcode + ";%");
			}
			if (StringUtils.isNotEmpty(busiCode)) {
				sqlBuffer
						.append("AND  (t.busicode like ? or t.busicode is null) ");
				paramList.add("%" + busiCode + ";%");
			}
			if (StringUtils.isNotEmpty(cardType)) {
				sqlBuffer
						.append("AND (t.cardtype like ? or t.cardtype is null) ");
				paramList.add("%" + cardType + ";%");
			}
			sqlBuffer
					.append(" ORDER BY t.ordertype, t.orders) t1 WHERE ROWNUM = 1");
			logger.info("member rout sql:" + sqlBuffer);

			Session session = getSession();
			SQLQuery query = (SQLQuery) session.createSQLQuery(
					sqlBuffer.toString()).setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP);
			Object[] paramaters = paramList.toArray();
			if (null != paramaters) {
				for (int i = 0; i < paramaters.length; i++) {
					query.setParameter(i, paramaters[i]);
				}
			}
			List<Map<String, Object>> memberRoutList = query.list();
			if (memberRoutList.size() > 0) {
				logger.info("member rout:" + memberRoutList.get(0));
				return memberRoutList.get(0).get("RT").toString();
			} else {// 查找当前路由版本下的默认路由
				session = getSession();
				sqlBuffer = new StringBuffer();
				sqlBuffer.append("SELECT t.ROUTVER as rout ");
				sqlBuffer.append("FROM t_route_config t ");
				sqlBuffer.append("WHERE t.merchroutver = ? ");
				sqlBuffer.append("AND t.status = '00' ");
				sqlBuffer.append("AND t.isdef = '0' ");
				logger.info("member default rout sql:" + sqlBuffer);
				query = (SQLQuery) session.createSQLQuery(sqlBuffer.toString())
						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				query.setParameter(0, routeVer);
				List<Map<String, Object>> defaultMemberRoutList = query.list();
				if (defaultMemberRoutList.size() > 0) {
					logger.info("member default rout:"
							+ defaultMemberRoutList.get(0));
					return defaultMemberRoutList.get(0).get("ROUT").toString();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new PaymentRouterException("PC009");
		}
		logger.info("member " + memberId + " no find member rout!!!");
		throw new PaymentRouterException("PC010");
	}

	@Transactional(readOnly = true)
	public Map<String, Object> getCardInfo(String cardNo) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT type,bankcode,bankname ");
		sqlBuffer.append("FROM (SELECT t.TYPE,t.BANKCODE,b.bankname ");
		sqlBuffer.append("FROM t_card_bin t, t_bank_insti b ");
		sqlBuffer.append("WHERE t.bankcode = b.bankcode ");
		sqlBuffer.append("AND ? LIKE t.cardbin || '%' ");
		sqlBuffer.append("AND t.cardlen = ? ");
		sqlBuffer.append("ORDER BY t.cardbin DESC) ");
		sqlBuffer.append("WHERE ROWNUM = 1 ");
		Session session = getSession();
		SQLQuery query = (SQLQuery) session
				.createSQLQuery(sqlBuffer.toString()).setResultTransformer(
						Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter(0, cardNo);
		query.setParameter(1, cardNo.trim().length());
		List<Map<String, Object>> routList = query.list();
		if (routList.size() > 0) {
			return routList.get(0);
		}
		return null;
	}
}
