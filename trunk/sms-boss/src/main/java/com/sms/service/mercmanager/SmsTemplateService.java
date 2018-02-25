package com.sms.service.mercmanager;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sms.criteria.mercmanager.SmsTemplateCriteria;
import com.sms.dao.mercmanager.SmsTemplateDao;
import com.sms.entity.mercmanager.SmsTemplate;
import com.sms.util.HttpUtil;



@Service
public class SmsTemplateService {

	@Autowired 
	private SmsTemplateDao smsTemplateDao;
	
	@Autowired 
	private HttpUtil httpUtil;
	@Value("${validate_url}")
    private String validateUrl;
	
	public List<SmsTemplate> query(SmsTemplateCriteria criteria) {
		return smsTemplateDao.query(criteria);
	}

	public Map<String, Object> addSmsTemplate(SmsTemplate smsTemplate, Map<String, Object> result) throws UnsupportedEncodingException {
		
		/*String apiResult = httpUtil.httpPostOld(httpUtil.packageRequest("100",smsTemplate.getAccountNo(),smsTemplate.getTemplateContent()),validateUrl);
		JSONObject  apiJson = JSON.parseObject(apiResult);
		 if(!apiJson.getString("code").equals("0000")){
			 result.put("success", false);
			 result.put("message", apiJson.getIntValue("retmsg"));
			return result;
		 }*/
		
		Integer length = smsTemplate.getTemplateContent().replaceAll("\\^", "").replaceAll("\\$", "").replaceAll("\\*\\[\\\\s\\|\\\\S\\]\\*", "").length();
		smsTemplate.setTemplateLength(length);
		
		Integer i =smsTemplateDao.insert(smsTemplate);
		if(i ==0){
			result.put("success", false);
			result.put("message", "插入商短信模板失败");
			return result;
		}
		result.put("success", true);
		result.put("message", "添加成功");
		
		String notifyResult = httpUtil.httpPostOld(httpUtil.packageRequest("400"),validateUrl);
		JSONObject  notifyJson = JSON.parseObject(notifyResult);
		 if(!notifyJson.getString("code").equals("0000")){
			 result.put("success", false);
			 result.put("message", notifyJson.getIntValue("retmsg"));
			return result;
		 }
		return result;
	}
	
	public SmsTemplate getSmsTemplateByTemplateId(Long templateId) {
		return smsTemplateDao.getById(templateId);
	}
	public Map<String, Object> editSmsTemplate(SmsTemplate smsTemplate, Map<String, Object> result) throws UnsupportedEncodingException {
		
		Integer length = smsTemplate.getTemplateContent().replaceAll("\\^", "").replaceAll("\\$", "").replaceAll("\\*\\[\\\\s\\|\\\\S\\]\\*", "").length();
		smsTemplate.setTemplateLength(length);
		
		Integer i =smsTemplateDao.update(smsTemplate);
		if(i ==0){
			result.put("success", false);
			result.put("message", "更新短信模板失败");
			return result;
		}
		result.put("success", true);
		result.put("message", "添加成功");
		
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
