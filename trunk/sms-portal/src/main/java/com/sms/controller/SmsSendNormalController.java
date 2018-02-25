package com.sms.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sms.entity.manager.User;
import com.sms.entity.mercmanager.MercAccount;
import com.sms.entity.smsupload.SmsApplay;
import com.sms.service.mercmanager.MercAccountService;
import com.sms.service.smsupload.SmsApplayService;

@Controller
@RequestMapping("/smsSendNormal")
public class SmsSendNormalController {
	private Long maxFileSize = (long) (30*1048576);		// 上传文件，最大30M
	
	@Autowired
	private SmsApplayService smsApplayService;
	
	@Autowired
	private MercAccountService mercAccountService;
	
	@RequestMapping(value="/uploadExcel.ajax", method={RequestMethod.POST})  
    public @ResponseBody Map<String, Object> uploadExcel(@RequestParam(value="excelUpfile") MultipartFile file, 
    		HttpServletRequest request, HttpSession session, HttpServletResponse response) { 
		Map<String, Object> result = new HashMap<String, Object>();
        try {
            if(null == file)
                throw new Exception("文件为空！");
            if(file.getSize() > maxFileSize)
            	throw new RuntimeException("文件太大，请上传小于30M的文件");
            
            User u = (User) session.getAttribute("USER");
    		MercAccount records = mercAccountService.getMarketAccount(u.getMerchantId(), 200);
    		if(null == records)
    			throw new RuntimeException("商户账号加载失败");
    		
    		SmsApplay smsApplay = smsApplayService.genSmsApplayByExcel(file.getInputStream(), records.getAccountNo(), 200, 
    				records.getMerchantNameAbbreviation(), (String)session.getAttribute("USER_NAME"));
            
			result.put("smsApplayId", smsApplay.getSmsApplayId());
			result.put("batchType", smsApplay.getBatchType());
			result.put("batchNo", String.valueOf(smsApplay.getBatchNo()));
			result.put("excelRowNum", smsApplay.getMobileCount());
			result.put("repeatCount", smsApplay.getRepeatCount());
			result.put("succCount", smsApplay.getSuccCount());
			result.put("errorCount", smsApplay.getOutlierCount());
			result.put("excelConetnt", smsApplay.getTextAreaVal());
			
			result.put("success", true);
			result.put("message", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("message", e.getMessage());
		}
        return result;
    }
	
	@ResponseBody
	@RequestMapping("/doSendSms.ajax")
	public Map<String, Object> doSendSms(@Valid SmsApplay smsApplay, BindingResult error, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		try {
			
			if(null == smsApplay)
				throw new RuntimeException("数据加载异常，请重新进入该页面");
			smsApplayService.doSendSms(smsApplay);
			result.put("success", true);
			result.put("message", "操作成功，短信发送已受理");
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", e.getMessage());
		}
		return result;
	}
}