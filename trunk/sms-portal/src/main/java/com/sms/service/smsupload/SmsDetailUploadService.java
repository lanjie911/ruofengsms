package com.sms.service.smsupload;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.dao.smsupload.SmsBatchUploadDao;
import com.sms.dao.smsupload.SmsDetailUploadDao;
import com.sms.dao.trademanager.SmsAuditDao;
import com.sms.entity.smsupload.SmsBatchUpload;
import com.sms.entity.smsupload.SmsDetailUpload;
import com.sms.entity.trademanager.SmsAudit;
import com.sms.util.DatetimeUtil;

@Service
public class SmsDetailUploadService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private final int pageCount = 20000;
	
	@Autowired
	private SmsBatchUploadDao smsBatchUploadDao;
	
	@Autowired
	private SmsDetailUploadDao smsDetailUploadDao;
	
	@Autowired 
	private SmsAuditDao smsAuditDao;
	
	public Integer genSmsatchUpload(SmsBatchUpload newSmsBatchUpload){
		smsBatchUploadDao.insert(newSmsBatchUpload);
		return newSmsBatchUpload.getSmsUploadId();
	}
	
	public void insertUploadDetail(final String[] uploadStr, final SmsBatchUpload smsBatchUpload) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				ExecutorService dataPrsistence = Executors.newFixedThreadPool(20);
				//总记录数
				int rows = uploadStr.length;
				//页数
				int pageSum = rows%pageCount==0 ? rows/pageCount : rows/pageCount+1;
				int perIndex = 1, loopIndex = 1;
				List<SmsDetailUpload> list = new ArrayList<SmsDetailUpload>();
			
				for(int i = 0; i < rows; i++) {
					list.add(genDetailData(smsBatchUpload, uploadStr[i]));
					if((loopIndex == perIndex*pageCount) || perIndex == pageSum && loopIndex == rows){
						dataPrsistence.submit(batchInsertSmsDetailUpload(list));
						list = new ArrayList<SmsDetailUpload>();
						perIndex++;
					}
					loopIndex++;
				}
			}
		}).start();
	}
	
	private SmsDetailUpload genDetailData(SmsBatchUpload smsBatchUpload, String initStr){
		SmsDetailUpload newSmsDetailUpload = new SmsDetailUpload();
//		newSmsDetailUpload.setSmsUploadId(smsBatchUpload.getSmsUploadId());
		newSmsDetailUpload.setAccountNo(smsBatchUpload.getAccountNo());
		newSmsDetailUpload.setUploadContent(initStr);
		return newSmsDetailUpload;
	}
	
	private Runnable batchInsertSmsDetailUpload(List<SmsDetailUpload> list) {
		return new Runnable() {
			@Override
			public void run() {
				smsDetailUploadDao.insertSmsDetailUploadBatch(list);
			}
		};
	}

	/**
	 * 高级发送处理
	 * @param smsBatchUpload
	 */
	/*public void smsIndividuation(final SmsBatchUpload smsBatchUpload) {
		int row = smsBatchUploadDao.uploadFinish(smsBatchUpload);
		if(row != 1)
			throw new RuntimeException("提交失败");
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// 1.根据批次号分页加载数据
				dealSmsIndividuation(smsBatchUpload);
				// 2.处理list，批量update
			}
		}).start();
	}*/
	
	/*private void dealSmsIndividuation(SmsBatchUpload smsBatchUpload) {
		try {
			Thread.sleep(300000);
			ExecutorService dataPrsistence = Executors.newFixedThreadPool(50);
			boolean loopFlag = true;
			logger.info("dealSmsIndividuation-Start");
			while(loopFlag) {
				int row = smsDetailUploadDao.countBySmsUploadId(smsBatchUpload.getSmsUploadId());
				if(row == 0){
					loopFlag = false;
					return;
				}
				
				List<SmsDetailUpload> list = smsDetailUploadDao.loadFinishUpload(smsBatchUpload.getSmsUploadId());
				logger.info("Load List,list.size:" + list.size());
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
			
			// 将清洗之后的数据同步到 短信审批表里面
			boolean notifyFlag = true;
			while(notifyFlag) {
				int row = smsDetailUploadDao.countNotifyBySmsUploadId(smsBatchUpload.getSmsUploadId());
				if(row == 0){
					notifyFlag = false;
					return;
				}
				
				List<SmsDetailUpload> notifyList = smsDetailUploadDao.loadNotifyData(smsBatchUpload.getSmsUploadId());
				if(notifyList.isEmpty() || notifyList.size() == 0)
					continue;
				prepareSmsData(smsBatchUpload, notifyList);
			}
			logger.info("dealSmsIndividuation-Finish");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("SmsDetailUploadService.dealSmsIndividuation-ERROR", e);
		}
	}*/
	
	/*public void notifySmsbackUp(Integer smsUploadId) {
		try {
			SmsBatchUpload smsBatchUpload = smsBatchUploadDao.getById(smsUploadId);
			boolean notifyFlag = true;
			while(notifyFlag) {
				int row = smsDetailUploadDao.countNotifyBySmsUploadId(smsBatchUpload.getSmsUploadId());
				if(row == 0){
					notifyFlag = false;
					return;
				}
				
				List<SmsDetailUpload> notifyList = smsDetailUploadDao.loadNotifyData(smsBatchUpload.getSmsUploadId());
				if(notifyList.isEmpty() || notifyList.size() == 0)
					continue;
				prepareSmsData(smsBatchUpload, notifyList);
			}
			logger.info("dealSmsIndividuation-Finish");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	private void prepareSmsData(SmsBatchUpload smsBatchUpload, List<SmsDetailUpload> notifyList){
		for(SmsDetailUpload sc:notifyList){
			try {
				SmsAudit smsAudit = new SmsAudit();
				smsAudit.setAccountNo(smsBatchUpload.getAccountNo());
				smsAudit.setMerchantNameAbbreviation(smsBatchUpload.getMerchantNameAbbreviation());
				smsAudit.setAccountType(200);
				smsAudit.setOrderFlag(smsBatchUpload.getOrderFlag());
				if(null != smsBatchUpload.getReservationDatetime()){
					smsAudit.setReservationDatetime(DatetimeUtil.timestamp2StringDatetime(smsBatchUpload.getReservationDatetime())); 
				}else{
					smsAudit.setReservationDatetime(DatetimeUtil.getCurrentDateTime("yyyy-MM-dd HH:mm:ss")); 
				}
				smsAudit.setMobile(sc.getMobile());
				smsAudit.setSmsContent(sc.getSmsContent());
				smsAudit.setSignTip(smsBatchUpload.getSignTip());
				smsAudit.setSmsCount(sc.getSmsContentLength());
				smsAudit.setAuditingStatus(100);
				smsAudit.setSmsAccountNum(sc.getChargingCount());
				smsAudit.setSmsUnitCount(1);
				smsAudit.setBatchNo(smsBatchUpload.getBatchNo());
				smsAudit.setRegistrars(smsBatchUpload.getOperator());
				smsAudit.setCostTip(100);
				int row = smsAuditDao.insert(smsAudit);
				if(row == 1)
					smsDetailUploadDao.delete(sc.getDetailUploadId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			
	}

	/*private void analysisSmsContent(SmsBatchUpload smsBatchUpload, List<SmsDetailUpload> list, ExecutorService dataPrsistence,
			String indexVal, String smsFormatTemplate, int mobileIndex, String smsSign) {
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				try {
					
					List<SmsDetailUpload> updateList = new ArrayList<SmsDetailUpload>();
					String[] paramIndexVal = indexVal.split(",");
					for(SmsDetailUpload sc : list) {
						String uploadContent = sc.getUploadContent();
						String[] smsParams = uploadContent.split("#TT#");
						String tempSms = replaceSmsTemplate(smsFormatTemplate, smsParams, paramIndexVal, smsSign);
						
						sc.setMobile(smsParams[mobileIndex]);
						sc.setSmsContent(tempSms);
						sc.setSmsContentLength(tempSms.length());
						Integer sendNum = 1;
						 if(70 < tempSms.length())					//计算短信条数
							sendNum = (int) (Math.ceil(tempSms.length()/68d));
						sc.setChargingCount(sendNum);
						updateList.add(sc);
					}
					smsDetailUploadDao.batchUpdate(updateList);
				} catch (Exception e) {
					logger.error("SmsDetailUploadService.analysisSmsContent-ERROR", e);
				}
			}
		};
		dataPrsistence.submit(r);
	}*/
	
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
	
	
	public static void main(String[] args) {
		int rows = 150;
		int pageCount = 100000;
		//页数
		int pageSum = rows%pageCount==0 ? rows/pageCount : rows/pageCount+1;
		int perIndex = 1, loopIndex = 1;
		List<SmsDetailUpload> list = new ArrayList<SmsDetailUpload>();
	
		for(int i = 0; i < rows; i++) {
			if((loopIndex == perIndex*pageCount) || perIndex == pageSum && loopIndex == rows){
				System.out.println("序号：" +loopIndex + "\t批量Insert.....");
				perIndex++;
			}
			loopIndex++;
		}
		
		/*List<SmsDetailUpload> list = new ArrayList<SmsDetailUpload>();
		SmsDetailUpload newSmsDetailUpload = new SmsDetailUpload();
		newSmsDetailUpload.setAccountNo(1000157l);
		newSmsDetailUpload.setUploadContent("13811300001#TT#20040818005#TT#刘世芳#TT#3月#TT#工资#TT#8154#TT#");
		newSmsDetailUpload.setSmsUploadId(2);
		list.add(newSmsDetailUpload);
		list.add(newSmsDetailUpload);
		list.add(newSmsDetailUpload);
		list.add(newSmsDetailUpload);
		
		String enterSmsContent = "员工 {列3},你在{列4}工资为{列6},注意查收";
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
		System.out.println(smsFormatTemplate);
		
		String mobileColumn = "{列1}";
		
		// 解析手机号所在数组下标
		int mobileIndex = Integer.valueOf(mobileColumn.substring(mobileColumn.indexOf("{列")+2, mobileColumn.indexOf("}")))-1;
		System.out.println("手机号下标：" + mobileIndex);
		
		String smsSign = "【测试签名】";
		String[] paramIndexVal = indexVal.split(",");
		for(SmsDetailUpload sc : list){
			String uploadContent = sc.getUploadContent();
			String[] smsParams = uploadContent.split("#TT#");
			String tempSms = replaceSmsTemplate(smsFormatTemplate, smsParams, paramIndexVal, smsSign);
			
			sc.setMobile(smsParams[mobileIndex]);
			sc.setSmsContent(tempSms);
			sc.setSmsContentLength(tempSms.length());
			Integer sendNum = 1;
			 if(70 < tempSms.length())																//计算短信条数
				sendNum = (int) (Math.ceil(tempSms.length()/68d));
			sc.setChargingCount(sendNum);
			System.out.println(sc.toString());
		}*/
	}
	
}