package com.zlebank.zplatform.payment.dao;

import com.zlebank.zplatform.payment.commons.dao.BaseDAO;
import com.zlebank.zplatform.payment.pojo.PojoInsteadPayRealtime;

/**
 * 
 * 实时代付订单DAO接口
 *
 * @author guojia
 * @version
 * @date 2016年10月17日 下午2:37:10
 * @since
 */
public interface InsteadPayRealtimeDAO extends BaseDAO<PojoInsteadPayRealtime>  {

	/**
	 * 通过tn获取代付订单信息
	 * @param tn 受理订单号
	 * @return 代付订单信息pojo
	 */
	public PojoInsteadPayRealtime queryOrderByTN(String tn);
	
	/**
	 * 
	 * @param txnseqno
	 */
	public void updateOrderToStartPay(String txnseqno);
	
	/**
	 * 
	 * @param merchNo
	 * @param orderNo
	 * @return
	 */
	public PojoInsteadPayRealtime getOrderinfoByOrderNoAndMerchNo(String merchNo,String orderNo);
}
