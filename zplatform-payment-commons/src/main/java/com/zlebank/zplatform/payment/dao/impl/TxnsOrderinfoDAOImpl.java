/* 
 * TxnsOrderinfoDAOImpl.java  
 * 
 * version TODO
 *
 * 2016年9月9日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.payment.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.payment.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.payment.dao.TxnsOrderinfoDAO;
import com.zlebank.zplatform.payment.exception.PaymentQuickPayException;
import com.zlebank.zplatform.payment.pojo.PojoTxnsOrderinfo;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年9月9日 下午5:08:47
 * @since
 */
@Repository
public class TxnsOrderinfoDAOImpl extends
		HibernateBaseDAOImpl<PojoTxnsOrderinfo> implements TxnsOrderinfoDAO {

	/**
	 *
	 * @param orderinfo
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void saveOrderInfo(PojoTxnsOrderinfo orderinfo) {
		// TODO Auto-generated method stub
		super.saveEntity(orderinfo);
	}

	/**
	 *
	 * @param txnseqno
	 * @return
	 */
	@Transactional(readOnly = true)
	public PojoTxnsOrderinfo getOrderinfoByTxnseqno(String txnseqno) {
		Criteria criteria = getSession()
				.createCriteria(PojoTxnsOrderinfo.class);
		criteria.add(Restrictions.eq("relatetradetxn", txnseqno));
		return (PojoTxnsOrderinfo) criteria.uniqueResult();
	}

	/**
	 *
	 * @param orderNo
	 * @param merchNo
	 * @return
	 */
	@Transactional(readOnly = true)
	public PojoTxnsOrderinfo getOrderinfoByOrderNoAndMerchNo(String orderNo,
			String merchNo) {
		Criteria criteria = getSession()
				.createCriteria(PojoTxnsOrderinfo.class);
		criteria.add(Restrictions.eq("orderno", orderNo));
		criteria.add(Restrictions.eq("secmemberno", merchNo));
		return (PojoTxnsOrderinfo) criteria.uniqueResult();
	}

	/**
	 *
	 * @param tn
	 * @return
	 */
	@Transactional(readOnly = true)
	public PojoTxnsOrderinfo getOrderinfoByTN(String tn) {
		Criteria criteria = getSession()
				.createCriteria(PojoTxnsOrderinfo.class);
		criteria.add(Restrictions.eq("tn", tn));
		return (PojoTxnsOrderinfo) criteria.uniqueResult();
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void updateOrderToStartPay(String txnseqno)
			throws PaymentQuickPayException {
		PojoTxnsOrderinfo orderinfo = getWaitPayOrderinfo(txnseqno);
		if (orderinfo == null) {
			//throw new PaymentQuickPayException("T010");
		} else {
			Session session = getSession();
			Query query = session
					.createQuery("update PojoTxnsOrderinfo set status = ? where relatetradetxn = ? and (status=? or status = ?)");

			Object[] paramaters = new Object[] { "02", txnseqno, "01", "03" };
			if (paramaters != null) {
				for (int i = 0; i < paramaters.length; i++) {
					query.setParameter(i, paramaters[i]);
				}
			}
			int rows = query.executeUpdate();
			if (rows != 1) {
				//throw new PaymentQuickPayException("T011");
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public PojoTxnsOrderinfo getWaitPayOrderinfo(String txnseqno) {
		Session session = getSession();
		Query query = session
				.createQuery("from PojoTxnsOrderinfo where relatetradetxn = ? and (status=? or status = ?)");
		Object[] paramaters = new Object[] { txnseqno, "01", "03" };
		if (paramaters != null) {
			for (int i = 0; i < paramaters.length; i++) {
				query.setParameter(i, paramaters[i]);
			}
		}
		PojoTxnsOrderinfo orderinfo = (PojoTxnsOrderinfo) query.uniqueResult();
		if (orderinfo != null) {
			session.evict(orderinfo);
		}
		return orderinfo;
	}

}
