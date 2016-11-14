/* 
 * RealNameCardServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年11月10日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.payment.realname.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.zlebank.zplatform.cmbc.producer.enums.WithholdingTagsEnum;
import com.zlebank.zplatform.cmbc.producer.interfaces.Producer;
import com.zlebank.zplatform.payment.bean.TradeBean;
import com.zlebank.zplatform.payment.commons.bean.ResultBean;
import com.zlebank.zplatform.payment.commons.utils.BeanCopyUtil;
import com.zlebank.zplatform.payment.exception.PaymentRealNameException;
import com.zlebank.zplatform.payment.realname.bean.RealNameCardBean;
import com.zlebank.zplatform.payment.realname.service.RealNameCardService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年11月10日 上午11:24:55
 * @since 
 */
@Service("realNameCardService")
public class RealNameCardServiceImpl implements RealNameCardService {

	@Autowired
	@Qualifier("cmbcWithholdingProducer")
	private Producer producer_cmbc;
	/**
	 *
	 * @param realNameCardBean
	 * @return
	 * @throws PaymentRealNameException
	 */
	@Override
	public ResultBean realName(RealNameCardBean realNameCardBean)
			throws PaymentRealNameException {
		ResultBean resultBean = null;
		TradeBean tradeBean = new TradeBean();
		tradeBean.setAcctName(realNameCardBean.getCardKeeper());//银行账户名称
		tradeBean.setCardNo(realNameCardBean.getCardNo());//银行卡号
		tradeBean.setMobile(realNameCardBean.getPhone());
		tradeBean.setCardType(realNameCardBean.getCardType());
		tradeBean.setValidthru(realNameCardBean.getExpired());
		tradeBean.setCvv2(realNameCardBean.getCvn2());
		tradeBean.setCertId(realNameCardBean.getCertNo());
		try {
			SendResult sendJsonMessage = producer_cmbc.sendJsonMessage(JSON.toJSONString(tradeBean), WithholdingTagsEnum.REALNAME);
			com.zlebank.zplatform.cmbc.producer.bean.ResultBean source = producer_cmbc.queryReturnResult(sendJsonMessage);
			resultBean = BeanCopyUtil.copyBean(ResultBean.class, source);
			return resultBean;
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
		return null;
	}

}
