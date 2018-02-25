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

import com.sms.criteria.mercmanager.MercAccountCriteria;
import com.sms.entity.manager.User;
import com.sms.entity.mercmanager.MercAccount;
import com.sms.service.mercmanager.MercAccountService;
import com.sms.util.ErrorMessageUtil;


@Controller
@RequestMapping("/mercaccount")
public class MercAccountController {

	@Autowired
	private MercAccountService mercAccountService;
	
	@ResponseBody
	@RequestMapping("/queryAccountNo.ajax")
	public List<MercAccount> query(HttpSession session) {
		
		MercAccountCriteria criteria = new MercAccountCriteria();
		User u = (User) session.getAttribute("USER");
		criteria.setMerchantId(u.getMerchantId());
		criteria.setPaging(false);
		List<MercAccount> records = mercAccountService.query(criteria);
		
		return records;
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
	}
	
	@ResponseBody
	@RequestMapping("/queryMarketAccount.ajax")
	public MercAccount queryMarketAccount(HttpSession session) {
		User u = (User) session.getAttribute("USER");
		MercAccount records = mercAccountService.getMarketAccount(u.getMerchantId(),200);
		return records;
	}
	
}	
