/* 
 * AccountPayServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年11月11日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.payment.accpay.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.fee.bean.FeeBean;
import com.zlebank.zplatform.fee.exception.TradeFeeException;
import com.zlebank.zplatform.fee.service.TradeFeeService;
import com.zlebank.zplatform.payment.accpay.bean.AccountPayBean;
import com.zlebank.zplatform.payment.accpay.service.AccountPayService;
import com.zlebank.zplatform.payment.commons.bean.ResultBean;
import com.zlebank.zplatform.payment.commons.utils.BeanCopyUtil;
import com.zlebank.zplatform.payment.dao.TxnsLogDAO;
import com.zlebank.zplatform.payment.dao.TxnsOrderinfoDAO;
import com.zlebank.zplatform.payment.enums.TradeStatFlagEnum;
import com.zlebank.zplatform.payment.exception.PaymentAccountPayException;
import com.zlebank.zplatform.payment.exception.PaymentQuickPayException;
import com.zlebank.zplatform.payment.pojo.PojoTxnsLog;
import com.zlebank.zplatform.payment.pojo.PojoTxnsOrderinfo;
import com.zlebank.zplatform.trade.acc.service.TradeAccountingService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年11月11日 上午10:44:04
 * @since 
 */
@Service("accountPayService")
public class AccountPayServiceImpl implements AccountPayService {

	@Autowired
	private TxnsLogDAO txnsLogDAO;
	@Autowired
	private TxnsOrderinfoDAO orderinfoDAO;
	@Autowired
	private TradeFeeService tradeFeeService;
	@Autowired
	private TradeAccountingService tradeAccountingService;
	/**
	 *
	 * @param payBean
	 * @return
	 * @throws PaymentQuickPayException 
	 */
	@Override
	public ResultBean pay(AccountPayBean payBean) throws PaymentAccountPayException, PaymentQuickPayException{
		/**
		 * 业务流程
		 * 1.校验账户支付bean,交易序列号和受理订单号不能同时为空,会员号不能为空
		 * 2.校验订单信息
		 */
		if(StringUtils.isEmpty(payBean.getTn())&&StringUtils.isEmpty(payBean.getTxnseqno())){
			throw new PaymentAccountPayException("PC020");
		}
		if(StringUtils.isEmpty(payBean.getMemberId())){
			throw new PaymentAccountPayException("PC021");
		}
		PojoTxnsLog txnsLog = null;
		PojoTxnsOrderinfo orderinfo = null;
		if(StringUtils.isNotEmpty(payBean.getTxnseqno())){
			txnsLog = txnsLogDAO.getTxnsLogByTxnseqno(payBean.getTxnseqno());
			orderinfo = orderinfoDAO.getOrderinfoByTxnseqno(payBean.getTxnseqno());
		}else{
			orderinfo = orderinfoDAO.getOrderinfoByTN(payBean.getTn());
			txnsLog = txnsLogDAO.getTxnsLogByTxnseqno(orderinfo.getRelatetradetxn());
			payBean.setTxnseqno(orderinfo.getRelatetradetxn());
		}
		if(txnsLog==null||orderinfo==null){
			throw new PaymentAccountPayException("PC004");
		}
		if ("00".equals(orderinfo.getStatus())) {
			throw new PaymentAccountPayException("PC022");
		}
		if ("02".equals(orderinfo.getStatus())) {
			throw new PaymentAccountPayException("PC005");
		}
		if ("04".equals(orderinfo.getStatus())) {
			throw new PaymentAccountPayException("PC006");
		}
		if (!orderinfo.getOrderamt().toString().equals(payBean.getTxnAmt())) {
			throw new PaymentAccountPayException("PC007");
		}
		if ("999999999999999".equals(payBean.getMemberId())) {
			throw new PaymentAccountPayException("PC023");
		}
		
		orderinfoDAO.updateOrderToStartPay(payBean.getTxnseqno());
		txnsLogDAO.updateAccountPay(payBean);
		txnsLogDAO.updateTradeStatFlag(payBean.getTxnseqno(), TradeStatFlagEnum.PAYING);
		//计算交易手续费
		try {
			FeeBean feeBean = new FeeBean();
			feeBean.setBusiCode(txnsLog.getBusicode());
			feeBean.setFeeVer(txnsLog.getFeever());
			feeBean.setTxnAmt(txnsLog.getAmount()+"");
			feeBean.setMerchNo(txnsLog.getAccsecmerno());
			feeBean.setCardType("");
			feeBean.setTxnseqnoOg("");
			feeBean.setTxnseqno(txnsLog.getTxnseqno());
			long fee = tradeFeeService.getCommonFee(feeBean);
			txnsLogDAO.updateTradeFee(txnsLog.getTxnseqno(), fee);
		} catch (TradeFeeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new PaymentAccountPayException("PC028");
		}
		com.zlebank.zplatform.trade.acc.bean.ResultBean accountingForResultBean = tradeAccountingService.accountingForSync(txnsLog.getTxnseqno());
		ResultBean resultBean = BeanCopyUtil.copyBean(ResultBean.class, accountingForResultBean);
		txnsLogDAO.updateAccountPayResult(payBean.getTxnseqno(), resultBean);
		return resultBean;
	}

}
