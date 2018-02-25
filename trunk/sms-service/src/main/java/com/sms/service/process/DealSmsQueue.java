package com.sms.service.process;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sms.dao.primarydao.PhoneDao;
import com.sms.dao.primarydao.PlainSendRecordDao;
import com.sms.entity.MercChannel;
import com.sms.entity.Phone;
import com.sms.entity.PlainSendRecord;
import com.sms.service.PrepareParamService;
import com.sms.service.send.SwichToSendService;

/**
 * 队列短信数据处理
 * @author Cao
 */

@Component
public class DealSmsQueue {

	private static Logger logger = LoggerFactory.getLogger(DealSmsQueue.class);
	
	//private ScheduledExecutorService smsRealTimePush = null;
	private ScheduledExecutorService smsDelayTimePush = null;
	
	//private final static int realTimeNum = 200;
	private final static int delayTimeNum = 200;
	
	@Autowired
	private SmsMessageQueue smsMessageQueue;
	
	@Autowired
	private PlainSendRecordDao plainSendRecordDao;
	
	@Autowired
	private SwichToSendService swichToSendService;
	
	@Autowired
	private PrepareParamService prepareParamService;
	
	@Autowired
	private PhoneDao phoneDao;
	
	/*private class SmsRealTimeDeal implements Runnable {
		
		@Override
		public void run() {
			try {
				if(smsMessageQueue.checkRealTimePushQueueIsEmpity())
					return;
				PlainSendRecord tempSmsRecord = smsMessageQueue.pollRealTimeSmsInfo();
				if(null == tempSmsRecord)
					return;
				
				Phone phone = phoneDao.getByMobile(tempSmsRecord.getMobile().substring(0, 7));
				if(null == phone){
					tempSmsRecord.setSendStatus(300);
					tempSmsRecord.setSendMsg("号码格式有误");
					plainSendRecordDao.insert(tempSmsRecord);
					throw new RuntimeException(tempSmsRecord.getMobile()+"号码格式有误");
				}
				tempSmsRecord = swich(tempSmsRecord, phone);
				tempSmsRecord.setProvince(phone.getProvince());
				tempSmsRecord.setCity(phone.getCity());
				tempSmsRecord.setIsp(phone.getIsp());
					
				Map<String,Object> result = swichToSendService.send(tempSmsRecord.getMobile(), tempSmsRecord.getContent(), tempSmsRecord.getChannelName(),tempSmsRecord.getAccountType());
				// 2.记录短信发送结果
				tempSmsRecord.setSendStatus(300);
				tempSmsRecord.setSendMsg("发送失败");
				if((boolean)result.get("status")){
					tempSmsRecord.setSendStatus(200);
					tempSmsRecord.setSendMsg("提交成功");
					tempSmsRecord.setReqMsgId((String)result.get("reqMsgId"));
				}
				Integer inserRows = plainSendRecordDao.insert(tempSmsRecord);
				if(1 != inserRows)
					logger.error("短信记录失败：analysisRealTimePushQueue-SmsRecord:", tempSmsRecord.toString());
				
			} catch (Exception e) {
				logger.error("短信发送失败:", e);
			}
		}
	}*/
	
	private class SmsDelayTimeDeal implements Runnable {

		@Override
		public void run() {
			try {
				if(smsMessageQueue.checkDelayedPushQueueIsEmpity())
					return;
					
				PlainSendRecord tempSmsRecord = smsMessageQueue.pollDelayedSmsInfo();
				if(null == tempSmsRecord)
					return;
				
				Phone phone = phoneDao.getByMobile(tempSmsRecord.getMobile().substring(0, 7));
				if(null == phone){
					tempSmsRecord.setSendStatus(300);
					tempSmsRecord.setSendMsg("号码格式有误");
					plainSendRecordDao.insert(tempSmsRecord);
					throw new RuntimeException(tempSmsRecord.getMobile()+"号码格式有误");
				}
				tempSmsRecord.setProvince(phone.getProvince());
				tempSmsRecord.setCity(phone.getCity());
				tempSmsRecord.setIsp(phone.getIsp());
				tempSmsRecord = swich(tempSmsRecord, phone);
				
				Map<String,Object> result = swichToSendService.send(tempSmsRecord.getSignTip(), tempSmsRecord.getMobile(), tempSmsRecord.getContent(), tempSmsRecord.getChannelName(),tempSmsRecord.getAccountType(), phone.getOperatorCode());
				// 2.记录预约短信
				tempSmsRecord.setSendStatus(300);
				tempSmsRecord.setSendMsg("发送失败");
				if((boolean)result.get("status")){
					tempSmsRecord.setSendStatus(200);
					tempSmsRecord.setSendMsg("提交成功");
					tempSmsRecord.setReqMsgId((String)result.get("reqMsgId"));
				}
				Integer inserRows = plainSendRecordDao.insert(tempSmsRecord);
				if(1 != inserRows)
					logger.error("短信记录失败：analysisRealTimePushQueue-SmsRecord:", tempSmsRecord.toString());
				//3.根据结果扣减余额
			} catch (Exception e) {
				logger.error("DealSmsQueue.analysisDelayedPushQueue-ERROR:", e);
			}
		}
	}
	
	public PlainSendRecord swich(PlainSendRecord plainSendRecord,Phone phone) throws Exception{
		List<MercChannel> mercChannelList =  prepareParamService.getMercChannel(plainSendRecord.getAccountNo());
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
		}else{
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
		}
	}
	
	@PostConstruct
	public void start() {
		//smsRealTimePush = Executors.newScheduledThreadPool(realTimeNum); 
		
		/*for(int i = 0; i < realTimeNum; i++){
			smsRealTimePush.scheduleAtFixedRate(new SmsRealTimeDeal(), 2, 2, TimeUnit.SECONDS);
		}
		logger.info("[DealSmsQueue.analysisRealTimePushQueue-Started]");*/
		
		smsDelayTimePush = Executors.newScheduledThreadPool(delayTimeNum);
		for(int k = 0; k < delayTimeNum; k++){
			smsDelayTimePush.scheduleAtFixedRate(new SmsDelayTimeDeal(), 2, 5, TimeUnit.SECONDS);
		}
		logger.info("[DealSmsQueue.analysisDelayedPushQueue-Started]");
	}
	
	@PreDestroy
	public void destory() {
		logger.info("[DealSmsQueue.smsRealTimePush-Destory]");
		//smsRealTimePush.shutdown();
		logger.info("[DealSmsQueue.smsDelayTimePush-Destory]");
		smsDelayTimePush.shutdown();
	}
}