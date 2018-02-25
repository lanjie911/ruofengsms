package com.sms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sms.criteria.mercmanager.CusManagerCriteria;
import com.sms.entity.mercmanager.CusManager;
import com.sms.service.mercmanager.CusManagerService;
import com.sms.util.ErrorMessageUtil;


@Controller
@RequestMapping("/cusmanager")
public class CusManagerController {

	@Autowired
	private CusManagerService cusManagerService;
	
	@ResponseBody
	@RequestMapping("/query.ajax")
	public Map<String, Object> query(CusManagerCriteria criteria, HttpSession session) {
		 
		List<CusManager> records = cusManagerService.query(criteria);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", criteria.getTotalCount());
		result.put("rows", records);

		return result;
	}
	
	@ResponseBody
	@RequestMapping("/querymerc.ajax")
	public Map<String, Object> querymerc(CusManagerCriteria criteria, HttpSession session) {
		 
		List<CusManager> records = cusManagerService.querymerc(criteria);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", criteria.getTotalCount());
		result.put("rows", records);

		return result;
	}
	
	@ResponseBody
	@RequestMapping("/addCusManager.ajax")
	public  Map<String, Object> addCusManager(@Valid CusManager cusManager, BindingResult error,HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		
		if(error.hasErrors()){
			result.put("success", false);
			result.put("message",ErrorMessageUtil.getErrorMessage(error));
			return result;
		}
		try {
			result=cusManagerService.addCusManager(cusManager,result);
		} catch(Exception e) {
			result.put("success", false);
			result.put("message", e.toString());
		}
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping("/getCusmanagerById.ajax")
	public  CusManager getCusmanagerById(@RequestParam("cusManagerId") Long cusManagerId, HttpSession session) {
		return cusManagerService.getCusmanagerById(cusManagerId);
	}
	
	@ResponseBody
	@RequestMapping("/getCusmanagers.ajax")
	public List<CusManager> getCusmanagers() {
		return cusManagerService.getCusmanagers();
	}
	
	@ResponseBody
	@RequestMapping("/editCusmanager.ajax")
	public Map<String, Object> editCusmanager(@Valid CusManager cusManager,  BindingResult error,HttpSession session){
		Map<String, Object> result = new HashMap<String, Object>(2);
		
		if(error.hasErrors()){
			result.put("success", false);
			result.put("message", ErrorMessageUtil.getErrorMessage(error));
			return result;
		}
		try {
			result=cusManagerService.editCusmanager(cusManager,result);
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
	@RequestMapping("/delete.ajax")
	public Map<String, Object> delete(@Param("cusManagerId") Long cusManagerId,HttpSession session) {
		 
		Map<String, Object> result = cusManagerService.delete(cusManagerId);

		return result;
	}
}	
