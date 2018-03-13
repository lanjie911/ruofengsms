package com.sms.service.send;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.dao.primarydao.PhoneDao;
import com.sms.dao.primarydao.PlainSendRecordDao;
import com.sms.entity.MercAccount;
import com.sms.entity.MercChannel;
import com.sms.entity.Phone;
import com.sms.entity.PlainSendRecord;
import com.sms.service.PrepareParamService;
import com.sms.util.DatetimeUtil;
import com.sms.util.TradeException;

@Service
public class ChannelService {
	private Logger logger = LoggerFactory.getLogger(ChannelService.class);
	
	@Autowired
	private UnsubscribeService unsubscribeService;
	
	@Autowired
	private AuditingService auditingService;
	
	@Autowired
	private PhoneDao phoneDao;
	
	@Autowired
	private PlainSendRecordDao plainSendRecordDao;
	
	@Autowired
	private SwichToSendService swichToSendService;
	
	@Autowired
	private PrepareParamService prepareParamService;
	
	private ExecutorService smsRealTimePush = Executors.newFixedThreadPool(500);
//	private ExecutorService smsRealTimePush = Executors.newFixedThreadPool(200);
	
	//即时短信发送
	public void switchChannelToSend(String reservationDateTime, String content, String messageId, Integer orderFlag, 
			MercAccount mercAccount, Integer sendNum, String[] mobiles, String signTip, String mercReqTime) throws TradeException {
		PlainSendRecord plainSendRecord;
		//String smsContent = signTip + content;	//短信内容组装：签名+短信内容
		for(String mobile : mobiles) {
			plainSendRecord = new PlainSendRecord(mercAccount.getSmsGroupId(), mercAccount.getSmsGroupDesc(), mercAccount.getAccountNo(), mercAccount.getMerchantNameAbbreviation(),
					mercAccount.getAccountType(), mercAccount.getFailToReissueFlag(),null,null, 100,null,null,mercReqTime,messageId,reservationDateTime,signTip);
			if(100 == mercAccount.getStartBlacklistFlag() && unsubscribeService.checkIfInBlackList(mobile)){			//黑名单校验
				String[] mobilesAudit = {mobile};
				auditingService.insertToAudit(orderFlag, reservationDateTime, messageId, content, mercAccount, sendNum, mobilesAudit, signTip);
				continue;
			}
			
			plainSendRecord.setMobile(mobile);
			plainSendRecord.setContent(content);
			dealImmediatelySms(plainSendRecord);
		}
	}
	
	public Integer switchChannelToSendOne(String reservationDateTime, String content, String messageId, 
			Integer orderFlag, MercAccount mercAccount, Integer sendNum, String mobile, 
			String signTip, String mercReqTime) throws TradeException {
		PlainSendRecord plainSendRecord = new PlainSendRecord(mercAccount.getSmsGroupId(), mercAccount.getSmsGroupDesc(), mercAccount.getAccountNo(), mercAccount.getMerchantNameAbbreviation(),
				mercAccount.getAccountType(), mercAccount.getFailToReissueFlag(),null,null, 100,null,null,mercReqTime,messageId, reservationDateTime, signTip);
		if(100 == mercAccount.getStartBlacklistFlag() && unsubscribeService.checkIfInBlackList(mobile)){			//黑名单校验
			auditingService.insertToAuditOne(orderFlag, reservationDateTime, messageId, content, mercAccount, sendNum, mobile, signTip);
		}
		
		if(content.contains(signTip)){
			plainSendRecord.setContent(content.replace(signTip, ""));
		} else {
			plainSendRecord.setContent(content);
		}
		plainSendRecord.setMobile(mobile);
		
		/*String strContent;
		if(content.contains(signTip)){
			strContent = content;
		}else{
			strContent = signTip + content;
		}
		plainSendRecord.setMobile(mobile);
		plainSendRecord.setContent(strContent);*/
//		SmsMessageQueue.addRealTimeSmsInfo(plainSendRecord);								//将记录插入queue
		dealImmediatelySms(plainSendRecord);
		return sendNum;
	}
	
	public String cutMsg(String content, Integer i, Integer sendNum, String signTip){
		String strContent = "";
		if(content.length()+signTip.length()<=70){
			strContent = signTip + content;			//短信长度+前名长度<70
		}else{
			if((i+1)*(68-signTip.length()) > content.length()){
				strContent = signTip + content.substring(i*(68-signTip.length()));
			}else{
				strContent = signTip + content.substring(i*(68-signTip.length()), (i+1)*(68-signTip.length()));
			}
		}
		return strContent;
	}
	
	private void dealImmediatelySms(PlainSendRecord tempSendBean) {
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				if(null == tempSendBean)
					return;
				PlainSendRecord plainSendRecord = new PlainSendRecord();
				try {
					
					Phone phone = phoneDao.getByMobile(tempSendBean.getMobile().substring(0, 7));
					if(null == phone){
						tempSendBean.setSendStatus(300);
						tempSendBean.setSendMsg("号码格式有误");
						plainSendRecordDao.insert(tempSendBean);
						throw new RuntimeException(tempSendBean.getMobile()+"号码格式有误");
					}
					
					plainSendRecord.setProvince(phone.getProvince());
					plainSendRecord.setCity(phone.getCity());
					plainSendRecord.setIsp(phone.getIsp());
					
					plainSendRecord = swichSmsChannel(tempSendBean, phone);
						
					Map<String, Object> result = swichToSendService.send(plainSendRecord.getSignTip(), plainSendRecord.getMobile(), plainSendRecord.getContent(), plainSendRecord.getChannelName(), plainSendRecord.getAccountType(), phone.getOperatorCode());
					
//					Map<String, Object> result = new HashMap<>();
//					result.put("status", true);
//					String reqMsgId = getReqMsgId();
//					result.put("reqMsgId", reqMsgId);
					
					logger.info("ChannelService.dealImmediatelySms-swichToSendService Result{}", result);
					// 2.记录短信发送结果
					plainSendRecord.setSendStatus(300);
					plainSendRecord.setSendMsg("发送失败");
					plainSendRecord.setContent(plainSendRecord.getSignTip() + plainSendRecord.getContent());
					if((boolean)result.get("status")) {
						plainSendRecord.setSendStatus(200);
						plainSendRecord.setSendMsg("提交成功");
						plainSendRecord.setReqMsgId((String)result.get("reqMsgId"));
					}
					Integer inserRows = plainSendRecordDao.insert(plainSendRecord);
					if(1 != inserRows)
						logger.error("短信记录失败：analysisRealTimePushQueue-SmsRecord:", plainSendRecord.toString());
				} catch (Exception e) {
					logger.info("短信发送失败:"+tempSendBean.getMobile());
					logger.info("短信发送失败:"+plainSendRecord.getMobile());
					logger.error("短信发送失败:",e);
				}
			}
		};
		smsRealTimePush.execute(r);
	}

	private PlainSendRecord swichSmsChannel(PlainSendRecord plainSendRecord, Phone phone) throws Exception {
		List<MercChannel> mercChannelList =  prepareParamService.getMercChannel(plainSendRecord.getAccountNo());
		logger.info("ChannelService.swichSmsChannel-mercChannelList.size:{}", mercChannelList.size());
		logger.info("ChannelService.phone.isp:{}", phone.getIsp()+phone.getPhone());
		if(plainSendRecord.getAccountType() == 100){
			for(MercChannel mercChannel : mercChannelList){
				if(mercChannel.getChannelAttribute() == 100){
					if(phone.getIsp().equals(mercChannel.getSupportOperatorsDes())){
						switch (phone.getIsp()) {
						case "移动":
							plainSendRecord.setChannelName(mercChannel.getChannelDes()+"CMCC");
							break;
						case "联通":
							plainSendRecord.setChannelName(mercChannel.getChannelDes()+"UN");
							break;
						case "电信":
							plainSendRecord.setChannelName(mercChannel.getChannelDes()+"CN");
							break;
						}
						return plainSendRecord;
					}
				}
			}
			for(MercChannel mercChannel : mercChannelList){
				if(mercChannel.getChannelAttribute() == 100 && mercChannel.getSupportOperatorsDes().equals("全网通")){
					plainSendRecord.setChannelName(mercChannel.getChannelDes());
					return plainSendRecord;
				}
			}
			plainSendRecord.setSendMsg("无通道信息");
			plainSendRecord.setSendStatus(300);
			plainSendRecordDao.insert(plainSendRecord);
			throw new RuntimeException(plainSendRecord.getMobile()+"无通道信息");
		} else if(plainSendRecord.getAccountType() == 200) {
			for(MercChannel mercChannel : mercChannelList){
				if(mercChannel.getChannelAttribute() == 200){
					if(phone.getIsp().equals(mercChannel.getSupportOperatorsDes())){
						switch (phone.getIsp()) {
						case "移动":
							plainSendRecord.setChannelName(mercChannel.getChannelDes()+"yxCMCC");
							break;
						case "联通":
							plainSendRecord.setChannelName(mercChannel.getChannelDes()+"yxUN");
							break;
						case "电信":
							plainSendRecord.setChannelName(mercChannel.getChannelDes()+"yxCN");
							break;
						}
						return plainSendRecord;
					}
				}
			}
			for(MercChannel mercChannel : mercChannelList){
				if(mercChannel.getChannelAttribute() == 200 && mercChannel.getSupportOperatorsDes().equals("全网通")){
					plainSendRecord.setChannelName(mercChannel.getChannelDes());
					return plainSendRecord;
				}
			}
			plainSendRecord.setSendMsg("无通道信息");
			plainSendRecord.setSendStatus(300);
			plainSendRecordDao.insert(plainSendRecord);
			throw new RuntimeException(plainSendRecord.getMobile()+"无通道信息");
		} else if(plainSendRecord.getAccountType() == 300) {
			for(MercChannel mercChannel : mercChannelList) {
				if(mercChannel.getChannelAttribute() == 300) {
					if(phone.getIsp().equals(mercChannel.getSupportOperatorsDes())) {
						switch (phone.getIsp()) {
						case "移动":
							plainSendRecord.setChannelName(mercChannel.getChannelDes()+"CMCC");
							break;
						case "联通":
							plainSendRecord.setChannelName(mercChannel.getChannelDes()+"UN");
							break;
						case "电信":
							plainSendRecord.setChannelName(mercChannel.getChannelDes()+"CN");
							break;
						}
						return plainSendRecord;
					}
				}
			}
			for(MercChannel mercChannel : mercChannelList) {
				if(mercChannel.getChannelAttribute() == 300 && mercChannel.getSupportOperatorsDes().equals("全网通")){
					plainSendRecord.setChannelName(mercChannel.getChannelDes());
					return plainSendRecord;
				}
			}
			plainSendRecord.setSendMsg("无通道信息");
			plainSendRecord.setSendStatus(300);
			plainSendRecordDao.insert(plainSendRecord);
			throw new RuntimeException(plainSendRecord.getMobile()+"无通道信息");
		}
		return plainSendRecord;
	}
	
	public static String getReqMsgId(){
		String currentDateTime = DatetimeUtil.getCurrentDateTime("yyyyMMddHHmmss");
		int randNum = 1 + (int) (Math.random() * ((9999999 - 1) + 1));
		String reqMsgId = currentDateTime + randNum;
		return reqMsgId;
		
	}
	
	public static void main(String[] args) {
		String reqMsgId = getReqMsgId();
		System.out.println(reqMsgId);
	}
	
	
	
}