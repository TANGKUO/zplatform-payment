/* 
 * InsteadPayRealtimeDAOImpl.java  
 * 
 * version TODO
 *
 * 2016年10月21日 
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
import com.zlebank.zplatform.payment.dao.InsteadPayRealtimeDAO;
import com.zlebank.zplatform.payment.pojo.PojoInsteadPayRealtime;
import com.zlebank.zplatform.payment.pojo.PojoTxnsOrderinfo;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年10月21日 上午11:49:04
 * @since 
 */
@Repository("insteadPayRealtimeDAO")
public class InsteadPayRealtimeDAOImpl extends HibernateBaseDAOImpl<PojoInsteadPayRealtime> implements InsteadPayRealtimeDAO {

	

	/**
	 *
	 * @param tn
	 * @return
	 */
	@Override
	@Transactional(readOnly=true)
	public PojoInsteadPayRealtime queryOrderByTN(String tn) {
		Criteria criteria = getSession().createCriteria(PojoInsteadPayRealtime.class);
		criteria.add(Restrictions.eq("tn", tn));
		return (PojoInsteadPayRealtime) criteria.uniqueResult();
	}

	/**
	 *
	 * @param txnseqno
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void updateOrderToStartPay(String txnseqno) {
		PojoInsteadPayRealtime orderinfo = getWaitPayOrderinfo(txnseqno);
		if (orderinfo == null) {
			//throw new PaymentQuickPayException("T010");
		} else {
			Session session = getSession();
			Query query = session
					.createQuery("update PojoInsteadPayRealtime set status = ? where txnseqno = ? and (status=? or status = ?)");

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
	public PojoInsteadPayRealtime getWaitPayOrderinfo(String txnseqno) {
		Session session = getSession();
		Query query = session
				.createQuery("from PojoInsteadPayRealtime where txnseqno = ? and (status=? or status = ?)");
		Object[] paramaters = new Object[] { txnseqno, "01", "03" };
		if (paramaters != null) {
			for (int i = 0; i < paramaters.length; i++) {
				query.setParameter(i, paramaters[i]);
			}
		}
		PojoInsteadPayRealtime orderinfo = (PojoInsteadPayRealtime) query.uniqueResult();
		if (orderinfo != null) {
			session.evict(orderinfo);
		}
		return orderinfo;
	}

	/**
	 *
	 * @param merchNo
	 * @param orderNo
	 * @return
	 */
	@Override
	@Transactional(readOnly=true)
	public PojoInsteadPayRealtime getOrderinfoByOrderNoAndMerchNo(
			String merchNo, String orderNo) {
		Criteria criteria = getSession().createCriteria(PojoInsteadPayRealtime.class);
		criteria.add(Restrictions.eq("merId", merchNo));
		criteria.add(Restrictions.eq("orderno", orderNo));
		return (PojoInsteadPayRealtime) criteria.uniqueResult();
	}
}
