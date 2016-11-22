/* 
 * OrderServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年10月11日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.payment.order.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.zlebank.zplatform.order.producer.bean.ResultBean;
import com.zlebank.zplatform.order.producer.enums.OrderTagsEnum;
import com.zlebank.zplatform.order.producer.interfaces.Producer;
import com.zlebank.zplatform.payment.exception.PaymentOrderException;
import com.zlebank.zplatform.payment.order.bean.InsteadPayOrderBean;
import com.zlebank.zplatform.payment.order.bean.RefundOrderBean;
import com.zlebank.zplatform.payment.order.bean.SimpleOrderBean;
import com.zlebank.zplatform.payment.order.bean.WithdrawOrderBean;
import com.zlebank.zplatform.payment.order.service.OrderService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年10月11日 下午4:15:56
 * @since 
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService{
	private final static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	@Qualifier("simpleOrderProducer")
	private Producer producer_simple_order;
	@Autowired
	@Qualifier("insteadPayOrderProducer")
	private Producer producer_instead_pay;
	/**
	 *
	 * @param orderBean
	 * @return
	 */
	@Override
	public String createConsumeOrder(SimpleOrderBean orderBean) throws PaymentOrderException {
		//Producer producer = null;
		try {
			//producer = new SimpleOrderProducer(ResourceBundle.getBundle("producer_order").getString("single.namesrv.addr"));
			SendResult sendResult = producer_simple_order.sendJsonMessage(JSON.toJSONString(orderBean), OrderTagsEnum.COMMONCONSUME_SIMPLIFIED);
			ResultBean resultBean = producer_simple_order.queryReturnResult(sendResult);
			if(resultBean.isResultBool()){
				return resultBean.getResultObj().toString();
			}else{
				throw new PaymentOrderException("PC027",resultBean.getErrMsg());
			}
		} catch (MQClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getErrorMessage());
			throw new PaymentOrderException("PC013");
		} catch (RemotingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new PaymentOrderException("PC013");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new PaymentOrderException("PC013");
		} catch (MQBrokerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new PaymentOrderException("PC013");
		}
		
		
	}



	/**
	 *
	 * @param orderBean
	 * @return
	 * @throws PaymentOrderException 
	 */
	@Override
	public String createInsteadPayOrder(InsteadPayOrderBean orderBean) throws PaymentOrderException {
		//Producer producer = null;
		try {
			//producer = new InsteadPayOrderProducer(ResourceBundle.getBundle("producer_order").getString("single.namesrv.addr"));
			SendResult sendResult = producer_instead_pay.sendJsonMessage(JSON.toJSONString(orderBean), OrderTagsEnum.INSTEADPAY_REALTIME);
			ResultBean resultBean = producer_instead_pay.queryReturnResult(sendResult);
			if(resultBean.isResultBool()){
				return resultBean.getResultObj().toString();
			}else{
				throw new PaymentOrderException("PC014",resultBean.getErrMsg());
			}
		} catch (MQClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getErrorMessage());
			throw new PaymentOrderException();
		} catch (RemotingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new PaymentOrderException();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new PaymentOrderException();
		} catch (MQBrokerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new PaymentOrderException();
		}
	}



	/**
	 *
	 * @param refundOrderBean
	 * @return
	 */
	@Override
	public String createRefundOrder (
			RefundOrderBean refundOrderBean) throws PaymentOrderException {
		try {
			//producer = new SimpleOrderProducer(ResourceBundle.getBundle("producer_order").getString("single.namesrv.addr"));
			SendResult sendResult = producer_simple_order.sendJsonMessage(JSON.toJSONString(refundOrderBean), OrderTagsEnum.REFUND_SIMPLIFIED);
			ResultBean resultBean = producer_simple_order.queryReturnResult(sendResult);
			if(resultBean.isResultBool()){
				return resultBean.getResultObj().toString();
			}else{
				throw new PaymentOrderException("PC025",resultBean.getErrMsg());
			}
		} catch (MQClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getErrorMessage());
			throw new PaymentOrderException("PC013");
		} catch (RemotingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new PaymentOrderException("PC013");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new PaymentOrderException("PC013");
		} catch (MQBrokerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new PaymentOrderException("PC013");
		}
	}



	/**
	 *
	 * @param orderBean
	 * @return
	 * @throws PaymentOrderException
	 */
	@Override
	public String createRechargeOrder(SimpleOrderBean orderBean)
			throws PaymentOrderException {
		try {
			//producer = new SimpleOrderProducer(ResourceBundle.getBundle("producer_order").getString("single.namesrv.addr"));
			SendResult sendResult = producer_simple_order.sendJsonMessage(JSON.toJSONString(orderBean), OrderTagsEnum.RECHARGE_SIMPLIFIED);
			ResultBean resultBean = producer_simple_order.queryReturnResult(sendResult);
			if(resultBean.isResultBool()){
				return resultBean.getResultObj().toString();
			}else{
				throw new PaymentOrderException("PC026",resultBean.getErrMsg());
			}
		} catch (MQClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getErrorMessage());
			throw new PaymentOrderException("PC013");
		} catch (RemotingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new PaymentOrderException("PC013");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new PaymentOrderException("PC013");
		} catch (MQBrokerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new PaymentOrderException("PC013");
		}
	}



	/**
	 *
	 * @param withdrawOrderBean
	 * @return
	 * @throws PaymentOrderException 
	 */
	@Override
	public String createWithdrawOrder(WithdrawOrderBean withdrawOrderBean) throws PaymentOrderException {
		try {
			//producer = new SimpleOrderProducer(ResourceBundle.getBundle("producer_order").getString("single.namesrv.addr"));
			SendResult sendResult = producer_simple_order.sendJsonMessage(JSON.toJSONString(withdrawOrderBean), OrderTagsEnum.WITHDRAW_SIMPLIFIED);
			ResultBean resultBean = producer_simple_order.queryReturnResult(sendResult);
			if(resultBean.isResultBool()){
				return resultBean.getResultObj().toString();
			}else{
				throw new PaymentOrderException("PC024",resultBean.getErrMsg());
			}
		} catch (MQClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getErrorMessage());
			throw new PaymentOrderException("PC013");
		} catch (RemotingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new PaymentOrderException("PC013");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new PaymentOrderException("PC013");
		} catch (MQBrokerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new PaymentOrderException("PC013");
		}
	}
	
}
