package com.sms.service.send;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.dao.PlainSendRecordDao;
import com.sms.entity.MercAccount;
import com.sms.entity.MercChannel;
import com.sms.entity.Phone;
import com.sms.entity.PlainSendRecord;
import com.sms.service.PrepareParamService;
import com.sms.service.process.DealSmsQueue;
import com.sms.service.process.SmsMessageQueue;
import com.sms.util.TradeException;

@Service
public class ChannelService {
	private static Map<String, String> mobileOperator = new HashMap<String, String>();
	static{
		mobileOperator.put("130", "联通");
		mobileOperator.put("131", "联通");
		mobileOperator.put("132", "联通");
		mobileOperator.put("133", "电信");
		mobileOperator.put("134", "移动");
		mobileOperator.put("135", "移动");
		mobileOperator.put("136", "移动");
		mobileOperator.put("137", "移动");
		mobileOperator.put("138", "移动");
		mobileOperator.put("139", "移动");
		mobileOperator.put("145", "联通");
		mobileOperator.put("147", "移动");
		mobileOperator.put("149", "电信");
		mobileOperator.put("150", "移动");
		mobileOperator.put("151", "移动");
		mobileOperator.put("152", "移动");
		mobileOperator.put("153", "移动");
		mobileOperator.put("155", "联通");
		mobileOperator.put("156", "联通");
		mobileOperator.put("157", "移动");
		mobileOperator.put("158", "移动");
		mobileOperator.put("159", "移动");
		mobileOperator.put("171", "联通");
		mobileOperator.put("172", "联通");
		mobileOperator.put("173", "电信");
		mobileOperator.put("175", "联通");
		mobileOperator.put("176", "联通");
		mobileOperator.put("177", "电信");
		mobileOperator.put("178", "移动");
		mobileOperator.put("180", "电信");
		mobileOperator.put("181", "电信");
		mobileOperator.put("182", "移动");
		mobileOperator.put("183", "移动");
		mobileOperator.put("184", "移动");
		mobileOperator.put("185", "联通");
		mobileOperator.put("186", "联通");
		mobileOperator.put("187", "移动");
		mobileOperator.put("188", "移动");
		mobileOperator.put("189", "电信");
	}

	@Autowired
	private UnsubscribeService unsubscribeService;
	
	@Autowired
	private AuditingService auditingService;
	
	@Autowired
	private PlainSendRecordDao plainSendRecordDao;
	
	@Autowired
	private PrepareParamService prepareParamService;
	
	@Autowired
	private SwichToSendService swichToSendService;
	
	private Logger logger = LoggerFactory.getLogger(DealSmsQueue.class);
	
	//及时短信
	public Integer switchChannelToSend(String reservationDateTime,String content,String messageId,Integer orderFlag, MercAccount mercAccount, Integer sendNum, String[] mobiles,String signTip, String mercReqTime) throws TradeException {
		PlainSendRecord plainSendRecord;
		for(String mobile : mobiles){
			plainSendRecord = new PlainSendRecord(mercAccount.getSmsGroupId(), mercAccount.getSmsGroupDesc(), mercAccount.getAccountNo(), mercAccount.getMerchantNameAbbreviation(),
					mercAccount.getAccountType(), mercAccount.getFailToReissueFlag(),null,null, 100,null,null,mercReqTime,messageId,null,signTip);
			if(100 == mercAccount.getStartBlacklistFlag() && unsubscribeService.checkIfInBlackList(mobile)){			//黑名单校验
				String[] mobilesAudit = {mobile};
				auditingService.insertToAudit(orderFlag,reservationDateTime,messageId,content,mercAccount, sendNum, mobilesAudit,signTip);
				continue;
			}
			
			String strContent = content + signTip;
			plainSendRecord.setMobile(mobile);
			plainSendRecord.setContent(strContent);
			doSend(plainSendRecord);
//			SmsMessageQueue.addRealTimeSmsInfo(plainSendRecord);
			/*for(int i=0;i<sendNum;i++){
				String strContent = cutMsg(content, i, sendNum, signTip);							//截取短信
				plainSendRecord.setMobile(mobile);
				plainSendRecord.setContent(strContent);
				SmsMessageQueue.addRealTimeSmsInfo(plainSendRecord);								//将记录插入queue
			}*/
		}
		return sendNum * mobiles.length;
	}
	
	public Integer switchChannelToSendOne(String reservationDateTime,String content,String messageId,Integer orderFlag, MercAccount mercAccount, Integer sendNum, String mobile,String signTip, String mercReqTime) throws TradeException {
		PlainSendRecord plainSendRecord = new PlainSendRecord(mercAccount.getSmsGroupId(), mercAccount.getSmsGroupDesc(), mercAccount.getAccountNo(), mercAccount.getMerchantNameAbbreviation(),
				mercAccount.getAccountType(), mercAccount.getFailToReissueFlag(),null,null, 100,null,null,mercReqTime,messageId,null,signTip);
		if(100 == mercAccount.getStartBlacklistFlag() && unsubscribeService.checkIfInBlackList(mobile)){			//黑名单校验
			auditingService.insertToAuditOne(orderFlag,reservationDateTime,messageId,content,mercAccount, sendNum, mobile,signTip);
		}
		
		String strContent = content + signTip;
		plainSendRecord.setMobile(mobile);
		plainSendRecord.setContent(strContent);
		doSend(plainSendRecord);
//		SmsMessageQueue.addRealTimeSmsInfo(plainSendRecord);		
		/*for(int i=0;i<sendNum;i++){
			String strContent = cutMsg(content, i, sendNum, signTip);							//截取短信
			plainSendRecord.setMobile(mobile);
			plainSendRecord.setContent(strContent);
			SmsMessageQueue.addRealTimeSmsInfo(plainSendRecord);								//将记录插入queue
		}*/
		return sendNum;
	}
	
	public Integer switchChannelToSendAfterAudit(String content,String messageId, MercAccount mercAccount, Integer sendNum, String mobile,String signTip, String mercReqTime) throws TradeException {
		PlainSendRecord plainSendRecord = new PlainSendRecord(mercAccount.getSmsGroupId(), mercAccount.getSmsGroupDesc(), mercAccount.getAccountNo(), mercAccount.getMerchantNameAbbreviation(),
				mercAccount.getAccountType(), mercAccount.getFailToReissueFlag(),null,null, 100,null,null,mercReqTime,messageId,null,signTip);
		String strContent = content + signTip;
		plainSendRecord.setMobile(mobile);
		plainSendRecord.setContent(strContent);
		doSend(plainSendRecord);
		/*SmsMessageQueue.addRealTimeSmsInfo(plainSendRecord);
		for(int i=0;i<sendNum;i++){
			String strContent = cutMsg(content, i, sendNum, signTip);							//截取短信
			plainSendRecord.setMobile(mobile);
			plainSendRecord.setContent(strContent);
			SmsMessageQueue.addRealTimeSmsInfo(plainSendRecord);								//将记录插入queue
		}*/
		return sendNum;
	}
	
	public Integer switchChannelToSendReservation(String reservationDateTime,String content,String messageId,Integer orderFlag, MercAccount mercAccount, Integer sendNum, String mobile,String signTip) throws TradeException {
		PlainSendRecord plainSendRecord = new PlainSendRecord(mercAccount.getSmsGroupId(), mercAccount.getSmsGroupDesc(), mercAccount.getAccountNo(), mercAccount.getMerchantNameAbbreviation(),
				mercAccount.getAccountType(), mercAccount.getFailToReissueFlag(),null,null, 100,null,null,reservationDateTime,messageId,reservationDateTime,signTip);
		if(100 == mercAccount.getStartBlacklistFlag() && unsubscribeService.checkIfInBlackList(mobile)){			//黑名单校验
			auditingService.insertToAuditOne(orderFlag,reservationDateTime,messageId,content,mercAccount, sendNum, mobile,signTip);
		}
		String strContent = content + signTip;
		plainSendRecord.setMobile(mobile);
		plainSendRecord.setContent(strContent);
		SmsMessageQueue.addDelayedPushSmsInfo(plainSendRecord);
		/*for(int i=0;i<sendNum;i++){
			String strContent = cutMsg(content, i, sendNum, signTip);							//截取短信
			plainSendRecord.setMobile(mobile);
			plainSendRecord.setContent(strContent);
			SmsMessageQueue.addDelayedPushSmsInfo(plainSendRecord);							//将记录插入queue
		}*/
		return sendNum;
	}
	
	public String cutMsg(String content, Integer i, Integer sendNum, String signTip){
		return content;
		/*String strContent = "";
		if(content.length()+signTip.length()<=70){
			strContent = signTip + content;			//短信长度+前名长度<70
		}else{
			if((i+1)*(68-signTip.length()) > content.length()){
				strContent = signTip + content.substring(i*(68-signTip.length()));
			}else{
				strContent = signTip + content.substring(i*(68-signTip.length()), (i+1)*(68-signTip.length()));
			}
		}
		return strContent;*/
	}
	
	private void doSend(PlainSendRecord tempSmsRecord) {
		String phoneShip = "";
		if(tempSmsRecord.getMobile().startsWith("170")){
			phoneShip = query170OperatorMobile(tempSmsRecord.getMobile());
		} else {
			phoneShip = mobileOperator.get(tempSmsRecord.getMobile().substring(0, 3));
		}
		
		if(null == phoneShip || phoneShip.equals("")){
			tempSmsRecord.setSendStatus(300);
			tempSmsRecord.setSendMsg("号码格式有误");
			plainSendRecordDao.insert(tempSmsRecord);
			return;
		}
		try {
			tempSmsRecord = swich(tempSmsRecord, phoneShip);
			tempSmsRecord.setProvince(null);
			tempSmsRecord.setCity(null);
			tempSmsRecord.setIsp(phoneShip);
		} catch (Exception e) {
			logger.error("ChannelService.doSend-ERROR:", e);
		}
		
		Map<String,Object> result = swichToSendService.send(tempSmsRecord.getMobile(), tempSmsRecord.getContent(), tempSmsRecord.getChannelName(),tempSmsRecord.getAccountType());
		// 2.记录短信发送结果
		tempSmsRecord.setSendStatus(300);
		tempSmsRecord.setSendMsg("发送失败");
		if((boolean)result.get("status")){
			tempSmsRecord.setSendStatus(200);
			tempSmsRecord.setSendMsg("提交成功");
			tempSmsRecord.setReqMsgId((String)result.get("reqMsgId"));
		}
		int inserRows = plainSendRecordDao.insert(tempSmsRecord);
		if(1 != inserRows)
			logger.error("短信记录失败：analysisRealTimePushQueue-SmsRecord:", tempSmsRecord.toString());
	}
	
	private String query170OperatorMobile(String mobileStr) {
		String mobileOperator = "";
		int mobilePrefix = Integer.valueOf(mobileStr.substring(0,7));
		if(mobilePrefix >= 1700000 && mobilePrefix <= 1702999) mobileOperator =  "电信";
		if(mobilePrefix >= 1703000 && mobilePrefix <= 1703999) mobileOperator =  "移动";
		if(mobilePrefix >= 1704000 && mobilePrefix <= 1704999) mobileOperator =  "联通";
		if(mobilePrefix >= 1705000 && mobilePrefix <= 1706999) mobileOperator =  "移动";
		if(mobilePrefix >= 1707000 && mobilePrefix <= 1709999) mobileOperator =  "联通";
		return mobileOperator;
	}
	
	public PlainSendRecord swich(PlainSendRecord plainSendRecord, String phoneShip) throws Exception{
		List<MercChannel> mercChannelList =  prepareParamService.getMercChannel(plainSendRecord.getAccountNo());
		if(plainSendRecord.getAccountType() == 100){
			for(MercChannel mercChannel : mercChannelList){
				if(mercChannel.getChannelAttribute() == 100) {
					if(phoneShip.equals(mercChannel.getSupportOperatorsDes())){
						switch (phoneShip) {
						case "移动":
							plainSendRecord.setChannelName(mercChannel.getChannelCode()+"CMCC");
							break;
						case "联通":
							plainSendRecord.setChannelName(mercChannel.getChannelCode()+"UN");
							break;
						case "电信":
							plainSendRecord.setChannelName(mercChannel.getChannelCode()+"CN");
							break;
						}
						return plainSendRecord;
					}
				}
			}
			for(MercChannel mercChannel : mercChannelList){
				if(mercChannel.getChannelAttribute() == 100 && mercChannel.getSupportOperatorsDes().equals("全网通")){
					plainSendRecord.setChannelName(mercChannel.getChannelCode());
					return plainSendRecord;
				}
			}
			plainSendRecord.setSendMsg("无通道信息");
			plainSendRecord.setSendStatus(300);
			plainSendRecordDao.insert(plainSendRecord);
			throw new RuntimeException(plainSendRecord.getMobile()+"无通道信息");
		}else{
			for(MercChannel mercChannel : mercChannelList){
				if(mercChannel.getChannelAttribute() == 200){
					if(phoneShip.equals(mercChannel.getSupportOperatorsDes())){
						switch (phoneShip) {
						case "移动":
							plainSendRecord.setChannelName(mercChannel.getChannelCode()+"yxCMCC");
							break;
						case "联通":
							plainSendRecord.setChannelName(mercChannel.getChannelCode()+"yxUN");
							break;
						case "电信":
							plainSendRecord.setChannelName(mercChannel.getChannelCode()+"yxCN");
							break;
						}
						return plainSendRecord;
					}
				}
			}
			for(MercChannel mercChannel : mercChannelList) {
				if(mercChannel.getChannelAttribute() == 200 && mercChannel.getSupportOperatorsDes().equals("全网通")){
					plainSendRecord.setChannelName(mercChannel.getChannelCode());
					return plainSendRecord;
				}
			}
			plainSendRecord.setSendMsg("无通道信息");
			plainSendRecord.setSendStatus(300);
			plainSendRecordDao.insert(plainSendRecord);
			throw new RuntimeException(plainSendRecord.getMobile()+"无通道信息");
		}
	}
}