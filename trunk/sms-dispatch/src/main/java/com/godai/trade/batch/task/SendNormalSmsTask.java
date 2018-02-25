package com.godai.trade.batch.task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.godai.trade.service.smsupload.SmsApplayService;

/**
 * 处理普通短信发送
 */
@Component
public class SendNormalSmsTask extends SimpleBatchTaskControl{
	private final Logger logger = LoggerFactory.getLogger(SendNormalSmsTask.class);
	private ScheduledExecutorService normalSendExec = Executors.newScheduledThreadPool(1);
	
	@Autowired
	private SmsApplayService smsApplayService;
	
	@PostConstruct
	public void doSend(){
		normalSendExec.scheduleAtFixedRate(smsApplayService.doSendNormal(), 5, 3, TimeUnit.SECONDS);
		logger.info("SendNormalSmsTask-Stared");
	}
	
	@PreDestroy
	public void closeSch() throws Exception {
		if(null != normalSendExec)
			shutdownAndAwaitTermination(normalSendExec, SendNormalSmsTask.class.getName());
	}
}