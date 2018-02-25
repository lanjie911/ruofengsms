package com.sms.service.smsmanager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sms.criteria.smsmanager.BlackCriteria;
import com.sms.dao.smsmanager.BlackDao;
import com.sms.entity.smsmanager.Black;
import com.sms.tradeservice.api.service.TradeForDispatchService;
import com.sms.util.HttpUtil;



@Service
public class BlackService {
	
	protected Logger logger = Logger.getLogger(BlackService.class);

	@Autowired 
	private BlackDao blackDao;
	
	@Value("${notifyTradeChangeA}")
    private String notifyTradeChangeA;
	
	@Value("${notifyTradeChangeB}")
    private String notifyTradeChangeB;
	
	@Autowired 
	private TradeForDispatchService tradeForDispatchService;
	
	public List<Black> query(BlackCriteria criteria) {
		return blackDao.query(criteria);
	}


	public Map<String, Object> updateStatus(Long unsubscribeId, Integer beforeStatus,  Integer afterStatus, String operator) {
		Map<String, Object> result = new HashMap<>();
		Integer i =  blackDao.updteStatus(unsubscribeId,beforeStatus,afterStatus,operator);
		if(i==0){
			result.put("success", false);
			result.put("message", "取消退订失败");
		}else{
			result.put("success", true);
			result.put("message", "取消退订成功");
		}
		return result;
	}


	public Map<String, Object> addBlack(Black black, Map<String, Object> result) {
		Black blackTemp = blackDao.getByMobile(black.getUnsubscribeMobile());
		if(blackTemp !=null){
			result.put("success", false);
			result.put("message", "该手机号已是退订用户或黑名单，请更换手机号");
			return result;
		}
		Integer i =  blackDao.insert(black);
		if(i==0){
			result.put("success", false);
			result.put("message", "添加失败");
		}else{
			result.put("success", true);
			result.put("message", "添加成功");
		}
		try {
			result = toNotifyTradeChange(black.getUnsubscribeMobile(),"100",notifyTradeChangeA,result);
		} catch (Exception e) {
			logger.error("通知A服务同步失败");
		}
		try {
			result = toNotifyTradeChange(black.getUnsubscribeMobile(),"100",notifyTradeChangeB,result);
		} catch (Exception e) {
			logger.error("通知B服务同步失败");
		}
		
		return result;
	}


	public Black getBlackById(Long unsubscribeId) {
		return blackDao.getById(unsubscribeId);
	}
	
	public Map<String, Object> editBlack(Black black, Map<String, Object> result) {
		Integer i =  blackDao.update(black);
		if(i==0){
			result.put("success", false);
			result.put("message", "修改失败");
		}else{
			result.put("success", true);
			result.put("message", "修改成功");
		}
		try {
			if(black.getUnsubscribeStatus() ==100){
				result = toNotifyTradeChange(black.getUnsubscribeMobile(),"100",notifyTradeChangeA,result);
			}else{
				result = toNotifyTradeChange(black.getUnsubscribeMobile(),"200",notifyTradeChangeA,result);
			}
		} catch (Exception e) {
			logger.error("通知A服务同步失败");
		}
		try {
			if(black.getUnsubscribeStatus() ==100){
				result = toNotifyTradeChange(black.getUnsubscribeMobile(),"100",notifyTradeChangeB,result);
			}else{
				result = toNotifyTradeChange(black.getUnsubscribeMobile(),"200",notifyTradeChangeB,result);
			}
		} catch (Exception e) {
			logger.error("通知B服务同步失败");
		}
		
		return result;
	}
	
	public Map<String, Object> toNotifyTradeChange(String mobile,String type,String url,Map<String, Object> result) {
		
		String apiResult = HttpUtil.notifyTradeChange("200",mobile,null,type,null,url);
		 JSONObject  apiJson = JSON.parseObject(apiResult);
		 JSONObject jsonObject = new JSONObject();
		 jsonObject.put("mobile", mobile);
		 jsonObject.put("transCode", "200");
		 jsonObject.put("type", type);
		 String resp = tradeForDispatchService.doSendMarketMsgAfterAudit(jsonObject.toJSONString());
		 String code = JSONObject.parseObject(resp).getString("code");
		 if(!apiJson.getString("code").equals("0000") && !code.equals("0000")){
			 result.put("success", false);
			 result.put("message", apiJson.getIntValue("retmsg"));
		 }
		 return result;
	}
	

}
