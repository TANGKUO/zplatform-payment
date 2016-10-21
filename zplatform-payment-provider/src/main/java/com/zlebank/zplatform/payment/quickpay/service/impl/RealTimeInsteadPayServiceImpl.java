/* 
 * RealTimeInsteadPayServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年10月21日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.payment.quickpay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.zlebank.zplatform.cmbc.producer.InsteadPayProducer;
import com.zlebank.zplatform.cmbc.producer.WithholdingProducer;
import com.zlebank.zplatform.cmbc.producer.enums.InsteadPayTagsEnum;
import com.zlebank.zplatform.cmbc.producer.enums.WithholdingTagsEnum;
import com.zlebank.zplatform.cmbc.producer.interfaces.Producer;
import com.zlebank.zplatform.payment.commons.bean.InsteadPayTradeBean;
import com.zlebank.zplatform.payment.commons.bean.ResultBean;
import com.zlebank.zplatform.payment.commons.bean.TradeBean;
import com.zlebank.zplatform.payment.commons.dao.InsteadPayRealtimeDAO;
import com.zlebank.zplatform.payment.commons.dao.TxnsLogDAO;
import com.zlebank.zplatform.payment.commons.dao.TxnsOrderinfoDAO;
import com.zlebank.zplatform.payment.commons.enums.ChannelEnmu;
import com.zlebank.zplatform.payment.commons.enums.TradeStatFlagEnum;
import com.zlebank.zplatform.payment.commons.utils.BeanCopyUtil;
import com.zlebank.zplatform.payment.commons.utils.DateUtil;
import com.zlebank.zplatform.payment.exception.PaymentInsteadPayException;
import com.zlebank.zplatform.payment.exception.PaymentOrderException;
import com.zlebank.zplatform.payment.exception.PaymentQuickPayException;
import com.zlebank.zplatform.payment.exception.PaymentRouterException;
import com.zlebank.zplatform.payment.order.bean.InsteadPayOrderBean;
import com.zlebank.zplatform.payment.order.service.OrderService;
import com.zlebank.zplatform.payment.pojo.PojoInsteadPayRealtime;
import com.zlebank.zplatform.payment.pojo.PojoTxnsLog;
import com.zlebank.zplatform.payment.pojo.PojoTxnsOrderinfo;
import com.zlebank.zplatform.payment.quickpay.service.RealTimeInsteadPayService;
import com.zlebank.zplatform.payment.router.service.RouteConfigService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年10月21日 上午10:58:52
 * @since 
 */
@Service("realTimeInsteadPayService")
public class RealTimeInsteadPayServiceImpl implements RealTimeInsteadPayService {

	@Autowired
	private OrderService orderService;
	@Autowired
	private InsteadPayRealtimeDAO insteadPayRealtimeDAO;
	@Autowired
	private TxnsLogDAO txnsLogDAO;
	@Autowired
	private RouteConfigService routeConfigService;
	/**
	 *
	 * @param insteadPayOrderBean
	 * @return
	 * @throws PaymentOrderException 
	 * @throws PaymentInsteadPayException 
	 * @throws PaymentQuickPayException 
	 * @throws PaymentRouterException 
	 */
	@Override
	public ResultBean singleInsteadPay(InsteadPayOrderBean insteadPayOrderBean) throws PaymentOrderException, PaymentInsteadPayException, PaymentQuickPayException, PaymentRouterException {
		ResultBean resultBean = null;
		String tn = orderService.createInsteadPayOrder(insteadPayOrderBean);
		PojoInsteadPayRealtime orderinfo = insteadPayRealtimeDAO.queryOrderByTN(tn);
		if(orderinfo==null){//订单不存在
			throw new PaymentInsteadPayException();
		}
		if("02".equals(orderinfo.getStatus())){//订单支付中
			//throw new PaymentQuickPayException();
		}
		if("04".equals(orderinfo.getStatus())){//订单过期
			//throw new PaymentQuickPayException();
		}
		if(!insteadPayOrderBean.getTxnAmt().equals(orderinfo.getTransAmt().toString())){
			throw new PaymentInsteadPayException();
		}
		PojoTxnsLog txnsLog = txnsLogDAO.getTxnsLogByTxnseqno(orderinfo.getTxnseqno());
		if(txnsLog==null){
			throw new PaymentInsteadPayException();
		}
		String channelCode = routeConfigService.getTradeChannel(DateUtil.getCurrentDateTime(), orderinfo.getTransAmt().toString(), orderinfo.getMerId(), txnsLog.getBusicode(), txnsLog.getPan(), txnsLog.getRoutver());
		txnsLogDAO.riskTradeControl(txnsLog.getTxnseqno(),txnsLog.getAccfirmerno(),txnsLog.getAccsecmerno(),txnsLog.getAccmemberid(),txnsLog.getBusicode(),txnsLog.getAmount()+"",txnsLog.getCardtype(),txnsLog.getPan());
		txnsLogDAO.initretMsg(txnsLog.getTxnseqno());
		insteadPayRealtimeDAO.updateOrderToStartPay(txnsLog.getTxnseqno());
		txnsLogDAO.updateTradeStatFlag(txnsLog.getTxnseqno(), TradeStatFlagEnum.READY);
		try {
			InsteadPayTradeBean tradeBean = new InsteadPayTradeBean();
			tradeBean.setAcc_no(txnsLog.getPan());
			tradeBean.setAcc_name(txnsLog.getPanName());
			tradeBean.setTrans_amt(txnsLog.getAmount().toString());
			tradeBean.setRemark(orderinfo.getRemark());
			tradeBean.setTxnseqno(txnsLog.getTxnseqno());
			if(ChannelEnmu.fromValue(channelCode)==ChannelEnmu.CMBCINSTEADPAY_REALTIME){
				com.zlebank.zplatform.cmbc.producer.bean.ResultBean sendTradeMsgToCMBC = sendTradeMsgToCMBC(tradeBean);
				resultBean = BeanCopyUtil.copyBean(ResultBean.class, sendTradeMsgToCMBC);
			}
			
		} catch (MQClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemotingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MQBrokerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultBean;
	}

	private com.zlebank.zplatform.cmbc.producer.bean.ResultBean sendTradeMsgToCMBC(InsteadPayTradeBean tradeBean) throws MQClientException, RemotingException, InterruptedException, MQBrokerException{
		Producer producer = new InsteadPayProducer("192.168.101.104:9876", InsteadPayTagsEnum.INSTEADPAY_REALTIME);
		SendResult sendResult = producer.sendJsonMessage(JSON.toJSONString(tradeBean));
		com.zlebank.zplatform.cmbc.producer.bean.ResultBean queryReturnResult = producer.queryReturnResult(sendResult);
		System.out.println(JSON.toJSONString(queryReturnResult));
		return queryReturnResult;
	}
}
