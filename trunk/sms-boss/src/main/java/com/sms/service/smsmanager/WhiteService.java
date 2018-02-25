package com.sms.service.smsmanager;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sms.criteria.smsmanager.WhiteCriteria;
import com.sms.dao.smsmanager.WhiteDao;
import com.sms.entity.smsmanager.White;
import com.sms.tradeservice.api.service.TradeForDispatchService;
import com.sms.util.HttpUtil;



@Service
public class WhiteService {
	
	protected Logger logger = Logger.getLogger(WhiteService.class);

	@Autowired 
	private WhiteDao whiteDao;
	
	@Value("${notifyTradeChangeA}")
    private String notifyTradeChangeA;
	
	@Value("${notifyTradeChangeB}")
    private String notifyTradeChangeB;
	
	@Autowired 
	private TradeForDispatchService tradeForDispatchService;
	
	public List<White> query(WhiteCriteria criteria) {
		return whiteDao.query(criteria);
	}

	public Map<String, Object> addWhite(String mobile,String remark, Map<String, Object> result) {
		
		Integer i = 0;
		White whiteTemp = whiteDao.getByMobile(mobile);
		if(whiteTemp !=null){
			whiteTemp.setStatus(100);
			whiteTemp.setRemark(remark);
			i = whiteDao.updte(whiteTemp);
		}else{
			White white = new White();
			white.setMobile(mobile);
			white.setRemark(remark);
			i =  whiteDao.insert(white);
		}
		if(i==0){
			result.put("success", false);
			result.put("message", "添加失败");
		}else{
			result.put("success", true);
			result.put("message", "添加成功");
		}
		try {
			result = toNotifyTradeChange(mobile,"100",notifyTradeChangeA,result);
		} catch (Exception e) {
			logger.error("通知A服务同步失败");
		}
		try {
			result = toNotifyTradeChange(mobile,"100",notifyTradeChangeB,result);
		} catch (Exception e) {
			logger.error("通知B服务同步失败");
		}
		
		return result;
	}


	public White getWhiteByMobile(String mobile) {
		return whiteDao.getByMobile(mobile);
	}
	
	public Map<String, Object> editWhite(White white, Map<String, Object> result) {
		Integer i =  whiteDao.update(white);
		if(i==0){
			result.put("success", false);
			result.put("message", "修改失败");
		}else{
			result.put("success", true);
			result.put("message", "修改成功");
		}
		try {
			result = toNotifyTradeChange(white.getMobile(),"100",notifyTradeChangeA,result);
		} catch (Exception e) {
			logger.error("通知A服务同步失败");
		}
		try {
			result = toNotifyTradeChange(white.getMobile(),"100",notifyTradeChangeB,result);
		} catch (Exception e) {
			logger.error("通知B服务同步失败");
		}
		
		return result;
	}

	public Map<String, Object> delete(String mobile, Map<String, Object> result) {
		
		Integer i =  whiteDao.deleteByMobile(mobile);
		if(i==0){
			result.put("success", false);
			result.put("message", "修改失败");
		}else{
			result.put("success", true);
			result.put("message", "修改成功");
		}
		try {
			result = toNotifyTradeChange(mobile,"200",notifyTradeChangeA,result);
		} catch (Exception e) {
			logger.error("通知A服务同步失败");
		}
		try {
			result = toNotifyTradeChange(mobile,"200",notifyTradeChangeB,result);
		} catch (Exception e) {
			logger.error("通知B服务同步失败");
		}
		
		return result;
	}
	
public Map<String, Object> toNotifyTradeChange(String mobile,String type,String url,Map<String, Object> result) {
		
		String apiResult = HttpUtil.notifyTradeChange("300",mobile,null,type,null,url);
		 JSONObject  apiJson = JSON.parseObject(apiResult);
		 JSONObject jsonObject = new JSONObject();
		 jsonObject.put("mobile", mobile);
		 jsonObject.put("type", type);
		 jsonObject.put("transCode", "300");
		 String resp = tradeForDispatchService.doSendMarketMsgAfterAudit(jsonObject.toJSONString());
		 String code = JSONObject.parseObject(resp).getString("code");
		 if(!apiJson.getString("code").equals("0000") && !code.equals("0000")){
			 result.put("success", false);
			 result.put("message", apiJson.getIntValue("retmsg"));
		 }
		 return result;
	}
}
