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

import com.sms.criteria.mercmanager.MercInfoCriteria;
import com.sms.entity.mercmanager.MercInfo;
import com.sms.service.mercmanager.MercInfoService;
import com.sms.util.ErrorMessageUtil;


@Controller
@RequestMapping("/mercinfo")
public class MercInfoController {

	@Autowired
	private MercInfoService mercInfoService;
	
	
	@ResponseBody
	@RequestMapping("/query.ajax")
	public Map<String, Object> query(MercInfoCriteria criteria, HttpSession session) {
		
		List<MercInfo> records = mercInfoService.query(criteria);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", criteria.getTotalCount());
		result.put("rows", records);

		return result;
	}
	
	
	@ResponseBody
	@RequestMapping("/addMerc.ajax")
	public  Map<String, Object> addMerc(@Valid MercInfo mercInfo, BindingResult error,HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		
		if(error.hasErrors()){
			result.put("success", false);
			result.put("message",ErrorMessageUtil.getErrorMessage(error));
			return result;
		}
		try {
			mercInfo.setOperator((String) session.getAttribute("USER_NAME"));
			result=mercInfoService.addMerc(mercInfo,result);
		} catch(Exception e) {
			result.put("success", false);
			result.put("message", e.toString());
		}
		return result;
	}
	
	 @ResponseBody
	@RequestMapping("/editMerc.ajax")
	public Map<String, Object> editMerc(@Valid MercInfo mercInfo,  BindingResult error,HttpSession session){
		Map<String, Object> result = new HashMap<String, Object>(2);
		
		if(error.hasErrors()){
			result.put("success", false);
			result.put("message", ErrorMessageUtil.getErrorMessage(error));
			return result;
		}
		try {
			mercInfo.setOperator((String) session.getAttribute("USER_NAME"));
			result=mercInfoService.editMerc(mercInfo,result);
			result.put("success", true);
			result.put("message", "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("message", "修改失败");
		}
		return result;
	}
	 
	@ResponseBody
	@RequestMapping("/getMercInfoByMercId.ajax")
	public  MercInfo getMercInfoByMercId(@RequestParam("merchantId") Long merchantId, HttpSession session) {
		return mercInfoService.getMercInfoByMercId(merchantId);
	}
	
	@ResponseBody
	@RequestMapping("/getMercInfoCount.ajax")
	public  Integer getMercInfoCount( HttpSession session) {
		return mercInfoService.getMercInfoCount();
	}
}
	
