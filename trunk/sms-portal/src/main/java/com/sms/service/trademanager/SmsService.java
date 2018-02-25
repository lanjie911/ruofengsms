package com.sms.service.trademanager;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sms.constant.Constants;
import com.sms.criteria.trademanager.SmsCriteria;
import com.sms.dao.smsupload.SmsBatchUploadDao;
import com.sms.dao.trademanager.SmsAuditDao;
import com.sms.dao.trademanager.SmsDao;
import com.sms.entity.manager.User;
import com.sms.entity.smsupload.SmsBatchUpload;
import com.sms.entity.trademanager.Sms;
import com.sms.entity.trademanager.SmsAudit;
import com.sms.service.idfactory.SerialIdFactory;
import com.sms.util.DatetimeUtil;
import com.sms.util.HttpUtil;

@Service
public class SmsService {
	private final int pageCount = 20000;
	@Autowired 
	private SmsDao smsDao;
	
	@Autowired 
	private SmsAuditDao smsAuditDao;
	
	@Autowired 
	private HttpUtil httpUtil;
	@Value("${validate_url}")
    private String validateUrl;
	
	@Autowired
	private SerialIdFactory idFactory;
	
	@Autowired
	private SmsBatchUploadDao smsBatchUploadDao;
	
	public List<Sms> query(SmsCriteria criteria) {
		return smsDao.query(criteria);
	}

	@Transactional
	public Map<String, Object>addSms(Sms sms, User u, Map<String, Object> result) throws UnsupportedEncodingException {
		String apiResult = httpUtil.httpPost(httpUtil.packageRequest("100",sms.getAccountNo(),sms.getContent()),validateUrl);
		JSONObject  apiJson = JSON.parseObject(apiResult);
		 if(!apiJson.getString("code").equals("0000")){
			 result.put("success", false);
			 result.put("message", apiJson.getIntValue("retmsg"));
			return result;
		 }

		 SmsAudit smsAudit = new SmsAudit();
		 smsAudit.setAccountNo(sms.getAccountNo());
		 smsAudit.setMerchantNameAbbreviation(sms.getMerchantNameAbbreviation());
		 smsAudit.setAccountType(200);
		 smsAudit.setOrderFlag(Integer.valueOf(sms.getOrderFlag()));
		 smsAudit.setReservationDatetime(sms.getReservationDatetime());
		 smsAudit.setMobile(sms.getMobile());
		 smsAudit.setSmsContent(sms.getContent());
		 smsAudit.setSignTip(sms.getSignTip());
		 smsAudit.setSmsCount(sms.getContent().length()+sms.getSignTip().length());
		 if(sms.getSendAuditFlag() == 100){
			 smsAudit.setAuditingStatus(100);
		 }else{
			 smsAudit.setAuditingStatus(200);
		 }
		 Integer sendNum = 1;
		 if(70 < sms.getContent().length())																//计算短信条数
			sendNum = (int) (Math.ceil(sms.getContent().length()/68d));
		 smsAudit.setSmsAccountNum(sendNum);
		 smsAudit.setSmsUnitCount(1);
		 smsAudit.setBatchNo(DatetimeUtil.getCurrentDateTime("yyyyMMddHHmmss"));
		 result.put("batchNo", smsAudit.getBatchNo());
		 smsAudit.setRegistrars(u.getLoginName());
		 
		 //String mobilesStr = StringUtils.deleteWhitespace(sms.getMobile());
		 //mobilesStr = mobilesStr.replaceAll("\n",",");
		 //sms.setMobile(mobilesStr);
//		 String[] mobiles = mobilesStr.split(",");
		 String[] mobiles = sms.getMobile().split("\r\n");
		 Integer sumCost =  (int) Math.floor(mobiles.length * sendNum * sms.getCostQuantity()/100);
		 
		 String[] strs = Arrays.copyOfRange(mobiles, sumCost, mobiles.length);
		 smsAudit.setCostTip(100);
		 Integer i =  smsAuditDao.insertBatch(strs, smsAudit);
		 
		 smsAudit.setCostTip(200);
		 String[] nosends = Arrays.copyOfRange(mobiles, 0, sumCost);
		 if(nosends.length >0){
			 i +=  smsAuditDao.insertBatch(nosends,smsAudit);
		 }
		 
		 
		if(i==0){
			result.put("success", false);
			result.put("message", "提交审核失败");
		}else{
			result.put("success", true);
			result.put("message", "提交审核成功");
		}
		return result;
	}
	
	public void addSmsNormal(final SmsBatchUpload smsBatchUpload) {
		int row = smsBatchUploadDao.uploadFinish(smsBatchUpload);
		if(row != 1)
			throw new RuntimeException("提交失败");
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				 try {
					 String batchNo = smsBatchUpload.getBatchNo();
					 int orderFlag = smsBatchUpload.getOrderFlag();
					 String reservationDatetime = null;
					 if(null != smsBatchUpload.getReservationDatetime())
						 reservationDatetime = DatetimeUtil.timestamp2StringDatetime(smsBatchUpload.getReservationDatetime());
					 String smsContent = smsBatchUpload.getSmsContent() + smsBatchUpload.getSignTip();
					 int smsCount = smsContent.length();
					 String signTip = smsBatchUpload.getSignTip();
					 Integer sendNum = 1;
					 if(70 < smsContent.length())																//计算短信条数
						sendNum = (int) (Math.ceil(smsContent.length()/68d));
					smsAuditDao.updateBySmsBatchUpload(batchNo, orderFlag, reservationDatetime, smsContent, smsCount, sendNum, signTip);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public Map<String, Object> addIndividuationSms(Sms sms, User u, Map<String, Object> result) throws UnsupportedEncodingException {
		
		String batchNo =(String) idFactory.generate(Constants.CHANNEL_NAME);
		String[] mobileAndContent = sms.getSmsConetntHidden().split("#NN#");
		
		List<SmsAudit> smsAuditList = new ArrayList<>();
		for (String mobileAndContents : mobileAndContent) {
			String[] temp= mobileAndContents.split("#TT#");
			//校验短信内容信息
			String apiResult = httpUtil.httpPost(httpUtil.packageRequest("100",sms.getAccountNo(),temp[1]),validateUrl);
			JSONObject  apiJson = JSON.parseObject(apiResult);
			 if(!apiJson.getString("code").equals("0000")){
				 result.put("success", false);
				 result.put("message", apiJson.getIntValue("retmsg"));
				return result;
			 }
			 
			 SmsAudit smsAudit = new SmsAudit();
			 smsAudit.setAccountNo(sms.getAccountNo());
			 smsAudit.setMerchantNameAbbreviation(sms.getMerchantNameAbbreviation());
			 smsAudit.setAccountType(200);
			 smsAudit.setOrderFlag(Integer.valueOf(sms.getOrderFlag()));
			 if(StringUtils.isNoneBlank(sms.getReservationDatetime())){
				 smsAudit.setReservationDatetime(sms.getReservationDatetime()); 
			 }else{
				 smsAudit.setReservationDatetime(DatetimeUtil.getCurrentDateTime("yyyy-MM-dd HH:mm:ss")); 
			 }
			 smsAudit.setMobile(temp[0]);
			 smsAudit.setSmsContent(temp[1]);
			 smsAudit.setSignTip(sms.getSignTip());
			 smsAudit.setSmsCount(temp[1].length()+sms.getSignTip().length());
			 if(sms.getSendAuditFlag() == 100){
				 smsAudit.setAuditingStatus(100);
			 }else{
				 smsAudit.setAuditingStatus(200);
			 }
			 Integer sendNum = 1;
			 if(70 < temp[1].length())																//计算短信条数
				sendNum = (int) (Math.ceil(temp[1].length()/68d));
			 smsAudit.setSmsAccountNum(sendNum);
			 smsAudit.setSmsUnitCount(1);
			 smsAudit.setBatchNo(batchNo);
			 smsAudit.setRegistrars(u.getLoginName());
			 smsAudit.setCostTip(100);
			
			 smsAuditList.add(smsAudit);
		}
		Integer i =  smsAuditDao.insertBatchList(smsAuditList);
		if(i==0){
			result.put("success", false);
			result.put("message", "提交审核失败");
		}else{
			result.put("success", true);
			result.put("message", "提交审核成功");
		}
		return result;
	}
	
	public void insertUploadDetail(final String[] uploadStr, final SmsBatchUpload smsBatchUpload) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				//总记录数
				int rows = uploadStr.length;
				List<SmsAudit> list = new ArrayList<SmsAudit>();
				if(rows < 100000){
					for(int i = 0; i < rows; i++) {
						list.add(genDetailData(smsBatchUpload, uploadStr[i]));
					}
					smsAuditDao.insertSmsDetailUploadBatch(list);
				}else{
					ExecutorService dataPrsistence = Executors.newFixedThreadPool(20);
					//页数
					int pageSum = rows%pageCount==0 ? rows/pageCount : rows/pageCount+1;
					int perIndex = 1, loopIndex = 1;
					for(int i = 0; i < rows; i++) {
						list.add(genDetailData(smsBatchUpload, uploadStr[i]));
						if((loopIndex == perIndex*pageCount) || perIndex == pageSum && loopIndex == rows){
							dataPrsistence.submit(batchInsertSmsDetailUpload(list));
							list = new ArrayList<SmsAudit>();
							perIndex++;
						}
						loopIndex++;
					}
				}
			}
		}).start();
	}
	
	private SmsAudit genDetailData(SmsBatchUpload smsBatchUpload, String initStr){
		SmsAudit sc = new SmsAudit();
		sc.setMerchantNameAbbreviation(smsBatchUpload.getMerchantNameAbbreviation());
		sc.setBatchNo(smsBatchUpload.getBatchNo());
		sc.setAccountNo(smsBatchUpload.getAccountNo());
		sc.setUploadContent(initStr);
		return sc;
	}
	
	private Runnable batchInsertSmsDetailUpload(List<SmsAudit> list) {
		return new Runnable() {
			@Override
			public void run() {
				smsAuditDao.insertSmsDetailUploadBatch(list);
			}
		};
	}
	
	/**
	 * 高级发送处理
	 * @param smsBatchUpload
	 */
	public void smsIndividuation(final SmsBatchUpload smsBatchUpload) {
		int row = smsBatchUploadDao.uploadFinish(smsBatchUpload);
		if(row != 1)
			throw new RuntimeException("提交失败");
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				int mobileCount = smsBatchUpload.getMobileCount();
				if(mobileCount < 100000){
					dealSmsIndividuationLessThan100000(smsBatchUpload);
				}else{
					dealSmsIndividuation(smsBatchUpload);
				}
			}
		}).start();
	}
	
	private void dealSmsIndividuation(SmsBatchUpload smsBatchUpload) {
		try {
			Thread.sleep(60000);
			ExecutorService dataPrsistence = Executors.newFixedThreadPool(20);
			boolean loopFlag = true;
			while(loopFlag) {
				int row = smsAuditDao.countBySmsUploadBatchNo(smsBatchUpload.getBatchNo());
				if(row == 0){
					loopFlag = false;
					return;
				}
				
				List<SmsAudit> list = smsAuditDao.loadFinishUploadLessThan10000(smsBatchUpload.getBatchNo());
				if(list.isEmpty() || list.size() == 0)
					continue;
				
				String enterSmsContent = smsBatchUpload.getSmsContent();
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
				
				String mobileColumn = smsBatchUpload.getMobileColumn();
				String smsSign = smsBatchUpload.getSignTip();
				// 解析手机号所在数组下标
				int mobileIndex = Integer.valueOf(mobileColumn.substring(mobileColumn.indexOf("{列")+2, mobileColumn.indexOf("}")))-1;
				
				analysisSmsContent(smsBatchUpload, list, dataPrsistence, indexVal, smsFormatTemplate, mobileIndex, smsSign);
				Thread.sleep(5000);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void dealSmsIndividuationLessThan100000(SmsBatchUpload smsBatchUpload) {
		try {
				
			List<SmsAudit> list = smsAuditDao.loadFinishUploadALL(smsBatchUpload.getBatchNo());
			if(list.isEmpty() || list.size() == 0){
				System.out.println("smsAuditDao.loadFinishUpload-Size 0 By batchNo:" + smsBatchUpload.getBatchNo());
				return;
			}
			String enterSmsContent = smsBatchUpload.getSmsContent();
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
			
			String mobileColumn = smsBatchUpload.getMobileColumn();
			String smsSign = smsBatchUpload.getSignTip();
			// 解析手机号所在数组下标
			int mobileIndex = Integer.valueOf(mobileColumn.substring(mobileColumn.indexOf("{列")+2, mobileColumn.indexOf("}")))-1;
			
			analysisSmsContentLessThan100000(smsBatchUpload, list, indexVal, smsFormatTemplate, mobileIndex, smsSign);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void analysisSmsContent(SmsBatchUpload smsBatchUpload, List<SmsAudit> list, ExecutorService dataPrsistence,
			String indexVal, String smsFormatTemplate, int mobileIndex, String smsSign) {
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				try {
					
					List<SmsAudit> updateList = new ArrayList<SmsAudit>();
					String[] paramIndexVal = indexVal.split(",");
					for(SmsAudit sc : list) {
						String uploadContent = sc.getUploadContent();
						String[] smsParams = uploadContent.split("#TT#");
						String tempSms = replaceSmsTemplate(smsFormatTemplate, smsParams, paramIndexVal, smsSign);
						
						sc.setMobile(smsParams[mobileIndex]);
						sc.setSmsContent(tempSms);
						Integer sendNum = 1;
						 if(70 < tempSms.length())					//计算短信条数
							sendNum = (int) (Math.ceil(tempSms.length()/68d));
						sc.setSmsCount(sendNum);
						sc.setSmsUnitCount(1);
						sc.setOrderFlag(smsBatchUpload.getOrderFlag());
						if(null != smsBatchUpload.getReservationDatetime()){
							sc.setReservationDatetime(DatetimeUtil.timestamp2StringDatetime(smsBatchUpload.getReservationDatetime())); 
						}else{
							sc.setReservationDatetime(DatetimeUtil.getCurrentDateTime("yyyy-MM-dd HH:mm:ss")); 
						}
						sc.setSignTip(smsBatchUpload.getSignTip());
						sc.setSmsAccountNum(sendNum);
						sc.setCostTip(100);
						
						updateList.add(sc);
					}
					smsAuditDao.batchUpdate(updateList);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		dataPrsistence.submit(r);
	}
	
	private void analysisSmsContentLessThan100000(SmsBatchUpload smsBatchUpload, List<SmsAudit> list,
			String indexVal, String smsFormatTemplate, int mobileIndex, String smsSign) {
		try {
			
			List<SmsAudit> updateList = new ArrayList<SmsAudit>();
			String[] paramIndexVal = indexVal.split(",");
			for(SmsAudit sc : list) {
				String uploadContent = sc.getUploadContent();
				String[] smsParams = uploadContent.split("#TT#");
				String tempSms = replaceSmsTemplate(smsFormatTemplate, smsParams, paramIndexVal, smsSign);
				
				sc.setMobile(smsParams[mobileIndex]);
				sc.setSmsContent(tempSms);
				Integer sendNum = 1;
				if(70 < tempSms.length())					//计算短信条数
					sendNum = (int) (Math.ceil(tempSms.length()/68d));
				sc.setSmsCount(sendNum);
				sc.setSmsUnitCount(1);
				sc.setOrderFlag(smsBatchUpload.getOrderFlag());
				if(null != smsBatchUpload.getReservationDatetime()){
					sc.setReservationDatetime(DatetimeUtil.timestamp2StringDatetime(smsBatchUpload.getReservationDatetime())); 
				}else{
					sc.setReservationDatetime(DatetimeUtil.getCurrentDateTime("yyyy-MM-dd HH:mm:ss")); 
				}
				sc.setSignTip(smsBatchUpload.getSignTip());
				sc.setSmsAccountNum(sendNum);
				sc.setCostTip(100);
				
				updateList.add(sc);
			}
			smsAuditDao.batchUpdate(updateList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String replaceSmsTemplate(String smsTemplate, String[] smsParams, String[] paramIndexVal, String smsSign) {
		String smsContent = null;
		String tmpStr = "";
		for(int i = 0; i<paramIndexVal.length; i++) {
			int indexVal = Integer.valueOf(paramIndexVal[i]);
			tmpStr += smsParams[indexVal] + ",";
		}
		
		smsContent = MessageFormat.format(smsTemplate, tmpStr.split(",")) + smsSign;
		return smsContent;
	}
	
	public void insertUploadDetailNormal(final String[] uploadStr, final SmsBatchUpload smsBatchUpload) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				//总记录数
				int rows = uploadStr.length;
				List<SmsAudit> list = new ArrayList<SmsAudit>();
				if(rows < 100000){
					for(int i = 0; i < rows; i++) {
						list.add(genDetailDataNormal(smsBatchUpload, uploadStr[i]));
					}
					smsAuditDao.insertSmsDetailUploadBatch(list);
				}else{
					ExecutorService dataPrsistence = Executors.newFixedThreadPool(20);
					//页数
					int pageSum = rows%pageCount==0 ? rows/pageCount : rows/pageCount+1;
					int perIndex = 1, loopIndex = 1;
					
					for(int i = 0; i < rows; i++) {
						list.add(genDetailDataNormal(smsBatchUpload, uploadStr[i]));
						if((loopIndex == perIndex*pageCount) || perIndex == pageSum && loopIndex == rows){
							dataPrsistence.submit(batchInsertSmsDetailUpload(list));
							list = new ArrayList<SmsAudit>();
							perIndex++;
						}
						loopIndex++;
					}
				}
				
			}
		}).start();
	}

	private SmsAudit genDetailDataNormal(SmsBatchUpload smsBatchUpload, String mobileStr){
		SmsAudit sc = new SmsAudit();
		sc.setMerchantNameAbbreviation(smsBatchUpload.getMerchantNameAbbreviation());
		sc.setBatchNo(smsBatchUpload.getBatchNo());
		sc.setAccountNo(smsBatchUpload.getAccountNo());
		sc.setMobile(mobileStr);
		return sc;
	}
}