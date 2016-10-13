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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.payment.commons.bean.ResultBean;
import com.zlebank.zplatform.payment.commons.dao.TxnsLogDAO;
import com.zlebank.zplatform.payment.commons.dao.TxnsOrderinfoDAO;
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
			throw new PaymentQuickPayException();
		}
		if("02".equals(orderinfo.getStatus())){//订单支付中
			throw new PaymentQuickPayException();
		}
		if("04".equals(orderinfo.getStatus())){//订单过期
			throw new PaymentQuickPayException();
		}
		PojoTxnsLog txnsLog = txnsLogDAO.getTxnsLogByTxnseqno(orderinfo.getRelatetradetxn());
		if(txnsLog==null){
			throw new PaymentQuickPayException();
		}
		
		
		String channelCode = routeConfigService.getTradeChannel(DateUtil.getCurrentDateTime(), orderinfo.getOrderamt().toString(), orderinfo.getMemberid(), txnsLog.getBusicode(), payBean.getCardNo(), txnsLog.getRoutver());
		
		
		txnsLogDAO.riskTradeControl(txnsLog.getTxnseqno(),txnsLog.getAccfirmerno(),txnsLog.getAccsecmerno(),txnsLog.getAccmemberid(),txnsLog.getBusicode(),txnsLog.getAmount()+"",payBean.getCardType(),payBean.getCardNo());
		
		txnsLogDAO.initretMsg(txnsLog.getTxnseqno());
		txnsLogDAO.updateBankCardInfo(txnsLog.getTxnseqno(), payBean);
		txnsOrderinfoDAO.updateOrderToStartPay(txnsLog.getTxnseqno());
		
		
		
		return null;
	}
	
	private void checkPayment(PayBean payBean) throws PaymentQuickPayException{
		PayCheckBean copyBean = BeanCopyUtil.copyBean(PayCheckBean.class, payBean);
		ResultBean resultBean = ValidateLocator.validateBeans(copyBean);
		if(!resultBean.isResultBool()){//支付信息非空，长度检查出现异常，非法数据
			throw new PaymentQuickPayException();
		}
		Map<String, Object> cardInfo = routeConfigService.getCardInfo(payBean.getCardNo());
		if(cardInfo==null){//银行卡信息错误
			throw new PaymentQuickPayException();
		}
		if(!cardInfo.get("TYPE").equals(payBean.getCardType())){//银行卡类型错误
			throw new PaymentQuickPayException();
		}
		payBean.setBankCode(cardInfo.get("BANKCODE").toString());
	}

}
