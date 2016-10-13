/* 
 * TxnsLogDAOImpl.java  
 * 
 * version TODO
 *
 * 2016年9月13日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.payment.commons.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.payment.commons.dao.TxnsLogDAO;
import com.zlebank.zplatform.payment.exception.PaymentRouterException;
import com.zlebank.zplatform.payment.pojo.PojoTxnsLog;
import com.zlebank.zplatform.payment.quickpay.bean.PayBean;
import com.zlebank.zplatform.payment.risk.enums.RiskLevelEnum;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年9月13日 下午5:33:02
 * @since
 */
@Repository
public class TxnsLogDAOImpl extends HibernateBaseDAOImpl<PojoTxnsLog> implements
		TxnsLogDAO {

	private static final Logger log = LoggerFactory
			.getLogger(TxnsLogDAOImpl.class);

	public void saveTxnsLog(PojoTxnsLog txnsLog) {
		super.saveEntity(txnsLog);
	}

	/**
	 *
	 * @param txnseqno
	 * @return
	 */
	@Override
	public PojoTxnsLog getTxnsLogByTxnseqno(String txnseqno) {
		Criteria criteria = getSession().createCriteria(PojoTxnsLog.class);
		criteria.add(Restrictions.eq("txnseqno", txnseqno));
		return (PojoTxnsLog) criteria.uniqueResult();
	}

	/**
	 *
	 */
	@Override
	public void riskTradeControl(String txnseqno, String coopInsti,
			String merchNo, String memberId, String busiCode, String txnAmt,
			String cardType, String cardNo) throws PaymentRouterException {
		// TODO Auto-generated method stub
		log.info("trade risk control start");
		int riskLevel = 0;
		int riskOrder = 0;
		RiskLevelEnum riskLevelEnum = null;
		String riskInfo = "";

		Session session = getSession();
		SQLQuery query = (SQLQuery) session.createSQLQuery(
				"SELECT FNC_GETRISK(?,?,?,?,?,?,?) AS RISK FROM DUAL")
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Object[] paramaters = new Object[] { coopInsti, merchNo, memberId,
				busiCode, txnAmt, cardType, cardNo };
		if (null != paramaters) {
			for (int i = 0; i < paramaters.length; i++) {
				query.setParameter(i, paramaters[i]);
			}
		}
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> riskList = query.list();
		log.info("trade risk result:" + JSON.toJSONString(riskList));
		if (riskList.size() > 0) {
			riskInfo = riskList.get(0).get("RISK") + "";
			if (riskInfo.indexOf(",") > 0) {
				String[] riskInfos = riskInfo.split(",");
				try {
					riskOrder = Integer.valueOf(riskInfos[0]);
					riskLevel = Integer.valueOf(riskInfos[1]);
					riskLevelEnum = RiskLevelEnum.fromValue(riskLevel);
					log.info("riskOrder:" + riskOrder);
					log.info("riskLevel:" + riskLevel);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new PaymentRouterException("T034");
				}
			} else {
				riskLevelEnum = RiskLevelEnum.fromValue(Integer
						.valueOf(riskInfo));
			}
		} else {
			throw new PaymentRouterException("T034");
		}
		if (RiskLevelEnum.PASS == riskLevelEnum) {// 交易通过
			return;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void initretMsg(String txnseqno) {
		// TODO Auto-generated method stub
		String hql = "update PojoTxnsLog set set payretcode = '',payretinfo='',retcode='',retinfo='' where txnseqno = ?  ";
		Session session = getSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, txnseqno);
		int rows = query.executeUpdate();
		log.info("initretmsg sql :{},effect rows:{}", hql, rows);
	}

	/**
	 *
	 * @param txnseqno
	 * @param payBean
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void updateBankCardInfo(String txnseqno, PayBean payBean) {
		// TODO Auto-generated method stub
		String hql = "update PojoTxnsLog set pan = ?,cardtype = ?,cardinstino = ?,panName = ? where txnseqno = ? ";
		Session session = getSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, payBean.getCardNo());
		query.setParameter(1, payBean.getCardType());
		query.setParameter(2, payBean.getBankCode());
		query.setParameter(3, payBean.getCardKeeper());
		query.setParameter(4, txnseqno);
		int rows = query.executeUpdate();
		log.info("updateBankCardInfo() sql :{},effect rows:{}", hql, rows);
	}
}
