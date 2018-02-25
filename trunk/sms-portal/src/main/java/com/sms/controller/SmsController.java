package com.sms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sms.criteria.trademanager.SmsCriteria;
import com.sms.dao.smsupload.SmsBatchUploadDao;
import com.sms.dao.smsupload.SmsDetailUploadDao;
import com.sms.entity.manager.User;
import com.sms.entity.smsupload.SmsBatchUpload;
import com.sms.entity.smsupload.SmsDetailUpload;
import com.sms.entity.trademanager.Sms;
import com.sms.service.smsupload.SmsDetailUploadService;
import com.sms.service.trademanager.SmsService;

@Controller
@RequestMapping("/sms")
public class SmsController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SmsService smsService;
	@Autowired
	private SmsDetailUploadService smsDetailUploadService;
	
	@Autowired
	private SmsDetailUploadDao smsDetailUploadDao;
	
	@Autowired
	private SmsBatchUploadDao smsBatchUploadDao;

	@ResponseBody
	@RequestMapping("/query.ajax")
	public Map<String, Object> query(SmsCriteria criteria, HttpSession session) {
		User u = (User) session.getAttribute("USER");
		criteria.setMerchantId(u.getMerchantId());
		List<Sms> records = smsService.query(criteria);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", criteria.getTotalCount());
		result.put("rows", records);

		return result;
	}

	@ResponseBody
	@RequestMapping("/addSms.ajax")
	public Map<String, Object> addSms(@Valid SmsBatchUpload smsBatchUpload, BindingResult error, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>(2);

		try {
			
			if(null == smsBatchUpload)
				throw new RuntimeException("数据加载异常，请重新进入该页面");
			
			smsService.addSmsNormal(smsBatchUpload);
			result.put("success", true);
			result.put("message", "操作成功，短信发送已受理");
		} catch (Exception e) {
			logger.error("SmsController.addSms-Exception:", e);
			result.put("success", false);
			result.put("message", e.getMessage());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/smsIndividuation.ajax", method = { RequestMethod.POST })
	public Map<String, Object> smsIndividuation(@Valid SmsBatchUpload smsBatchUpload, BindingResult error, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		try {
//			smsDetailUploadService.smsIndividuation(smsBatchUpload);
			smsService.smsIndividuation(smsBatchUpload);
			result.put("success", true);
			result.put("message", "操作成功，短信发送已受理");
		} catch (Exception e) {
			logger.error("SmsController.smsIndividuation-Exception:", e);
			result.put("success", false);
			result.put("message", e.getMessage());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/genSmsbatchUpload.ajax")
	public Map<String, Object> genSmsbatchUpload(@Valid Sms sms, BindingResult error, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		try {

			/*sms.setOperator((String) session.getAttribute("USER_NAME"));
			sms.setMessageId((String) idFactory.generate(Constants.CHANNEL_NAME));

			User u = (User) session.getAttribute("USER");
			MercAccount records = mercAccountService.getMarketAccount(u.getMerchantId(), 200);

			SmsBatchUpload sc = new SmsBatchUpload();
			sc.setAccountNo(records.getAccountNo());
			sc.setAccountType(200);
			sc.setMobileCount(500000);
			sc.setOperator((String) session.getAttribute("USER_NAME"));
			sc.setBatchType(200);
			smsDetailUploadService.genSmsatchUpload(sc);*/
			
			List<SmsDetailUpload> list = new ArrayList<SmsDetailUpload>();
			SmsDetailUpload newSmsDetailUpload = new SmsDetailUpload();
			newSmsDetailUpload.setAccountNo(1000157l);
			newSmsDetailUpload.setUploadContent("13811300001#TT#20040818005#TT#刘世芳#TT#3月#TT#工资#TT#8154#TT#");
//			newSmsDetailUpload.setSmsUploadId(4);
			list.add(newSmsDetailUpload);
			list.add(newSmsDetailUpload);
			list.add(newSmsDetailUpload);
			
			smsDetailUploadDao.insertSmsDetailUploadBatch(list);
			
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", e.toString());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/test.ajax")
	public Map<String, Object> test(HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		try {
//			smsDetailUploadService.dealSmsIndividuation();
			SmsBatchUpload smsBatchUpload = smsBatchUploadDao.getById(6);
			smsBatchUpload.setMobileColumn("{列1}");
			smsService.smsIndividuation(smsBatchUpload);
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", e.toString());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/notifySmsbackUp.ajax")
	public Map<String, Object> notifySmsbackUp(@RequestParam("smsUploadId")Integer smsUploadId, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		try {
//			smsDetailUploadService.notifySmsbackUp(smsUploadId);
			result.put("success", true);
			result.put("message", "已受理");
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", e.toString());
		}
		return result;
	}
}