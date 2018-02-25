package com.sms.service.smsmanager;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sms.criteria.smsmanager.IpCriteria;
import com.sms.dao.smsmanager.IpDao;
import com.sms.entity.smsmanager.Ip;
import com.sms.util.HttpUtil;



@Service
public class IpService {
	
	protected Logger logger = Logger.getLogger(IpService.class);

	@Autowired 
	private IpDao ipDao;
	
	@Value("${notifyTradeChangeA}")
    private String notifyTradeChangeA;
	
	@Value("${notifyTradeChangeB}")
    private String notifyTradeChangeB;
	
	public List<Ip> query(IpCriteria criteria) {
		return ipDao.query(criteria);
	}

	public Map<String, Object> addIp(String ipAddress,Map<String, Object> result) {
		
		Integer i = 0;
		Ip ipTemp = ipDao.getByIpAddr(ipAddress);
		if(ipTemp !=null){
			result.put("success", false);
			result.put("message", "此ip已存在");
			return result;
		}else{
			Ip ip = new Ip();
			ip.setIpAddress(ipAddress);
			i =  ipDao.insert(ip);
		}
		if(i==0){
			result.put("success", false);
			result.put("message", "添加失败");
		}else{
			result.put("success", true);
			result.put("message", "添加成功");
		}
		try {
			result = toNotifyTradeChange(ipAddress,"100",notifyTradeChangeA,result);
		} catch (Exception e) {
			logger.error("通知A服务同步失败");
		}
		
		try {
			result = toNotifyTradeChange(ipAddress,"100",notifyTradeChangeB,result);
		} catch (Exception e) {
			logger.error("通知B服务同步失败");
		}
		return result;
	}
	
	public Map<String, Object> delete(String ipAddress, Map<String, Object> result) {
		
		Integer i =  ipDao.deleteByIpAddr(ipAddress);
		if(i==0){
			result.put("success", false);
			result.put("message", "修改失败");
		}else{
			result.put("success", true);
			result.put("message", "修改成功");
		}
		try {
			result = toNotifyTradeChange(ipAddress,"200",notifyTradeChangeA,result);
		} catch (Exception e) {
			logger.error("通知A服务同步失败");
		}
		try {
			result = toNotifyTradeChange(ipAddress,"200",notifyTradeChangeB,result);
		} catch (Exception e) {
			logger.error("通知B服务同步失败");
		}
		
		return result;
	}
	
public Map<String, Object> toNotifyTradeChange(String ipAddress,String type,String url,Map<String, Object> result) {
		
		String apiResult = HttpUtil.notifyTradeChange("400",null,ipAddress,type,null,url);
		 JSONObject  apiJson = JSON.parseObject(apiResult);
		 if(!apiJson.getString("code").equals("0000")){
			 result.put("success", false);
			 result.put("message", apiJson.getIntValue("retmsg"));
		 }
		 return result;
	}
}
