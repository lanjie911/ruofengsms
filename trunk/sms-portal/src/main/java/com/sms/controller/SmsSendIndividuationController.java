package com.sms.controller;

import java.text.MessageFormat;
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
@RequestMapping("/smsSendIndividuation")
public class SmsSendIndividuationController {
	
	@Autowired
	private MercAccountService mercAccountService;
	
	@Autowired
	private SmsApplayService smsApplayService;
	
	// 上传文件，最大30M
	private Long maxFileSize = (long) (30*1048576);
	
	@RequestMapping(value="/uploadExcel.ajax",method={RequestMethod.POST})  
    public @ResponseBody Map<String, Object> uploadExcel(@RequestParam(value="excelUpfile") MultipartFile file, 
    		HttpServletRequest request, HttpSession session, HttpServletResponse response) { 
		Map<String, Object> result = new HashMap<String, Object>();
        try {            
            if(null == file)
                throw new Exception("文件不存在！");
            if(file.getSize() > maxFileSize)
            		throw new RuntimeException("文件太大，请上传小于30M的文件");
            
            User u = (User) session.getAttribute("USER");
    		MercAccount records = mercAccountService.getMarketAccount(u.getMerchantId(), 200);
    		if(null == records)
    			throw new RuntimeException("商户账号加载失败");
    		
    		SmsApplay smsApplay = smsApplayService.genSmsApplayIndividuationByExcel(file.getInputStream(), records.getAccountNo(), 
    				200, records.getMerchantNameAbbreviation(), (String)session.getAttribute("USER_NAME"));
            
            result.put("success", true);
			result.put("smsApplayId", smsApplay.getSmsApplayId());
			result.put("batchType", smsApplay.getBatchType());
			result.put("batchNo", String.valueOf(smsApplay.getBatchNo()));
			result.put("excelRowNum", smsApplay.getMobileCount());
			result.put("repeatCount", smsApplay.getRepeatCount());
			result.put("succCount", smsApplay.getSuccCount());
			result.put("excelHiddenContent", smsApplay.getHiddenVal());
			result.put("excelConetnt", smsApplay.getTextAreaVal());
			result.put("headStr", smsApplay.getHeadStr());
			result.put("message", "操作成功");
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", e.getMessage());
		}
        return result;
    }
	
	@ResponseBody
	@RequestMapping(value = "/genPreviewSmsContent.ajax",method={RequestMethod.POST})
	public Map<String, Object> genPreview(@RequestParam("initialHiddenData")String initialHiddenData, 
			@RequestParam("enterSmsContent")String enterSmsContent, @RequestParam("mobileColumn")String mobileColumn, 
			@RequestParam("smsSign")String smsSign,
			HttpServletRequest request, 
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			
			if(null == initialHiddenData || initialHiddenData.length() == 0)
				throw new RuntimeException("处理数据为空");
			
			String[] smsArea = initialHiddenData.split("#NN#");
			StringBuffer smsContent  = new StringBuffer(),smsConetntHidden = new StringBuffer();
			
			// 解析手机号所在数组下标
			int mobileIndex = Integer.valueOf(mobileColumn.substring(mobileColumn.indexOf("{列")+2, mobileColumn.indexOf("}")))-1;
			
			// 解析短信模板
			String smsFormatTemplate = "";
			String temp = enterSmsContent.replace("{列", "#2#");
			String[] sdsd = temp.split("#2#");
			int loopIndex = 0;
			String indexVal = "";
			for(int i = 0; i < sdsd.length; i++) {
				if(sdsd[i].indexOf("}") > 0) {
					indexVal += Integer.valueOf(sdsd[i].substring(0, sdsd[i].indexOf("}"))) - 1 + ",";
					smsFormatTemplate += "{" + loopIndex + sdsd[i].substring(sdsd[i].indexOf("}"), sdsd[i].length());
					loopIndex++;
				}else {
					smsFormatTemplate += sdsd[i];
				}
			}
			
			// 计算变量所在数组下标
			String[] paramIndexVal = indexVal.split(",");			
			for(int sa = 1; sa < smsArea.length; sa++) {
				String[] smsParams = smsArea[sa].split("#TT#");
				String tempSms = replaceSmsTemplate(smsFormatTemplate, smsParams, paramIndexVal, smsSign);
				smsContent.append(smsParams[mobileIndex] + "\t" + tempSms + "\n");
				smsConetntHidden.append(smsParams[mobileIndex] + "#TT#" + tempSms + "#NN#");
			}
			
			result.put("success", true);
			result.put("excelRowNum", smsArea.length - 1);
			result.put("smsContent", smsContent.toString());
			result.put("smsConetntHidden", smsConetntHidden.toString());
			result.put("message", "操作成功");
		} catch (Exception e) {
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
	
	private String replaceSmsTemplate(String smsTemplate, String[] smsParams, String[] paramIndexVal, String smsSign) {
		String smsContent = null;
		String tmpStr = "";
		for(int i = 0; i<paramIndexVal.length; i++) {
			int indexVal = Integer.valueOf(paramIndexVal[i]);
			tmpStr += smsParams[indexVal] + ",";
		}
		
		smsContent = MessageFormat.format(smsTemplate, tmpStr.split(",")) + smsSign;
		return smsContent;
	}
		
}