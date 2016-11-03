/* 
 * QuickPayServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年10月11日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.payment.quickpay.service.impl;

import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.zlebank.zplatform.cmbc.producer.WithholdingProducer;
import com.zlebank.zplatform.cmbc.producer.enums.WithholdingTagsEnum;
import com.zlebank.zplatform.cmbc.producer.interfaces.Producer;
import com.zlebank.zplatform.payment.commons.bean.ResultBean;
import com.zlebank.zplatform.payment.commons.bean.TradeBean;
import com.zlebank.zplatform.payment.commons.dao.TxnsLogDAO;
import com.zlebank.zplatform.payment.commons.dao.TxnsOrderinfoDAO;
import com.zlebank.zplatform.payment.commons.enums.ChannelEnmu;
import com.zlebank.zplatform.payment.commons.enums.TradeStatFlagEnum;
import com.zlebank.zplatform.payment.commons.utils.BeanCopyUtil;
import com.zlebank.zplatform.payment.commons.utils.DateUtil;
import com.zlebank.zplatform.payment.commons.utils.ValidateLocator;
import com.zlebank.zplatform.payment.exception.PaymentQuickPayException;
import com.zlebank.zplatform.payment.exception.PaymentRouterException;
import com.zlebank.zplatform.payment.pojo.PojoTxnsLog;
import com.zlebank.zplatform.payment.pojo.PojoTxnsOrderinfo;
import com.zlebank.zplatform.payment.quickpay.bean.PayBean;
import com.zlebank.zplatform.payment.quickpay.service.QuickPayService;
import com.zlebank.zplatform.payment.router.service.RouteConfigService;
import com.zlebank.zplatform.payment.validate.bean.PayCheckBean;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年10月11日 下午5:15:29
 * @since 
 */
@Service("quickPayService")
public class QuickPayServiceImpl implements QuickPayService{

	@Autowired
	private TxnsOrderinfoDAO txnsOrderinfoDAO;
	@Autowired
	private RouteConfigService routeConfigService;
	@Autowired
	private TxnsLogDAO txnsLogDAO;
	
	
	/**
	 * @param payBean
	 * @return
	 * @throws PaymentQuickPayException
	 */
	@Override
	public ResultBean pay(PayBean payBean) throws PaymentQuickPayException,PaymentRouterException {
		ResultBean resultBean = null;
		/** 支付流程
		 * 0.校验银行卡信息，是否符合卡bin要求，银行卡类型是否正确
		 * 1.订单校验：校验订单是否存在,交易状态是否为待支付，支付中，过期
		 * 2.交易路由：获取支付渠道代码
		 * 3.交易计费：计算交易手续费
		 * 4.交易风控：oracle function 交易风控处理 !!!有风险的交易暂未保存!!
		 * 5.初始化交易数据，保存支付方交易数据和银行卡相关数据
		 * 6.渠道生产者实例化，发送交易数据，查询交易结果
		 */
		checkPayment(payBean);
		PojoTxnsOrderinfo orderinfo = txnsOrderinfoDAO.getOrderinfoByTN(payBean.getTn());
		if(orderinfo==null){//订单不存在
			throw new PaymentQuickPayException("PC004");
		}
		if("02".equals(orderinfo.getStatus())){//订单支付中
			throw new PaymentQuickPayException("PC005");
		}
		if("04".equals(orderinfo.getStatus())){//订单过期
			throw new PaymentQuickPayException("PC006");
		}
		if(!payBean.getTxnAmt().equals(orderinfo.getOrderamt().toString())){
			throw new PaymentQuickPayException("PC007");
		}
		PojoTxnsLog txnsLog = txnsLogDAO.getTxnsLogByTxnseqno(orderinfo.getRelatetradetxn());
		if(txnsLog==null){
			throw new PaymentQuickPayException("PC008");
		}
		String channelCode = routeConfigService.getTradeChannel(DateUtil.getCurrentDateTime(), orderinfo.getOrderamt().toString(), orderinfo.getSecmemberno(), txnsLog.getBusicode(), payBean.getCardNo(), txnsLog.getRoutver());
		txnsLogDAO.riskTradeControl(txnsLog.getTxnseqno(),txnsLog.getAccfirmerno(),txnsLog.getAccsecmerno(),txnsLog.getAccmemberid(),txnsLog.getBusicode(),txnsLog.getAmount()+"",payBean.getCardType(),payBean.getCardNo());
		txnsLogDAO.initretMsg(txnsLog.getTxnseqno());
		txnsLogDAO.updateBankCardInfo(txnsLog.getTxnseqno(), payBean);
		txnsOrderinfoDAO.updateOrderToStartPay(txnsLog.getTxnseqno());
		txnsLogDAO.updateTradeStatFlag(txnsLog.getTxnseqno(), TradeStatFlagEnum.READY);
		TradeBean tradeBean = new TradeBean();
		tradeBean.setCardType(payBean.getCardType());
		tradeBean.setCardNo(payBean.getCardNo());
		tradeBean.setAcctName(payBean.getCardKeeper());
		tradeBean.setCertId(payBean.getCertNo());
		tradeBean.setMobile(payBean.getPhone());
		tradeBean.setTxnseqno(txnsLog.getTxnseqno());
		tradeBean.setBankCode(payBean.getBankCode());
		tradeBean.setAmount(txnsLog.getAmount().toString());
		ChannelEnmu channelEnmu = ChannelEnmu.fromValue(channelCode);
		try {
			if(channelEnmu==ChannelEnmu.CMBCWITHHOLDING){//民生跨行代扣
				com.zlebank.zplatform.cmbc.producer.bean.ResultBean sendTradeMsgToCMBC = sendTradeMsgToCMBC(tradeBean);
				if(sendTradeMsgToCMBC==null){
					throw new PaymentQuickPayException("PC019");
				}
				resultBean = BeanCopyUtil.copyBean(ResultBean.class, sendTradeMsgToCMBC);
			}
		} catch (MQClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new PaymentQuickPayException("PC013");
		} catch (RemotingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new PaymentQuickPayException("PC013");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new PaymentQuickPayException("PC013");
		} catch (MQBrokerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new PaymentQuickPayException("PC013");
		} catch (Throwable e) {
			// TODO: handle exception
			throw new PaymentQuickPayException("PC013");
		}
		
		
		return resultBean;
	}
	
	private com.zlebank.zplatform.cmbc.producer.bean.ResultBean sendTradeMsgToCMBC(TradeBean tradeBean) throws MQClientException, RemotingException, InterruptedException, MQBrokerException{
		Producer producer = new WithholdingProducer(ResourceBundle.getBundle("producer_cmbc").getString("single.namesrv.addr"), WithholdingTagsEnum.WITHHOLDING);
		SendResult sendResult = producer.sendJsonMessage(JSON.toJSONString(tradeBean));
		com.zlebank.zplatform.cmbc.producer.bean.ResultBean queryReturnResult = producer.queryReturnResult(sendResult);
		System.out.println(JSON.toJSONString(queryReturnResult));
		producer.closeProducer();
		return queryReturnResult;
	}
	
	
	private void checkPayment(PayBean payBean) throws PaymentQuickPayException{
		PayCheckBean copyBean = BeanCopyUtil.copyBean(PayCheckBean.class, payBean);
		ResultBean resultBean = ValidateLocator.validateBeans(copyBean);
		if(!resultBean.isResultBool()){//支付信息非空，长度检查出现异常，非法数据
			throw new PaymentQuickPayException("PC001");
		}
		Map<String, Object> cardInfo = routeConfigService.getCardInfo(payBean.getCardNo());
		if(cardInfo==null){//银行卡信息错误
			throw new PaymentQuickPayException("PC002");
		}
		if(!cardInfo.get("TYPE").toString().equals(payBean.getCardType())){//银行卡类型错误
			throw new PaymentQuickPayException("PC003");
		}
		payBean.setBankCode(cardInfo.get("BANKCODE").toString());
	}

}
