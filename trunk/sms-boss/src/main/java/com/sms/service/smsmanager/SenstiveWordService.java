package com.sms.service.smsmanager;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sms.criteria.smsmanager.SenstiveWordCriteria;
import com.sms.dao.smsmanager.SenstiveWordDao;
import com.sms.entity.smsmanager.SenstiveWord;
import com.sms.util.HttpUtil;



@Service
public class SenstiveWordService {

	@Autowired 
	private SenstiveWordDao senstiveWordDao;
	
	@Autowired 
	private HttpUtil httpUtil;
	
	@Value("${validate_url}")
    private String validateUrl;
	
	public List<SenstiveWord> query(SenstiveWordCriteria criteria) {
		return senstiveWordDao.query(criteria);
	}

	public Map<String, Object> addSens( SenstiveWord senstiveWord, Map<String, Object> result) throws UnsupportedEncodingException {
		Integer i =  senstiveWordDao.insert(senstiveWord);
		if(i==0){
			result.put("success", false);
			result.put("message", "添加失败");
		}else{
			result.put("success", true);
			result.put("message", "添加成功");
		}
		
		String notifyResult = httpUtil.httpPostOld(httpUtil.packageRequest("400"),validateUrl);
		JSONObject  notifyJson = JSON.parseObject(notifyResult);
		 if(!notifyJson.getString("code").equals("0000")){
			 result.put("success", false);
			 result.put("message", notifyJson.getIntValue("retmsg"));
			return result;
		 }
		return result;
	}

	public Map<String, Object> remove(Long wordId, Map<String, Object> result) throws UnsupportedEncodingException {
		Integer i =  senstiveWordDao.delete(wordId);
		if(i==0){
			result.put("success", false);
			result.put("message", "删除失败");
		}else{
			result.put("success", true);
			result.put("message", "删除成功");
		}
		String notifyResult = httpUtil.httpPostOld(httpUtil.packageRequest("400"),validateUrl);
		JSONObject  notifyJson = JSON.parseObject(notifyResult);
		 if(!notifyJson.getString("code").equals("0000")){
			 result.put("success", false);
			 result.put("message", notifyJson.getIntValue("retmsg"));
			return result;
		 }
		return result;
	}


}
