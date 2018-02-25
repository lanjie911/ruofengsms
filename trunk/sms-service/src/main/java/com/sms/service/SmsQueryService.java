package com.sms.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.sms.criteria.ReservationSendRecordCriteria;
import com.sms.entity.MercAccount;
import com.sms.service.send.MercAccountService;
import com.sms.service.send.ReservationSendRecordService;

/**
 * @author Cao
 * 短信结果查询
 */
@Service
public class SmsQueryService {
	
	
	private static Logger logger = LoggerFactory.getLogger(SmsQueryService.class);
	
	@Autowired
	private MercAccountService mercAccountService;
	
	@Autowired
	private ReservationSendRecordService reservationSendRecordService;
	
	public String resultQuery(JSONObject jsonObject){
		Long accountNo = jsonObject.getLongValue("accountNo");
		String orderNo = jsonObject.getString("orderNo");
		ReservationSendRecordCriteria criteria = new ReservationSendRecordCriteria();
		criteria.setMessageId(orderNo);
		criteria.setAccountNo(accountNo);
//		if(!StringUtils.isBlank(orderNo))
//			criteria.setOrderNo(orderNo);
		List<Map<String,Object>> list = reservationSendRecordService.queryResult(criteria);
		JSONObject j = new JSONObject();
		j.put("code", "0000");
		j.put("msg", "查询成功");
		j.put("list", list);
		return j.toJSONString();
	}

	public String balanceQuery(JSONObject jsonObject){
		Long accountNo = jsonObject.getLongValue("accountNo");
		MercAccount mercAccount = mercAccountService.getById(accountNo);
		JSONObject j = new JSONObject();
		j.put("code", "0000");
		j.put("msg", "查询成功");
		j.put("free", mercAccount.getFreeBalance());
		return j.toJSONString();
	}
}
