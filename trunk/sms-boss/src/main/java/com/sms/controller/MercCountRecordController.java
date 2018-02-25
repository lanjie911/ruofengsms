package com.sms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sms.criteria.countrecord.MercCountRecordCriteria;
import com.sms.entity.merccountrecord.MercCountRecord;
import com.sms.service.merccountrecord.MercCountRecordService;


@Controller
@RequestMapping("/merccountrecord")
public class MercCountRecordController {

	@Autowired
	private MercCountRecordService mercCountRecordService;
	
	
	@ResponseBody
	@RequestMapping("/query.ajax")
	public Map<String, Object> query(MercCountRecordCriteria criteria, HttpSession session) {
		 
		List<MercCountRecord> records = mercCountRecordService.query(criteria);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", criteria.getTotalCount());
		result.put("rows", records);

		return result;
	}
	
	/*@ResponseBody
	@RequestMapping("/generateAccountNo.ajax")
	public Long generateAccountNo() {
		return serialNumberService.generateAccountNo("s_merchant_account");
	}
	
	
	@ResponseBody
	@RequestMapping("/addMercAccount.ajax")
	public  Map<String, Object> addMercAccount(@Valid MercAccount mercAccount, BindingResult error,HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		
		if(error.hasErrors()){
			result.put("success", false);
			result.put("message",ErrorMessageUtil.getErrorMessage(error));
			return result;
		}
		try {
			mercAccount.setOperator((String) session.getAttribute("USER_NAME"));
			result=mercAccountService.addMercAccount(mercAccount,result);
		} catch(Exception e) {
			result.put("success", false);
			result.put("message", e.toString());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/getMercAccountByAccountNo.ajax")
	public  MercAccount getMercAccountByAccountNo(@RequestParam("mercAccountNo") Long mercAccountNo, HttpSession session) {
		return mercAccountService.getMercAccountByAccountNo(mercAccountNo);
	}
	
	@ResponseBody
	@RequestMapping("/editMercAccount.ajax")
	public Map<String, Object> editMercAccount(@Valid MercAccount mercAccount,  BindingResult error,HttpSession session){
		Map<String, Object> result = new HashMap<String, Object>(2);
		
		if(error.hasErrors()){
			result.put("success", false);
			result.put("message", ErrorMessageUtil.getErrorMessage(error));
			return result;
		}
		try {
			mercAccount.setOperator((String) session.getAttribute("USER_NAME"));
			result=mercAccountService.editMercAccount(mercAccount,result);
			result.put("success", true);
			result.put("message", "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("message", "修改失败");
		}
		return result;
	}*/
	
}	
