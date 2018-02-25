package com.sms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sms.criteria.mercmanager.SmsTemplateCriteria;
import com.sms.entity.mercmanager.SmsTemplate;
import com.sms.service.mercmanager.SmsTemplateService;
import com.sms.util.ErrorMessageUtil;


@Controller
@RequestMapping("/smstemplate")
public class SmsTemplateController {

	@Autowired
	private SmsTemplateService smsTemplateService;
	
	
	@ResponseBody
	@RequestMapping("/query.ajax")
	public Map<String, Object> query(SmsTemplateCriteria criteria, HttpSession session) {
		 
		List<SmsTemplate> records = smsTemplateService.query(criteria);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", criteria.getTotalCount());
		result.put("rows", records);

		return result;
	}
	
	@ResponseBody
	@RequestMapping("/addSmsTemplate.ajax")
	public  Map<String, Object> addSmsTemplate(@Valid SmsTemplate smsTemplate, BindingResult error,HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		
		if(error.hasErrors()){
			result.put("success", false);
			result.put("message",ErrorMessageUtil.getErrorMessage(error));
			return result;
		}
		try {
			smsTemplate.setOperator((String) session.getAttribute("USER_NAME"));
			result=smsTemplateService.addSmsTemplate(smsTemplate,result);
		} catch(Exception e) {
			result.put("success", false);
			result.put("message", e.toString());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/getSmsTemplateByTemplateId.ajax")
	public  SmsTemplate getSmsTemplateByTemplateId(@RequestParam("templateId") Long templateId, HttpSession session) {
		return smsTemplateService.getSmsTemplateByTemplateId(templateId);
	}
	
	@ResponseBody
	@RequestMapping("/editSmsTemplate.ajax")
	public Map<String, Object> editSmsTemplate(@Valid SmsTemplate smsTemplate,  BindingResult error,HttpSession session){
		Map<String, Object> result = new HashMap<String, Object>(2);
		
		if(error.hasErrors()){
			result.put("success", false);
			result.put("message", ErrorMessageUtil.getErrorMessage(error));
			return result;
		}
		try {
			smsTemplate.setOperator((String) session.getAttribute("USER_NAME"));
			result=smsTemplateService.editSmsTemplate(smsTemplate,result);
			result.put("success", true);
			result.put("message", "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("message", "修改失败");
		}
		return result;
	}
	 
}
	
