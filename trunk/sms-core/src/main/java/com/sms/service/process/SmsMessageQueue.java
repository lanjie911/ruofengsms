package com.sms.service.process;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sms.entity.PlainSendRecord;
import com.sms.util.ResultCommon;
import com.sms.util.TradeException;

@Component
public class SmsMessageQueue {
	private static final Logger logger = LoggerFactory.getLogger(SmsMessageQueue.class);
	
	// 实时推送短信-队列
	private static ConcurrentLinkedQueue<PlainSendRecord> realTimePushQueue = null;
	// 预约推送短信-队列
	private static ConcurrentLinkedQueue<PlainSendRecord> delayedPushQueue = null;
	
	static {
		realTimePushQueue = new ConcurrentLinkedQueue<PlainSendRecord>();
		delayedPushQueue = new ConcurrentLinkedQueue<PlainSendRecord>();
	}
	
	public static void addRealTimeSmsInfo(PlainSendRecord smsRecord) throws TradeException {
		try {
			Boolean addResult = realTimePushQueue.add(smsRecord);
			if(!addResult)
				logger.error("SmsMessageQueue.addRealTimeSmsInfo-Add-Failure:", smsRecord.toString());
		} catch (Exception e) {
			logger.error("SmsMessageQueue.addRealTimeSmsInfo-ERROR:", e);
			throw new TradeException(ResultCommon.ErrorCodeTypeEnum.SMS_OFFER_QUEUE_ERROR.getValue(), ResultCommon.ErrorCodeTypeEnum.SMS_OFFER_QUEUE_ERROR.getText());
		}
	}
	
	public static PlainSendRecord pollRealTimeSmsInfo() {
		if(!realTimePushQueue.isEmpty())
			return realTimePushQueue.poll();
		return null;
	}
	
	public static Boolean checkRealTimePushQueueIsEmpity(){
		return realTimePushQueue.isEmpty();
	}
	
	public static void addDelayedPushSmsInfo(PlainSendRecord smsRecord) throws TradeException {
		try {
			Boolean addResult = delayedPushQueue.add(smsRecord);
			if(!addResult)
				logger.error("SmsMessageQueue.addDelayedPushSmsInfo-Add-Failure:", smsRecord.toString());
		} catch (Exception e) {
			logger.error("SmsMessageQueue.addDelayedPushSmsInfo-ERROR:", e);
			throw new TradeException(ResultCommon.ErrorCodeTypeEnum.SMS_OFFER_QUEUE_ERROR.getValue(), ResultCommon.ErrorCodeTypeEnum.SMS_OFFER_QUEUE_ERROR.getText());
		}
	}
	
	public static PlainSendRecord pollDelayedSmsInfo() {
		if(!delayedPushQueue.isEmpty())
			return delayedPushQueue.poll();
		return null;
	}
	
	public static Boolean checkDelayedPushQueueIsEmpity() {
		return delayedPushQueue.isEmpty();
	}
}