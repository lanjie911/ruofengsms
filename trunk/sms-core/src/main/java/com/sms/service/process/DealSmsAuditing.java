package com.sms.service.process;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sms.dao.AuditingDao;
import com.sms.entity.Auditing;
import com.sms.service.send.ChannelService;

/**
 * 处理预约短信
 * @author 陈奇
 */

@Component
public class DealSmsAuditing {
//	private static final Logger logger = LoggerFactory.getLogger(DealSmsAuditing.class);

//	@Autowired
//	private AuditingDao auditingDao;
//	
//	@Autowired
//	private ChannelService channelService;
//	
//	private ScheduledExecutorService se = null;
//	
//	private class SmsDeal implements Runnable {
//
//		@Override
//		public void run() {
//			// 加载短信记录
//			List<Auditing> list = auditingDao.loadAuditSms();
//			if(list.isEmpty() || null == list || list.size() == 0)
//				return;
//			// 循环遍历每一条预约短信，逐个短信发送
//			try {
//				for(Auditing audit : list) {
//					if(200 == audit.getOrderFlag()){
//						channelService.sendReserAferAudit(audit);
//					}else{
////						channelService.sendAferAudit(audit);
//					}
//					audit.setAuditingStatus(201);
//					auditingDao.update(audit);
//				} 
//			}catch (Exception e) {
//				logger.error("短信处理异常：", e);
//			}
//		}
//	}
//	
//	@PostConstruct
//	public void start() {
//		se = Executors.newScheduledThreadPool(1); 
//		se.scheduleAtFixedRate(new SmsDeal(), 60, 15, TimeUnit.SECONDS);
//		logger.info("[DealSmsAppointment-Sarted]");
//	}
//	
//	@PreDestroy
//	public void destory() {
//		logger.info("[DealSmsAppointment-Destory]");
//		if(null == se)
//			return;
//		se.shutdown();
//	}
	
}