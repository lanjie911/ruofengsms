package com.sms.service.smsupload;

import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sms.dao.smsupload.SmsApplayDao;
import com.sms.dao.smsupload.SmsDetailUploadDao;
import com.sms.entity.smsupload.SmsDetailUpload;
import com.sms.util.PhoneShipUtil;

/**
 * 解析高级短信数据
 */
@Component
public class AnalysisIndividuation {

	@Autowired
	private SmsDetailUploadDao smsDetailUploadDao;
	
	@Autowired
	private SmsApplayDao smsApplayDao;
	
	private AtomicInteger errorCount = new AtomicInteger(0); 

	public void analysisSms(String smsContent, Long batchNo, String mobileColumn, String signTip) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				boolean loopFlag = true;
				ExecutorService exec = Executors.newFixedThreadPool(20);
				
				String smsFormatTemplate = "";
				String temp = smsContent.replace("{列", "#2#");
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
				
				// 解析手机号所在数组下标
				int mobileIndex = Integer.valueOf(mobileColumn.substring(mobileColumn.indexOf("{列")+2, mobileColumn.indexOf("}")))-1;
				String[] paramIndexVal = indexVal.split(",");
				
				try {
					Thread.sleep(180000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				while(loopFlag){
					List<SmsDetailUpload> list = smsDetailUploadDao.loadPenddingByBatchNo(batchNo);
					if(list.size() == 0 || list.isEmpty()){
						loopFlag = false;
						break;
					}
					
					for(SmsDetailUpload sc : list){
						exec.submit(doAnalysis(sc, paramIndexVal, mobileIndex, smsFormatTemplate));
					}
				}
				refreshMobileNum(batchNo);
				exec.shutdown();
				
			}
		}).start();
	}
	
	/**
	 * 重新计算错误、待处理 数量
	 * @param batchNo 批次号
	 */
	private void refreshMobileNum(Long batchNo) {
		int succCount = smsDetailUploadDao.count101Num(batchNo);
		int outlierCount = errorCount.get();
		smsApplayDao.refreshMobileNum(batchNo, succCount, outlierCount);
	}

	public Runnable doAnalysis(final SmsDetailUpload smsDetailUpload, final String[] paramIndexVal, 
			final int mobileIndex, final String smsFormatTemplate){
		return new Runnable() {
			
			@Override
			public void run() {
				String uploadContent = smsDetailUpload.getUploadContent();
				String[] smsParams = uploadContent.split("#TT#");
				
				String mobile = smsParams[mobileIndex];
				if(!isMobile(mobile)){
					smsDetailUploadDao.delete(smsDetailUpload.getDetailUploadId());
					errorCount.incrementAndGet();
					return;
				}
				
				String tempSms = replaceSmsTemplate(smsFormatTemplate, smsParams, paramIndexVal);
				smsDetailUpload.setMobile(mobile);
				smsDetailUpload.setSmsContent(tempSms);
				smsDetailUpload.setSmsContentLength(tempSms.length());
				String phoneShip = "";
				if(mobile.startsWith("170")){
					phoneShip = PhoneShipUtil.query170OperatorMobile(mobile);
				} else {
					phoneShip = PhoneShipUtil.mobileOperator.get(mobile.substring(0, 3));
				}
				smsDetailUpload.setMobileOperator(Integer.valueOf(phoneShip));
				int sendNum = 1;
				if(70 < tempSms.length())					//计算短信条数
					sendNum = (int) (Math.ceil(tempSms.length()/68d));
				smsDetailUpload.setChargingCount(sendNum);
				smsDetailUploadDao.updateAnalysis(smsDetailUpload);
			}
		};
	}
	
	private String replaceSmsTemplate(String smsTemplate, String[] smsParams, String[] paramIndexVal) {
		String smsContent = null;
		String tmpStr = "";
		for(int i = 0; i<paramIndexVal.length; i++) {
			int indexVal = Integer.valueOf(paramIndexVal[i]);
			tmpStr += smsParams[indexVal] + ",";
		}
		
		smsContent = MessageFormat.format(smsTemplate, tmpStr.split(","));
		return smsContent;
	}
	
	private boolean isMobile(String mobile) {
        return Pattern.matches(PhoneShipUtil.REGEX_MOBILE, mobile);
    }
}