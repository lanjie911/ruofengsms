package com.sms.service.smsmanager;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sms.criteria.smsmanager.SmsCriteria;
import com.sms.dao.smsmanager.SmsAuditDao;
import com.sms.dao.smsmanager.SmsDao;
import com.sms.entity.smsmanager.Sms;
import com.sms.entity.smsmanager.SmsAudit;
import com.sms.tradeservice.api.service.TradeExcuteService;
import com.sms.util.DatetimeUtil;
import com.sms.util.HttpUtil;

@Service
public class SmsService {

	@Autowired 
	private SmsDao smsDao;
	@Autowired 
	private SmsAuditDao smsAuditDao;
	
	@Autowired 
	private TradeExcuteService tradeExcuteService;
	
	@Autowired 
	private HttpUtil httpUtil;
	
	@Value("${validate_url}")
    private String validateUrl;
	
	public List<Sms> query(SmsCriteria criteria) {
		return smsDao.query(criteria);
	}

	@Transactional
	public Map<String, Object> addSms(Sms sms, Map<String, Object> result,String operator) throws UnsupportedEncodingException {
		String apiResult = httpUtil.httpPostOld(httpUtil.packageRequest("100",sms.getAccountNo(),sms.getContent()),validateUrl);
		 JSONObject  apiJson = JSON.parseObject(apiResult);
		 if(!apiJson.getString("code").equals("0000")){
			 result.put("success", false);
			 result.put("message", apiJson.getIntValue("retmsg"));
			return result;
		 }
		 
		if(sms.getAccountType() == 200 && sms.getSendSmsType().equals("T0001")){
			 SmsAudit smsAudit = new SmsAudit();
			 smsAudit.setAccountNo(sms.getAccountNo());
			 smsAudit.setMerchantNameAbbreviation(sms.getMerchantNameAbbreviation());
			 smsAudit.setAccountType(200);
			 smsAudit.setOrderFlag(Integer.valueOf(sms.getAppointmentFlag()));
			 smsAudit.setReservationDatetime(sms.getReservationDatetime());
			 smsAudit.setMobile(sms.getMobile());
			 smsAudit.setSmsContent(sms.getContent());
			 smsAudit.setSignTip(sms.getSignTip());
			 smsAudit.setSmsCount(sms.getContent().length()+sms.getSignTip().length());
			 if(sms.getSendAuditFlag() == 100){
				 smsAudit.setAuditingStatus(100);
			 }else{
				 smsAudit.setAuditingStatus(200);
			 }
			 Integer sendNum = 1;
			 if(70 < sms.getContent().length())																//计算短信条数
				sendNum = (int) (Math.ceil(sms.getContent().length()/68d));
			 smsAudit.setSmsAccountNum(sendNum);
			 smsAudit.setSmsUnitCount(1);
			 smsAudit.setBatchNo(DatetimeUtil.getCurrentDateTime("yyyyMMddHHmmss"));
			 smsAudit.setRegistrars(operator);
			
			 String[] mobiles = sms.getMobile().split("@");
			 Integer sumCost =  (int) Math.floor(mobiles.length * sendNum * sms.getCostQuantity()/100);
			 
			 String[] strs = Arrays.copyOfRange(mobiles, sumCost, mobiles.length);
			 smsAudit.setCostTip(100);
			 Integer i =  smsAuditDao.insertBatch(strs,smsAudit);
			 
			 smsAudit.setCostTip(200);
			 String[] nosends = Arrays.copyOfRange(mobiles, 0, sumCost);
			 if(nosends.length >0){
				 i +=  smsAuditDao.insertBatch(nosends,smsAudit);
			 }
			 
			if(i==0){
				result.put("success", false);
				result.put("message", "提交审核失败");
			}else{
				result.put("success", true);
				result.put("message", "提交审核成功");
			}
			return result;
		}else{
			 JSONObject jsObject = new JSONObject();
			 jsObject.put("accountNo", sms.getAccountNo());
			 jsObject.put("mobile", sms.getMobile());
			 jsObject.put("content", sms.getContent());
			 jsObject.put("signTip",sms.getSignTip());
			 jsObject.put("messageId", DatetimeUtil.getCurrentDateTime("yyyyMMddHHmmss"));
		  
			if(sms.getAppointmentFlag().equals("100")){
				jsObject.put("reservationDatetime", sms.getReservationDatetime());
				String jresponse =  tradeExcuteService.sendMsgMarket(jsObject.toJSONString());
				JSONObject j = JSON.parseObject(jresponse);
				if(!j.getString("code").equals("0000")){
					result.put("success", false);
					result.put("message", j.get("msg"));
				}else{
					result.put("success", true);
					result.put("message", "添加成功");
				}
			}else{
				String jresponse =  tradeExcuteService.sendMsg(jsObject.toJSONString());
				JSONObject j = JSON.parseObject(jresponse);
				if(!j.getString("code").equals("0000")){
					result.put("success", false);
					result.put("message", j.get("msg"));
				}else{
					result.put("success", true);
					result.put("message", "添加成功");
				}
			}
			return result;
		}
	}
}