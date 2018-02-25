package com.sms.controller;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sms.constant.Constants;
import com.sms.entity.manager.User;
import com.sms.entity.mercmanager.MercAccount;
import com.sms.entity.smsupload.SmsBatchUpload;
import com.sms.service.idfactory.SerialIdFactory;
import com.sms.service.mercmanager.MercAccountService;
import com.sms.service.smsupload.SmsDetailUploadService;
import com.sms.service.trademanager.SmsService;
import com.sms.util.ExcelUtil;

@Controller
@RequestMapping("/excelController")
public class ExcelController {
	
	@Autowired
	private SmsDetailUploadService smsDetailUploadService;
	
	@Autowired
	private MercAccountService mercAccountService;
	
	@Autowired
	private SerialIdFactory idFactory;
	
	@Autowired
	private SmsService smsService;
	
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
            
            ExcelUtil newExcelUtil = new ExcelUtil();
            newExcelUtil.processExcel(file.getInputStream());
            String[] excelArray = newExcelUtil.getExcelArray();
            String textAreaVal = newExcelUtil.getTextAreaVal();
            String hiddenVal = newExcelUtil.getHiddenVal();
            String headStr = newExcelUtil.getHeadStr();
            
            String batchNo = (String) idFactory.generate("200");
            SmsBatchUpload sc = new SmsBatchUpload();
    		sc.setAccountNo(records.getAccountNo());
    		sc.setAccountType(200);
    		sc.setMobileCount(excelArray.length);
    		sc.setOperator((String)session.getAttribute("USER_NAME"));
    		sc.setBatchType(200);
    		sc.setBatchNo(batchNo);
    		sc.setMerchantNameAbbreviation(records.getMerchantNameAbbreviation());
            
            Integer smsUploadId = smsDetailUploadService.genSmsatchUpload(sc);
            if(null == smsUploadId)
            	throw new RuntimeException("生成批次号失败，请重新选择文件上传");
            smsService.insertUploadDetail(excelArray, sc);
            
            result.put("success", true);
			result.put("smsUploadId", smsUploadId);
			result.put("batchNo", batchNo);
			result.put("excelRowNum", excelArray.length);
			result.put("excelHiddenContent", hiddenVal);
			result.put("excelConetnt", textAreaVal);
			result.put("headStr", headStr);
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
	
	@RequestMapping(value="/uploadExcelNormal.ajax",method={RequestMethod.POST})  
    public @ResponseBody Map<String, Object> uploadExcelNormal(@RequestParam(value="excelUpfile") MultipartFile file, 
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
            
            ExcelUtil newExcelUtil = new ExcelUtil();
            newExcelUtil.processExcelNormal(file.getInputStream());
            String[] excelArray = newExcelUtil.getExcelArray();
            String textAreaVal = newExcelUtil.getTextAreaVal();
            
            String batchNo =(String) idFactory.generate(Constants.CHANNEL_NAME);
            SmsBatchUpload sc = new SmsBatchUpload();
    		sc.setAccountNo(records.getAccountNo());
    		sc.setAccountType(200);
    		sc.setMobileCount(excelArray.length);
    		sc.setOperator((String)session.getAttribute("USER_NAME"));
    		sc.setBatchType(100);
    		sc.setBatchNo(batchNo);
    		sc.setMerchantNameAbbreviation(records.getMerchantNameAbbreviation());
    		
            Integer smsUploadId = smsDetailUploadService.genSmsatchUpload(sc);
            if(null == smsUploadId)
            	throw new RuntimeException("生成批次号失败，请重新选择文件上传");
            smsService.insertUploadDetailNormal(excelArray, sc);
            
            result.put("success", true);
			result.put("smsUploadId", smsUploadId);
			result.put("batchNo", batchNo);
			result.put("excelRowNum", excelArray.length);
			result.put("excelConetnt", textAreaVal);
			result.put("message", "操作成功");
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
	
	public static void main(String[] args) {
//		String sms = "员工{列3},你在{列4}工资为{列6}元,请注意查收！";
//		String temp = sms.replace("{列", "#2#");
//		String[] sdsd = temp.split("#2#");
//		String smsFormatTemplate = "";
//		int loopIndex = 0;
//		String indexVal = "";
//		for(int i = 0; i< sdsd.length; i++) {
//			if(sdsd[i].indexOf("}") > 0) {
//				indexVal += sdsd[i].substring(0, sdsd[i].indexOf("}")) + ",";
//				smsFormatTemplate += "{" + loopIndex + sdsd[i].substring(sdsd[i].indexOf("}"), sdsd[i].length());
//				loopIndex++;
//			}else {
//				smsFormatTemplate += sdsd[i];
//			}
//		}
//		System.out.println("模板："+ smsFormatTemplate);
//		String[] paramVal = indexVal.split(",");
//		System.out.println("数组长度：" + paramVal.length);
//		for(int k = 0; k<paramVal.length; k++) {
//			System.out.println("数组下标："+ paramVal[k]);
//		}
		
		String mobileColumn = "{列1}";
		System.out.println(mobileColumn.substring(mobileColumn.indexOf("{列")+2, mobileColumn.indexOf("}")));
//		int mobileIndex = Integer.valueOf(mobileColumn.substring(mobileColumn.indexOf("{列"), mobileColumn.indexOf("}"))+1);
//		System.out.println(sms.replace("{列", "*#"));
//		
//		String sss = "员工{0},你在{1}工资为{2}元,请注意查收！";
//		String formatss = MessageFormat.format(sss, "王宝强", "4月", "29382.12");
//		System.out.println(formatss);
	}
		
}